package com.darren.architect_day30;

/**
 * Created by hcDarren on 2017/12/2.
 * 被观察者
 */

public abstract class Observable<T> implements ObservableSource<T>{

    public static <T> Observable<T> just(T item) {
        return onAssembly(new ObservableJust<T>(item));
    }

    private static <T> Observable<T> onAssembly(Observable<T> source) {
        // 留出来了
        return source;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        subscribeActual(observer);
    }

    public void subscribe(Consumer<T> onNext){
        subscribe(onNext,null,null);
    }

    private void subscribe(Consumer<T> onNext, Consumer<T> error, Consumer<T> complete) {
        subscribe(new LambdaObserver<T>(onNext));
    }

    protected abstract void subscribeActual(Observer<T> observer);


    /**
     * 操作符map
     * @param function
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Function<T, R> function) {
        return onAssembly(new ObservableMap<>(this,function));
    }
}
