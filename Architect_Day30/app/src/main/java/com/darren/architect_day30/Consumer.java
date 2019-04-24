package com.darren.architect_day30;

/**
 * Created by hcDarren on 2017/12/3.
 */

public interface Consumer<T> {
    void onNext(T item);
}
