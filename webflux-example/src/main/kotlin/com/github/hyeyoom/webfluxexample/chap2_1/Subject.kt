package com.github.hyeyoom.webfluxexample.chap2_1

interface Subject<T> {
    fun registerObserver(observer: Observer<T>)
    fun unregisterObserver(observer: Observer<T>)
    fun notifyObservers(event: T)
}