package com.github.hyeyoom.rxjava.chap2;

import io.reactivex.processors.PublishProcessor;

public class HotPublisherApp {
    public static void main(String[] args) {
        final PublishProcessor<Integer> processor = PublishProcessor.create();
        processor.subscribe(x-> System.out.println("sub1 = " + x));
        processor.onNext(1);
        processor.onNext(3);
        processor.subscribe(x-> System.out.println("sub2 = " + x));
        processor.onNext(5);
        processor.onNext(7);

        processor.onComplete();
    }
}
