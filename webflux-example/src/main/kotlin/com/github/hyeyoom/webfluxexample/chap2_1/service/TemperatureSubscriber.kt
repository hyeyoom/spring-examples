package com.github.hyeyoom.webfluxexample.chap2_1.service

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class TemperatureSubscriber(
    private val emitter: RxSseEmitter
): Subscriber<Temperature> {
    override fun onSubscribe(s: Subscription?) {
        TODO("Not yet implemented")
    }

    override fun onNext(t: Temperature) {
        emitter.send(t)
    }

    override fun onError(t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }
}