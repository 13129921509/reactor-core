package com.cai.reactor.core

interface Rule {

    List<Consumer> match(Event e)

    /**
     * 订阅
     * @param consumer
     * @return
     */
    void subscribe(Consumer consumer)

}
