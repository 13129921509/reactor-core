package com.cai.reactor.core

abstract class AbstractRule implements Rule{

    List<Consumer> consumers

    AbstractRule(){
        consumers = []
    }

    @Override
    void subscribe(Consumer consumer) {
        consumers.add(consumer)
        afterSubscribe(consumer)
    }

    abstract void afterSubscribe(Consumer consumer)
}
