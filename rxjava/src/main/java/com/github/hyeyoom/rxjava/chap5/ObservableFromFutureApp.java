package com.github.hyeyoom.rxjava.chap5;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ObservableFromFutureApp {
    public static void main(String[] args) {
        final long t1 = System.currentTimeMillis();
        final Future<Double> future = heavyLoad();
        lightLoad();
        final Observable<Double> fromFuture = Observable.fromFuture(future);
        fromFuture.subscribe(d -> System.out.println("ㅇㅅㅇ"));
        final long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
    }

    public static CompletableFuture<Double> heavyLoad() {
        return CompletableFuture.supplyAsync(() -> calculate());
    }

    private static Double calculate() {
        System.out.println("열심히 하는 중");
        try {
            Thread.sleep(6000);
            System.out.println("웰던");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 3.141592;
    }

    private static void lightLoad() {
        try {
            Thread.sleep(2000);
            System.out.println("짧은거");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
