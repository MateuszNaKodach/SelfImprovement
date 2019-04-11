package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener

@RabbitListener(queues = ["hello"])
class TutorialReceiver {

    @RabbitHandler
    fun receive(message: String){
        println(" [x] Received '$message'")
    }
}
