package com.cai.reactor.test

import com.cai.reactor.annotataion.BindRule
import com.cai.reactor.core.Consumer
import com.cai.reactor.core.Event
import com.cai.reactor.core.GroupMatchRule
import com.cai.reactor.core.SimpleRule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@BindRule(rules = [SimpleRule, GroupMatchRule])
class TestConsumer implements Consumer{

    Logger log  = LoggerFactory.getLogger(TestConsumer)

    @Override
    String key() {
        return "com.test.value"
    }

    @Override
    void consume(Event e) {
        log.info("consume-----")
        println e.params
    }
}
