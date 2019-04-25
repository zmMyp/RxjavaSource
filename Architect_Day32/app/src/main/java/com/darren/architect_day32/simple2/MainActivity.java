package com.darren.architect_day32.simple2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.darren.architect_day32.R;
import com.darren.architect_day32.simple2.rxlogin.RxLogin;
import com.darren.architect_day32.simple2.rxlogin.RxLoginPlatform;
import com.darren.architect_day32.simple2.rxlogin.RxLoginResult;
import com.darren.sharecomponent.ShareApplication;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private View mClearContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShareApplication.attach(this);

        mClearContent = findViewById(R.id.clear_content);
        RxView.clicks(mClearContent).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                RxLogin.create(MainActivity.this)
                        .doOauthVerify(RxLoginPlatform.Platform_QQ)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<RxLoginResult>() {
                            @Override
                            public void accept(RxLoginResult rxLoginResult) throws Exception {
                                if(rxLoginResult.isSucceed()){
                                    // 怎么进来
                                }
                                Toast.makeText(MainActivity.this,rxLoginResult.getMsg(),Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
