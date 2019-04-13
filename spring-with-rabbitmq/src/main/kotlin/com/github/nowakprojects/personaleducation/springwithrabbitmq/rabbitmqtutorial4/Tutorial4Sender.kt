package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial4

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tutorial4Sender(
        private val template: RabbitTemplate,
        private val direct: DirectExchange
) {

    private val index = AtomicInteger(0)
    private val count = AtomicInteger(0)
    private val keys = Tutorial4Config.Color.values()

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        if (index.incrementAndGet() == 3) {
            index.set(0)
        }
        val key = keys[index.get()]
        val message = StringBuilder("Hello to ").apply {
            append(key.name).append(' ')
            append(count.getAndIncrement())
        }.toString()
        template.convertAndSend(direct.name, key.name, message)
        println(" [x] Sent '$message'")
    }

}
