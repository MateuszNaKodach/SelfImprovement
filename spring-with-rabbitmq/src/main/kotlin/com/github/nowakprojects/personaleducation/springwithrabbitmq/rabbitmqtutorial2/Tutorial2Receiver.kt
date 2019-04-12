package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial2

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch

@RabbitListener(queues = ["hello"])
class Tutorial2Receiver(val id: Int) {

    @RabbitHandler
    fun receive(message: String){
        StopWatch()
                .apply { start() }
                .also {
                    println("Instance $id [x] Received <$message>")
                }
                .also {
                    doWork(message)
                }
                .apply { stop() }
                .also {
                    println("Instance $id [x] Done in ${it.totalTimeSeconds} s")
                }
    }

    private fun doWork(message: String) {
        message.toCharArray().filter { it == '.' }.forEach {
            Thread.sleep(1000)
        }
    }
}
