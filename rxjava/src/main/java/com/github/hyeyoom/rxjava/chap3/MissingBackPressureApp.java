package com.github.hyeyoom.rxjava.chap3;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class MissingBackPressureApp {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1L, TimeUnit.MILLISECONDS)
                .doOnNext(System.out::println)
                .observeOn(Schedulers.computation())
                .subscribe(
                        x -> {
                            System.out.println("뭔가 심각한 일을 수행 중");
                            Thread.sleep(1);
                            System.out.println("x = " + x);
                        },
                        System.err::println,
                        () -> System.out.println("bye")
                );
        Thread.sleep(2000);
    }
}
