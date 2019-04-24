package com.darren.architect_day30;
import android.support.annotation.NonNull;

/**
 * Created by hcDarren on 2017/12/2.
 * 观察者
 */
public interface Observer<T> {
    void onSubscribe();
    void onNext(@NonNull T item);
    void onError(@NonNull Throwable e);
    void onComplete();
}
