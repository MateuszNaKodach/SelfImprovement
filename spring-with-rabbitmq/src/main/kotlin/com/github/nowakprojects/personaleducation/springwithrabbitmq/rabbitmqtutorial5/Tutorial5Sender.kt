package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial5

import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger


class Tutorial5Sender(private val template: RabbitTemplate, private val topicExchange: TopicExchange) {


    private val index = AtomicInteger(0)
    private val count = AtomicInteger(0)

    private val keys = arrayOf("quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox", "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox")

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    fun send() {
        val builder = StringBuilder("Hello to ")
        if (this.index.incrementAndGet() == keys.size) {
            this.index.set(0)
        }
        val key = keys[this.index.get()]
        builder.append(key).append(' ')
        builder.append(this.count.incrementAndGet())
        val message = builder.toString()
        template.convertAndSend(topicExchange.name, key, message)
        println(" [x] Sent '$message'")
    }

}
