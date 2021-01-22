package com.cai.reactor.core

class DefaultAgent implements Agent{

    List<Rule> rules
    List<Event> events

    static DefaultAgent create(){
        return new DefaultAgent()
    }

    protected DefaultAgent(){
        rules = []
        events = []
    }

    @Override
    void addRule(Rule rule) {
        rules.add(rule)
    }

    @Override
    void addEvent(Event e) {
        events.add(e)
    }

    @Override
    void accept(Event e) {
        List<Consumer> consumers = []
        rules.each {
            consumers.addAll(it.match(e))
        }
        consumers.each {
            it.consume(e)
        }
    }

    @Override
    Rule getRule(Class<Rule> clazz) {
        for(Rule rule : rules){
            if(rule.getClass() == clazz)
                return rule
        }
        return null
    }
}
