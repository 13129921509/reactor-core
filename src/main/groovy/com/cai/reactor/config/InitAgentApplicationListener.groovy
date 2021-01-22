package com.cai.reactor.config

import com.cai.reactor.core.Agent
import org.springframework.beans.BeansException
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext

class InitAgentApplicationListener implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware{

    ConfigurableApplicationContext applicationContext

    AgentInfo agentInfo

    Agent agent

    @Override
    void onApplicationEvent(ApplicationReadyEvent event) {
        agentInfo = applicationContext.getBean(AgentInfo)
        agent = applicationContext.getBean(Agent)
        initAgent()
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext as ConfigurableApplicationContext
    }

    private void initAgent(){
        def rules = agentInfo.rules
        def events = agentInfo.events
        rules.each {
            agent.addRule(applicationContext.getBean(it))
        }
        events.each {
            agent.addEvent(applicationContext.getBean(it))
        }

        agentInfo.consumerBindRuleMap.each {k, v->

            v.findAll({ rules.contains(it) })
                    .each { agent.getRule(it)
                            .subscribe(applicationContext.getBean(k))}
        }

    }
}
