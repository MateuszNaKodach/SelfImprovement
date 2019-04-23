package com.github.nowakprojects.personaleducation.eventdrivenspringcloudstreamsink

import com.github.nowakprojects.eventdrivenspringcloudstream.Channels
import com.github.nowakprojects.eventdrivenspringcloudstream.UserDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.integration.annotation.Publisher
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@SpringBootApplication
class EventdrivenSpringcloudstreamSinkApplication

fun main(args: Array<String>) {
    runApplication<EventdrivenSpringcloudstreamSinkApplication>(*args)
}

@EnableBinding(Channels::class)
@Component
class EventHandler(private val channels: Channels) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun log() {
        log.info("EventHandler created!")
    }

    @StreamListener("users")
    fun handleEvent(event: UserDomainEvent) {
        when (event) {
            is UserDomainEvent.UserDeactivated -> {
                publishToPayments("cancelPayment")
                publishToShipments("cancelShipment")
                publishToCommunication("sendEmail")
            }
            is UserDomainEvent.UserActivated -> TODO()
            is UserDomainEvent.UserNicknameChanged -> TODO()
        }
    }

    @Publisher("payments")
    fun publishToPayments(command: String) = command

    @Publisher("shipments")
    fun publishToShipments(command: String) = command

    @Publisher("shipments")
    fun publishToCommunication(command: String) = command
}
