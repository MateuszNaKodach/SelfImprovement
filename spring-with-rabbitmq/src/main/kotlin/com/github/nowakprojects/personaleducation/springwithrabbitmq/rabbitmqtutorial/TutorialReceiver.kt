package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import javax.annotation.PostConstruct

@RabbitListener(queues = ["hello"])
class TutorialReceiver {

    @RabbitHandler
    fun receive(message: String){
        println(" [x] Received '$message'")
    }

    @PostConstruct
    fun print(){
        println("Constructed!")
    }
}
