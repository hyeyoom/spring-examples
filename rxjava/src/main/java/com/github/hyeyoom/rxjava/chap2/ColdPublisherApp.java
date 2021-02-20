package com.github.hyeyoom.rxjava.chap2;

import io.reactivex.Flowable;

public class ColdPublisherApp {
    public static void main(String[] args) {
        final Flowable<Integer> flowable = Flowable.just(1, 3, 5, 7);

        flowable.subscribe(x -> System.out.println("sub1 = " + x));
        flowable.subscribe(x -> System.out.println("sub2 = " + x));
    }
}
