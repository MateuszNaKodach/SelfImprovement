package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import javax.annotation.PostConstruct


class TutorialSender(val template: RabbitTemplate, val queue: Queue) {

    var messageCount = 0

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send(){
        val message = "Hello World! ${messageCount++}"
        template.convertAndSend(queue.name, message)
        println(" [x] Sent '$message'")
    }

    @PostConstruct
    fun print(){
        println("Constructed!")
    }
}
