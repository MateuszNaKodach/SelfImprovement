package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import com.github.nowakprojects.personaleducation.springwithrabbitmq.GETTING_STARTED_TUTORIAL
import com.github.nowakprojects.personaleducation.springwithrabbitmq.HELLO_WORLD
import com.github.nowakprojects.personaleducation.springwithrabbitmq.RECEIVER
import com.github.nowakprojects.personaleducation.springwithrabbitmq.SENDER
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(GETTING_STARTED_TUTORIAL, HELLO_WORLD)
@Configuration
internal class GettingStartedTutorialConfig {

    @Bean
    fun hello() = Queue("hello")

    @Profile(RECEIVER)
    fun receiver() = TutorialReceiver()

    @Profile(SENDER)
    fun sender(template: RabbitTemplate, queue: Queue) = TutorialSender(template, queue)

    @Profile("usage_message")
    @Bean
    fun usageRunner() = CommandLineRunner { println("This app uses Spring Profiles to control its behavior.") }
}