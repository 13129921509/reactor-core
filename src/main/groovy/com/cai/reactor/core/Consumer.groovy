package com.cai.reactor.core

interface Consumer {

    String key()

    void consume(Event e)
}
