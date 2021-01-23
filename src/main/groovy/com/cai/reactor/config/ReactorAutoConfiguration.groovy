package com.cai.reactor.config

import com.cai.reactor.core.Agent
import com.cai.reactor.core.Parallel
import com.cai.reactor.core.Publisher
import com.cai.reactor.core.SimplePublisher
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReactorAutoConfiguration implements ApplicationContextAware{

    ConfigurableApplicationContext applicationContext

    @Bean
    ReactorSetting reactorSetting(){
        return new ReactorSetting()
    }

    @Bean
    Agent agent(ReactorSetting reactorSetting){
        try{
            Class<Agent> a = Class.forName(reactorSetting.agent as String)
            if (a instanceof Parallel)
                return a.newInstance(reactorSetting.threadOfNumber)
            return a.newInstance()
        }catch(Throwable t){
            throw new ReactorException("Class :$reactorSetting.agent => Agent error!!!")
        }
    }

    @Bean
    Publisher publisher(Agent agent){
        return SimplePublisher.create(agent)
    }

    @Bean
    AgentInfo agentInfo(){
        return new AgentInfo()
    }

    @Bean
    ReactorScanBeanPostProcessor reactorScanBeanPostProcessor(){
        return new ReactorScanBeanPostProcessor()
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext as ConfigurableApplicationContext
    }
}

class ReactorException extends Exception{

    String message

    ReactorException(String message) {
        this.message = message
    }
}