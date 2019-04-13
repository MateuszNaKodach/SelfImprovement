package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial4

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch



class Tutorial4Receiver {

    @RabbitListener(queues = ["#{autoDeleteQueue.name}"])
    fun receiveAutoDeleteQueue(message: String){
        receive(message, "AUTO_DELETE<<<[ORANGE,BLACK]")
    }

    @RabbitListener(queues = ["#{durableQueue.name}"])
    fun receiveDurableQueue(message: String){
        receive(message, "DURABLE<<<[GREEN,BLACK]")
    }

    @Throws(InterruptedException::class)
    fun receive(message: String, receiver: String) {
        val watch = StopWatch()
        watch.start()
        println("instance $receiver [x] Received '$message'")
        doWork(message)
        watch.stop()
        println("instance " + receiver + " [x] Done message " +
                watch.totalTimeSeconds + "s")
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
