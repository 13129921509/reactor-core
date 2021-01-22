package com.cai.reactor.core

class TestEvent implements Event{

    Map<String, Object> info = [:]

    @Override
    String topic() {
        return "test.simple"
    }

    @Override
    Map getParams() {
        return info
    }

    Event addParam(String key, Object value){
        info.put(key,value)
        return this
    }

    Event addParams(Map params){
        info.putAll(params)
        return this
    }
}
