package com.cai.reactor.config

import com.cai.reactor.annotataion.BindRule
import com.cai.reactor.core.Agent
import com.cai.reactor.core.Consumer
import com.cai.reactor.core.Event
import com.cai.reactor.core.Rule
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.annotation.AnnotationUtils

class ReactorScanBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware{

    ConfigurableApplicationContext applicationContext

    AgentInfo agentInfo

    @Override
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        registerRule(bean)
        checkBindRule(bean)
        registerEvent(bean)
        return bean
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext as ConfigurableApplicationContext
        agentInfo = applicationContext.getBean(AgentInfo)
    }

    private void registerRule(Object bean){
        if (bean instanceof Rule){
            agentInfo.insertRule(bean.getClass())
        }
    }

    private void checkBindRule(Object bean){
        if (bean instanceof Consumer){
            BindRule bindRule = AnnotationUtils.findAnnotation(bean.class, BindRule)
            if (bindRule && bindRule.rules()){
                agentInfo.bindRule(bean.class, bindRule.rules().toList())
            } else{
                throw new ReactorException("${bean.class} unbound rule")
            }
        }

    }

    private void registerEvent(Object bean) {
        if (bean instanceof Event){
            agentInfo.insertEvent(bean.getClass())
        }
    }
}