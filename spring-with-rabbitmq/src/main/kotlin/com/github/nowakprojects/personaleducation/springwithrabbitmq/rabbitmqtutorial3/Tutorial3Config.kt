package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial3

import com.github.nowakprojects.personaleducation.springwithrabbitmq.*
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(GETTING_STARTED_TUTORIAL_3, PUBLISH_SUBSCRIBE, PUB_SUB)
@Configuration
internal class Tutorial3Config {

    @Bean
    fun fanoutExchange() = FanoutExchange("tutorial.fanout")

    @Profile(RECEIVER)
    class ReceiverConfig {

        @Bean
        fun autoDeleteQueue1() = AnonymousQueue()

        @Bean
        fun autoDeleteQueue2() = AnonymousQueue()

        @Bean
        fun binding1(exchange: FanoutExchange, autoDeleteQueue1: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue1).to(exchange)

        @Bean
        fun binding2(exchange: FanoutExchange, autoDeleteQueue2: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue2).to(exchange)

        @Bean
        fun receiver() = Tutorial3Receiver()

    }

    @Profile(SENDER)
    class SenderConfig {

        @Bean
        fun sender(template: RabbitTemplate, fanout: FanoutExchange) = Tutorial3Sender(template, fanout)

    }
}
