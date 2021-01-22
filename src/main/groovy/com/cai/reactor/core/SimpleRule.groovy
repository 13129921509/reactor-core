package com.cai.reactor.core

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SimpleRule extends AbstractRule{

    @Override
    List<Consumer> match(Event e) {
        return consumers.findAll { e.topic() == it.key() }.toList()
    }

    @Override
    void afterSubscribe(Consumer consumer) {

    }
}
