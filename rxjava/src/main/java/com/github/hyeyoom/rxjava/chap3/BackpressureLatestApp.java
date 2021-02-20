package com.github.hyeyoom.rxjava.chap3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureLatestApp {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(x -> System.out.println("interval doOnNext = " + x))
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(
                        data -> {
                            Thread.sleep(1000);
                            System.out.println(data);
                        },
                        System.err::println
                );
        Thread.sleep(5500);
    }
}
