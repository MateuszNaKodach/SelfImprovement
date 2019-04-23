package com.github.nowakprojects.eventdrivenspringcloudstream

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface Channels {

    @Input("users")
    fun users(): SubscribableChannel

    @Output("payments")
    fun payments(): MessageChannel

    @Output("shipments")
    fun shipments(): MessageChannel

    @Output("communication")
    fun communication(): MessageChannel
}
