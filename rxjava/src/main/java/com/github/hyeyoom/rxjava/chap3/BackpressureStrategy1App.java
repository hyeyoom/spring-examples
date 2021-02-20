package com.github.hyeyoom.rxjava.chap3;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureStrategy1App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis());
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(x -> System.out.println("interval doOnNext = " + x))
                .onBackpressureBuffer(
                        2,
                        () -> System.out.println("Overflow!"),
                        BackpressureOverflowStrategy.DROP_LATEST)
                .doOnNext(x -> System.out.println("onBackpressureBuffer = " + x))
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            Thread.sleep(1000);
                            System.out.println(data);
                        },
                        System.err::println
                );
        Thread.sleep(2800);
    }
}
