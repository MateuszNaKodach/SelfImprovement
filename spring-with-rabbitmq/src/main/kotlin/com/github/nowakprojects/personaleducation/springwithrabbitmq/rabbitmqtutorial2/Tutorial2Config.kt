package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial2

import com.github.nowakprojects.personaleducation.springwithrabbitmq.GETTING_STARTED_TUTORIAL_2
import com.github.nowakprojects.personaleducation.springwithrabbitmq.RECEIVER
import com.github.nowakprojects.personaleducation.springwithrabbitmq.SENDER
import com.github.nowakprojects.personaleducation.springwithrabbitmq.WORK_QUEUES
import com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial.TutorialReceiver
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile(GETTING_STARTED_TUTORIAL_2, WORK_QUEUES)
internal class Tutorial2Config {

    @Bean
    fun hello() = Queue("hello")

    @Profile(RECEIVER)
    class ReceiverConfig {
        @Bean
        fun receiver1() = Tutorial2Receiver(1)

        @Bean
        fun receiver2() = Tutorial2Receiver(2)
    }

    @Profile(SENDER)
    class SenderConfig {
        @Bean
        fun tutorial2Sender(template: RabbitTemplate, queue: Queue) = Tutorial2Sender(template, queue)
    }

}
