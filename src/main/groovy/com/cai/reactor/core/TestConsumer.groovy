package com.cai.reactor.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TestConsumer implements Consumer{
    Logger log = LoggerFactory.getLogger(TestConsumer)

    @Override
    String key() {
        return "test.simple"
    }

    @Override
    void consume(Event e) {
        println "match ${this.getClass().getSimpleName()}"
        println e.getParams()
        log.info("consume successful")
        Thread.sleep(1000L)
    }
}
