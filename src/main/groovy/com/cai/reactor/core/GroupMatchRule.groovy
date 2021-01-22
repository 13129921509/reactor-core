package com.cai.reactor.core

import org.springframework.stereotype.Component

@Component
class GroupMatchRule extends AbstractRule{

    final static GROUP_KEY = "*"

    Map<Class, List> groups

    GroupMatchRule() {
        this.groups = [:]
    }

    @Override
    List<Consumer> match(Event e) {
        List<Consumer> cs = []
        List keys = e.topic().split("\\.").toList()
        groups.each {k,v ->
            Consumer consumer
            if (groupMatch(v, keys) && (consumer = findConsumer(k)) != null){
                cs.add(consumer)
                consumer = null
            }
        }
        return cs
    }

    @Override
    void afterSubscribe(Consumer consumer) {
        groups.put(consumer.class, consumer.key().split("\\.").toList())
    }

    static boolean groupMatch(List<String> keys, List<String> topics){
        if (keys.size() != topics.size())
            return false
        for (int i = 0 ; i < keys.size() ; i++){
            if (!(keys[i] == topics[i] || GROUP_KEY == topics[i])){
                return false
            }
        }
        return true
    }

    Consumer findConsumer(Class<Consumer> clazz){
        return consumers.find {
            it.class == clazz
        }
    }
}
