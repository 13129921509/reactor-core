package com.cai.reactor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class ReactorCoreApplication {
    static void main(String[] args) {
        SpringApplication.run(ReactorCoreApplication, args)
    }
}
