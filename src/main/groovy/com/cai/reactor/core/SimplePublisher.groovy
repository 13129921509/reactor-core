package com.cai.reactor.core

class SimplePublisher implements Publisher{

    Agent agent

    static SimplePublisher create(Agent agent){
        return new SimplePublisher(agent)
    }

    private SimplePublisher(Agent agent){
        this.agent = agent
    }

    @Override
    boolean publish(Event e) {
        agent.accept(e)
        return true
    }
}
