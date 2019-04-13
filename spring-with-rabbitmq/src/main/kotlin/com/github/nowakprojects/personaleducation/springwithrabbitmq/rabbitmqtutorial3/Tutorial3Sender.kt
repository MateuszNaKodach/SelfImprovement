package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial3

import com.github.nowakprojects.personaleducation.springwithrabbitmq.GETTING_STARTED_TUTORIAL_3
import com.github.nowakprojects.personaleducation.springwithrabbitmq.PUBLISH_SUBSCRIBE
import com.github.nowakprojects.personaleducation.springwithrabbitmq.PUB_SUB
import com.github.nowakprojects.personaleducation.springwithrabbitmq.SENDER
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

class Tutorial3Sender(val template: RabbitTemplate, val fanout: FanoutExchange) {

    private val dots = AtomicInteger(0)
    private val count = AtomicInteger(0)

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send(){
        val builder = StringBuilder("Hello")
        if (dots.getAndIncrement() == 3) {
            dots.set(1)
        }
        for (i in 0 until dots.get()) {
            builder.append('.')
        }
        builder.append(count.incrementAndGet())
        val message = builder.toString()

        template.convertAndSend(fanout.name, "", message)
        println("TUTORIAL 3 [x] Sent '$message'")
    }

}
