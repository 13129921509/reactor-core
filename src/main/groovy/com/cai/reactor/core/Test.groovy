package com.cai.reactor.core

class Test {

    static void main(String[] args) {
//        创建代理
        DefaultAgent agent = ParallelAgent.create()
//        创建事件
        TestEvent testEvent = new TestEvent()
//        创建规则
        Rule simpleRule = new SimpleRule()
        Rule groupRule = new GroupMatchRule()
//        消费者订阅到规则
        simpleRule.subscribe(new TestConsumer())
        groupRule.subscribe(new TestConsumer())
//        代理添加事件
        agent.addEvent(testEvent)
//        代理添加规则
        agent.addRule(simpleRule)
        agent.addRule(groupRule)
//        创建发布者，发布事件
        SimplePublisher publisher = SimplePublisher.create(agent)
        publisher.publish(new TestEvent().addParam("k","kk"))
    }
}