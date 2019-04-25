package com.darren.architect_day31;

import android.support.annotation.NonNull;

/**
 * Created by hcDarren on 2017/12/9.
 */

class ObserverOnObservable<T> extends Observable<T> {
    final Observable<T> source;
    final Schedulers schedulers;
    public ObserverOnObservable(Observable<T> source, Schedulers schedulers) {
        this.source = source;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {
        source.subscribe(new ObserverOnObserver(observer,schedulers));
    }

    private class ObserverOnObserver<T> implements Observer<T>,Runnable{
        final Observer<T> observer;
        final Schedulers schedulers;
        private T value;
        public ObserverOnObserver(Observer<T> observer, Schedulers schedulers) {
            this.observer = observer;
            this.schedulers = schedulers;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(@NonNull T item) {
            value = item;
            schedulers.scheduleDirect(this);

        }

        @Override
        public void onError(@NonNull Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public void run() {
            // 主线程 或者 其他
            observer.onNext(value);
        }
    }
}
