package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!usage_message")
class RabbitAmqpTutorialRunner(
        @Value("\${tutorial.client.duration:0}") val duration: Int,
        val context: ConfigurableApplicationContext
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Ready ... running for " + duration + "ms")
        Thread.sleep(duration.toLong())
        context.close()
    }
}
