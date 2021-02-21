package com.github.hyeyoom.rxjava.chap5;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.List;

public class FromIterableApp {
    public static void main(String[] args) {
        final List<String> nations = Arrays.asList("Korea", "USA", "Canada", "UK");
        final Observable<String> observable = Observable.fromIterable(nations);
        final Disposable disposable = observable.subscribe(System.out::println);
        System.out.println(disposable.isDisposed());
    }
}
