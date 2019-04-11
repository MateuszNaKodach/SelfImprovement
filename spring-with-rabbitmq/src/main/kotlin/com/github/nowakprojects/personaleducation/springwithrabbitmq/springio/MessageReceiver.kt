package com.github.nowakprojects.personaleducation.springwithrabbitmq.springio

import com.github.nowakprojects.personaleducation.springwithrabbitmq.SPRING_IO
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.concurrent.CountDownLatch

@Profile(SPRING_IO)
internal class MessageReceiver {

    private val latch = CountDownLatch(1);

    fun receiveMessage(message: Any){
        println("Received message: $message")
        latch.countDown()
    }
}