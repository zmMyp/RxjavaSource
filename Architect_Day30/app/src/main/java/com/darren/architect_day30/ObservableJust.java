package com.darren.architect_day30;

/**
 * Created by hcDarren on 2017/12/2.
 */

public class ObservableJust<T> extends Observable<T> {
    private T item;
    public ObservableJust(T item) {
        this.item = item;
    }

    @Override
    protected void subscribeActual(Observer<T> observer) {

        try {
            // 3.第三步 observer -> MapObserver.onNext(String)
            observer.onNext(item);
            observer.onComplete();
        }catch (Exception e){
            observer.onError(e);
        }
    }

}
