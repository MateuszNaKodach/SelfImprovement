package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial4

import com.github.nowakprojects.personaleducation.springwithrabbitmq.GETTING_STARTED_TUTORIAL_4
import com.github.nowakprojects.personaleducation.springwithrabbitmq.RECEIVER
import com.github.nowakprojects.personaleducation.springwithrabbitmq.ROUTING
import com.github.nowakprojects.personaleducation.springwithrabbitmq.SENDER
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(GETTING_STARTED_TUTORIAL_4, ROUTING)
@Configuration
internal class Tutorial4Config {

    enum class Color {
        ORANGE,
        BLACK,
        GREEN;
    }

    @Bean
    fun directExchange() = DirectExchange("tutorial4.direct")

    @Profile(RECEIVER)
    class ReceiverConfig {

        @Bean
        fun autoDeleteQueue() = AnonymousQueue()

        @Bean
        fun durableQueue(): Queue = QueueBuilder.durable("tutorial4.durable-queue")
                .build()

        @Bean
        fun bindingAutoDeleteWithOrange(directExchange: DirectExchange, autoDeleteQueue: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue)
                        .to(directExchange)
                        .with(Color.ORANGE)

        @Bean
        fun bindingAutoDeleteWithBlack(directExchange: DirectExchange, autoDeleteQueue: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue)
                        .to(directExchange)
                        .with(Color.BLACK)

        @Bean
        fun bindingDurableWithGreen(directExchange: DirectExchange, durableQueue: Queue): Binding =
                BindingBuilder.bind(durableQueue)
                        .to(directExchange)
                        .with(Color.GREEN)

        @Bean
        fun bindingDurableWithBlack(directExchange: DirectExchange, durableQueue: Queue): Binding =
                BindingBuilder.bind(durableQueue)
                        .to(directExchange)
                        .with(Color.BLACK)

        @Bean
        fun tutorial4Receiver() = Tutorial4Receiver()

    }

    @Profile(SENDER)
    class SenderConfig {

        @Bean
        fun tutorial4Sender(template: RabbitTemplate, directExchange: DirectExchange) = Tutorial4Sender(template, directExchange)
    }
}