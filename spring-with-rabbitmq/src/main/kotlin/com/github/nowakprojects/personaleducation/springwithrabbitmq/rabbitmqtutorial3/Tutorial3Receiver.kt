package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial3

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch


class Tutorial3Receiver {

    @RabbitListener(queues = ["#{autoDeleteQueue1.name}"])
    fun receive1(message: String) {
        receive(message, 1)
    }

    @RabbitListener(queues = ["#{autoDeleteQueue2.name}"])
    fun receive2(message: String) {
        receive(message, 2)
    }

    @Throws(InterruptedException::class)
    fun receive(message: String, receiver: Int) {
        val watch = StopWatch()
        watch.start()
        println("TUTORIAL 3 instance $receiver [x] Received '$message'")
        doWork(message)
        watch.stop()
        println("TUTORIAL 3 instance " + receiver + " [x] Done in "
                + watch.totalTimeSeconds + "s")
    }

    @Throws(InterruptedException::class)
    private fun doWork(message: String) {
        for (ch in message.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000)
            }
        }
    }

}
