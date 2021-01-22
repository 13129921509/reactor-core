package com.cai.reactor.core

interface Event {

    String topic()

    Map getParams()

}
