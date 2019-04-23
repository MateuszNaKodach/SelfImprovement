package com.github.nowakprojects.eventdrivenspringcloudstream

import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.Publisher
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
@EnableBinding(value = [Source::class, UserUpdatesSource::class])
internal class EventPublisher(private val userUpdatesSource: UserUpdatesSource) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Publisher(channel = Source.OUTPUT)
    fun publish(event: DomainEvent) =
            event.also {
                log.info("about to send: {}", it)
            }//.also {
             //   userUpdatesSource.userUpdates().send(MessageBuilder.withPayload(event).build())
            //}

}
