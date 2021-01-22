package com.cai.reactor.core


import com.google.common.util.concurrent.ThreadFactoryBuilder
import groovyx.gpars.GParsExecutorsPool

import java.util.concurrent.ThreadFactory

class ParallelAgent extends DefaultAgent{

    private int numberOfThread = 5

    private ThreadFactory threadFactory

    ParallelAgent(int numberOfThread, ThreadFactory threadFactory) {
        this.numberOfThread = numberOfThread
        this.threadFactory  = threadFactory
    }

    static ParallelAgent create(){
        return new ParallelAgent()
    }

    static ParallelAgent create(int numberOfThread){
        return new ParallelAgent(numberOfThread)
    }

    static ParallelAgent create(int numberOfThread, ThreadFactory threadFactory){
        return new ParallelAgent(numberOfThread, threadFactory)
    }

    ParallelAgent(int numberOfThread) {
        this.numberOfThread = numberOfThread
        defaultCreateThreadFactory()
    }

    ParallelAgent() {
        defaultCreateThreadFactory()
    }

    private void defaultCreateThreadFactory(){
        threadFactory = new ThreadFactoryBuilder().setDaemon(false).setNameFormat("core-reactor-%d").build()
    }

    @Override
    void accept(Event e) {
        GParsExecutorsPool.withPool(numberOfThread, threadFactory){
            List<Consumer> consumers = []

            rules.eachParallel { Rule o->
                consumers.addAll(o.match(e))
            }
            consumers.eachParallel { Consumer c->
                c.consume(e)
            }
        }
    }
}
