package com.cai.reactor.test

import com.cai.reactor.core.Event
import com.cai.reactor.core.Publisher
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplicationRunListener
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner)
class ReactorCoreApplicationTest {

    @Autowired
    Publisher publisher

    @Test
    void test(){
        publisher.publish(new TestEvent())
    }
}
