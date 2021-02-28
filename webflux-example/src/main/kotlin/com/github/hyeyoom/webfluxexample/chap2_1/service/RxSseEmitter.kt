package com.github.hyeyoom.webfluxexample.chap2_1.service

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

class RxSseEmitter
    : SseEmitter() {
    companion object {
        val SSE_SESSION_TIMEOUT = 30 * 60 * 1000L
    }



}