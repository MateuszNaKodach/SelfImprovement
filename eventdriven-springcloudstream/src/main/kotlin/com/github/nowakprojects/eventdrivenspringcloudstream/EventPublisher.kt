package com.github.nowakprojects.eventdrivenspringcloudstream

import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.Publisher
import org.springframework.stereotype.Component

@Component
internal class EventPublisher {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Publisher(channel = Source.OUTPUT)
    fun publish(event: DomainEvent) =
            event.also {
                log.info("about to send: {}", it)
            }

}
