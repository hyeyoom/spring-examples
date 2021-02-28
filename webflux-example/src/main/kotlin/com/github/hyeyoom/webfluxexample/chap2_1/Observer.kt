package com.github.hyeyoom.webfluxexample.chap2_1

interface Observer<T> {
    fun observe(event: T)
}