package com.cai.reactor.annotataion

import com.cai.reactor.core.Rule

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface BindRule {

    Class<Rule>[] rules()

}