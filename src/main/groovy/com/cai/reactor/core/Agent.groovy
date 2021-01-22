package com.cai.reactor.core

interface Agent extends RuleOp{

    void addRule(Rule rule)

    void addEvent(Event e)

    void accept(Event e)
}
