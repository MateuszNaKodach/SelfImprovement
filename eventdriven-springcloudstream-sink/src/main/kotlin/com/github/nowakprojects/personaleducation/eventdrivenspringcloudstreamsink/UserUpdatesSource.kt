package com.github.nowakprojects.personaleducation.eventdrivenspringcloudstreamsink

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface UserUpdatesSource {

    @Output("userUpdatesChannel")
    fun userUpdates(): MessageChannel
}
