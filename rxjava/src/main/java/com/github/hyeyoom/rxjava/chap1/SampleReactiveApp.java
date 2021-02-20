package com.github.hyeyoom.rxjava.chap1;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.stream.IntStream;

public class SampleReactiveApp {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(1, 3, 21, 10, 8, 11)
                .subscribeOn(Schedulers.io())
                .filter(number -> number > 6 && (number % 2 != 0))
                .subscribe(x -> System.out.println("x = " + x));

        Thread.sleep(1000);
    }
}
