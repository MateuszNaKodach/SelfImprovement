package com.github.nowakprojects.personaleducation.eventdrivenspringcloudstreamsink

import com.github.nowakprojects.eventdrivenspringcloudstream.DomainEvent
import com.github.nowakprojects.eventdrivenspringcloudstream.UserDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@SpringBootApplication
class EventdrivenSpringcloudstreamSinkApplication

fun main(args: Array<String>) {
    runApplication<EventdrivenSpringcloudstreamSinkApplication>(*args)
}

@EnableBinding(Sink::class)
@Component
internal class EventHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun log() {
        log.info("EventHandler created!")
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: DomainEvent) {
        log.info("received: {}", event)
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: UserDomainEvent) {
        log.info("received: {}", event)
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: String) {
        log.info("received: {}", event)
    }

    /*
    @StreamListener(Sink.INPUT)
    fun handleEvent(event: UserDomainEvent.UserNicknameChanged) {
        log.info("received: {}", event)
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: UserDomainEvent.UserDeactivated) {
        log.info("received: {}", event)
    }

    @StreamListener(Sink.INPUT)
    fun handleEvent(event: UserDomainEvent.UserActivated) {
        log.info("received: {}", event)
    }*/

}
