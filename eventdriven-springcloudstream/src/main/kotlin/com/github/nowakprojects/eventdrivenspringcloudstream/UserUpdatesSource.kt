package com.github.nowakprojects.eventdrivenspringcloudstream

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface UserUpdatesSource {

    @Output("userUpdatesChannel")
    fun userUpdates(): MessageChannel
}
