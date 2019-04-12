package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial2

import com.github.nowakprojects.personaleducation.springwithrabbitmq.SENDER
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
@Profile(SENDER)
class Tutorial2Sender(val template: RabbitTemplate, val queue: Queue) {

    private val dots = AtomicInteger(0)
    private val count = AtomicInteger(0)

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (dots.incrementAndGet() == 4) {
            dots.set(1)
        }
        val message = "Hello${(1 .. dots.get()).map { '.' }.joinToString(separator = "")}${count.incrementAndGet()}"
        template.convertAndSend(queue.name, message)
        println(" [x] Sent '$message'")
    }

}
