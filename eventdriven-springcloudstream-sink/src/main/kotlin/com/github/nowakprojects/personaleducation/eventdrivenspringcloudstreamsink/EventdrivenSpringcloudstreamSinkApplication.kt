package com.github.nowakprojects.personaleducation.eventdrivenspringcloudstreamsink

import com.github.nowakprojects.eventdrivenspringcloudstream.DomainEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@EnableBinding(Sink::class)
@SpringBootApplication
class EventdrivenSpringcloudstreamSinkApplication

fun main(args: Array<String>) {
    runApplication<EventdrivenSpringcloudstreamSinkApplication>(*args)
}

@Component
internal class EventHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun log(){
        log.info("EventHandler created!")
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: DomainEvent) {
        log.info("received: {}", event)
    }

}
