package com.cai.reactor.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "reactor")
class ReactorSetting {

    int threadOfNumber = 5

    String agent = "com.cai.reactor.core.DefaultAgent"

}
