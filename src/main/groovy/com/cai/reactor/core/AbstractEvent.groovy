package com.cai.reactor.core

abstract class AbstractEvent implements Event{

    Map<String, Object> params = [:]

    void setParam(String key, Object value){
        params.put(key,value)
    }

    void setParam(Map kv){
        params.putAll(kv)
    }

    Map getParams(){
        return params
    }
}
