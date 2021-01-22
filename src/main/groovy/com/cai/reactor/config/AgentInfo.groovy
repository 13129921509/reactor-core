package com.cai.reactor.config

import com.cai.reactor.core.Consumer
import com.cai.reactor.core.Event
import com.cai.reactor.core.Rule

class AgentInfo {

    Map<Class<Consumer>,List<Class<Rule>>> consumerBindRuleMap = [:]

    List<Class<Rule>> rules = []

    List<Class<Event>> events = []
    void bindRule(Class<Consumer> cc, List<Class<Rule>> rules){
        consumerBindRuleMap.put(cc, rules)
    }

    void insertRule(Class<Rule> clazz){
        rules.add(clazz)
    }

    void insertEvent(Class<Event> clazz){
        events.add(clazz)
    }
}
