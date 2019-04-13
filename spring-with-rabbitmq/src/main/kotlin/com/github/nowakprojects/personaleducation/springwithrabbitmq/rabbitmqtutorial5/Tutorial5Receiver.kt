package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial5

import org.springframework.util.StopWatch
import org.springframework.amqp.rabbit.annotation.RabbitListener



class Tutorial5Receiver {

    @RabbitListener(queues = ["#{autoDeleteQueue.name}"])
    @Throws(InterruptedException::class)
    fun receive1(message: String) {
        receive(message, "AUTO_DELETE")
    }

    @RabbitListener(queues = ["#{durableQueue.name}"])
    @Throws(InterruptedException::class)
    fun receive2(message: String) {
        receive(message, "DURABLE")
    }

    @Throws(InterruptedException::class)
    fun receive(message: String, receiver: String) {
        val watch = StopWatch()
        watch.start()
        println("instance " + receiver + " [x] Received '"
                + message + "'")
        doWork(message)
        watch.stop()
        println("instance " + receiver + " [x] Done message "
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
