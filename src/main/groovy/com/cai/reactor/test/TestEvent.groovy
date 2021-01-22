package com.cai.reactor.test

import com.cai.reactor.core.Event
import org.springframework.stereotype.Component

@Component
class TestEvent implements Event{
    @Override
    String topic() {
        return "com.test.value"
    }

    @Override
    Map getParams() {
        return ["k":"123","z":222]
    }
}
