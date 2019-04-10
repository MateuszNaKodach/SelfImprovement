package com.github.nowakprojects.personaleducation.springwithrabbitmq

import org.springframework.context.annotation.Configuration
import java.util.concurrent.CountDownLatch

@Configuration
internal class MessageReceiver {

    private val latch = CountDownLatch(1);

    fun receiveMessage(message: Any){
        println("Received message: $message")
        latch.countDown()
    }
}