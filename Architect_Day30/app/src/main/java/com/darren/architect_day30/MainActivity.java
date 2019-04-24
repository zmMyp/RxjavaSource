package com.darren.architect_day30;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = (ImageView) findViewById(R.id.image);

        source1();

    }

    private void source1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.just("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555994563474&di=8ca112bd67ccbd6de75727fe064f8187&imgtype=0&src=http%3A%2F%2Fpic29.nipic.com%2F20130601%2F12122227_123051482000_2.jpg")
                        .map(new Function<String, Bitmap>() {
                            @Override
                            public Bitmap apply(String urlPath) throws Exception {
                                // 第五步
                                URL url = new URL(urlPath);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                InputStream inputStream = urlConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                return bitmap;
                            }
                        })
                        .map(new Function<Bitmap, Bitmap>() {
                            @Override
                            public Bitmap apply(@NonNull Bitmap bitmap) throws Exception {
                                bitmap = createWatermark(bitmap, "RxJava2.0");
                                return bitmap;
                            }
                        })
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void onNext(final Bitmap bitmap) {
                                // 第七步
                                Log.e("TAG", "item = " + bitmap);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mImage.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    private void source2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable<String> justObservable=Observable.just("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555994563474&di=8ca112bd67ccbd6de75727fe064f8187&imgtype=0&src=http%3A%2F%2Fpic29.nipic.com%2F20130601%2F12122227_123051482000_2.jpg");

                Observable<Bitmap>  ObversableMap1=justObservable.map(new Function<String, Bitmap>() {
                            @Override
                            public Bitmap apply(String urlPath) throws Exception {
                                // 第五步
                                URL url = new URL(urlPath);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                InputStream inputStream = urlConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                return bitmap;
                            }
                        });

                Observable<Bitmap> ObversableMap2 =ObversableMap1.map(new Function<Bitmap, Bitmap>() {
                            @Override
                            public Bitmap apply(@NonNull Bitmap bitmap) throws Exception {
                                bitmap = createWatermark(bitmap, "RxJava2.0");
                                return bitmap;
                            }
                        });

                Consumer<Bitmap> consumer=new Consumer<Bitmap>() {
                    @Override
                    public void onNext(final Bitmap bitmap) {
                        // 第七步
                        Log.e("TAG", "item = " + bitmap);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImage.setImageBitmap(bitmap);
                            }
                        });
                    }
                };

                ObversableMap2 .subscribe(consumer);


            }
        }).start();
    }

    private Bitmap createWatermark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印颜色
        p.setColor(Color.parseColor("#C5FF0000"));
        // 水印字体大小
        p.setTextSize(150);
        //抗锯齿
        p.setAntiAlias(true);
        //绘制图像
        canvas.drawBitmap(bitmap, 0, 0, p);
        //绘制文字
        canvas.drawText(mark, 0, h / 2, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bmp;
    }
}
