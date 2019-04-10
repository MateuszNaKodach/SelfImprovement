package com.github.nowakprojects.personaleducation.springwithrabbitmq

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val topicExchangeName = "spring-boot-exchange"
const val queueName = "spring-boot"
const val routingKey = "foo.bar.#"

//https://spring.io/guides/gs/messaging-rabbitmq/
@Configuration
internal class RabbitMqConfig {

    @Bean
    fun queue() = Queue(queueName,false,true,false)

    @Bean
    fun exchange() = TopicExchange(topicExchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange) =
            BindingBuilder.bind(queue).to(exchange).with(routingKey)

    @Bean
    fun listenerAdapter(messageReceiver: MessageReceiver) =
            MessageListenerAdapter(messageReceiver, "receiveMessage")

    @Bean
    fun container(connectionFactory: ConnectionFactory, listenerAdapter: MessageListenerAdapter)
    = SimpleMessageListenerContainer()
            .apply {
                this.connectionFactory = connectionFactory
                this.setQueueNames(queueName)
                this.setMessageListener(listenerAdapter)
            }
}