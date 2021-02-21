package com.github.hyeyoom.rxjava.chap5;

import io.reactivex.Observable;

import java.time.LocalDateTime;

public class ObservableDeferExample {
    public static void main(String[] args) throws InterruptedException {
        final Observable<LocalDateTime> observable = Observable.defer(() -> {
            final LocalDateTime now = LocalDateTime.now();
            System.out.println("@defer:          " + now);
            return Observable.just(now);
        });

        final Observable<LocalDateTime> observableJust = Observable.just(LocalDateTime.now());

        observable.subscribe(t -> System.out.println("defer 구독 시간1: " + t));
        observableJust.subscribe(t -> System.out.println("just 구독 시간2:  " + t));

        Thread.sleep(3000);

        observable.subscribe(t -> System.out.println("defer 구독 시간1: " + t));
        observableJust.subscribe(t -> System.out.println("just 구독 시간2:  " + t));
    }
}
