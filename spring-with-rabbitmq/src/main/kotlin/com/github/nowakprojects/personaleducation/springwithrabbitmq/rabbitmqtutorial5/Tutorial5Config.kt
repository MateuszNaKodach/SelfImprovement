package com.github.nowakprojects.personaleducation.springwithrabbitmq.rabbitmqtutorial5

import com.github.nowakprojects.personaleducation.springwithrabbitmq.*
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(GETTING_STARTED_TUTORIAL_5, TOPICS)
@Configuration
internal class Tutorial5Config {

    @Bean
    fun topicExchange() = TopicExchange("tutorial5.topic")

    @Profile(RECEIVER)
    class ReceiverConfig {

        @Bean
        fun autoDeleteQueue() = AnonymousQueue()

        @Bean
        fun durableQueue(): Queue = QueueBuilder.durable("tutorial5.durable-queue")
                .build()

        @Bean
        fun binding1(topicExchange: TopicExchange, autoDeleteQueue: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue)
                        .to(topicExchange)
                        .with("*.orange.*")

        @Bean
        fun binding2(topicExchange: TopicExchange, autoDeleteQueue: Queue): Binding =
                BindingBuilder.bind(autoDeleteQueue)
                        .to(topicExchange)
                        .with("*.*.rabbit")

        @Bean
        fun binding3(topicExchange: TopicExchange, durableQueue: Queue): Binding =
                BindingBuilder.bind(durableQueue)
                        .to(topicExchange)
                        .with("lazy.#")

        @Bean
        fun tutorial4Receiver() = Tutorial5Receiver()

    }

    @Profile(SENDER)
    class SenderConfig {

        @Bean
        fun tutorial5Sender(template: RabbitTemplate, topicExchange: TopicExchange) = Tutorial5Sender(template, topicExchange)
    }
}