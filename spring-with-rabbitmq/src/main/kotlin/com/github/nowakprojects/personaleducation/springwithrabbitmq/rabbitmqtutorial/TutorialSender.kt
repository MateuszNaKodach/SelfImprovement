package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled


class TutorialSender(val template: RabbitTemplate, val queue: Queue) {

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send(){
        val message = "Hello World!"
        template.convertAndSend(queue.name, message)
        println(" [x] Sent '$message'")
    }

}
