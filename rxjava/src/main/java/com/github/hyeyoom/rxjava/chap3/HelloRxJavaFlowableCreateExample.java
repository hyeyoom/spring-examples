package com.github.hyeyoom.rxjava.chap3;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class HelloRxJavaFlowableCreateExample {
    public static void main(String[] args) throws InterruptedException {
        final Flowable<String> flowable = Flowable.create((FlowableOnSubscribe<String>) emitter -> {
            final String[] data = {"Hello", "RxJava!"};
            for (String datum : data) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(datum);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println(t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }
                });
        Thread.sleep(5000);
    }
}
