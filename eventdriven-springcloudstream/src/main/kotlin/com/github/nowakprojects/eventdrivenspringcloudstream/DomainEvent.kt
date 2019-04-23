package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant

interface DomainEvent {
    val occuredAt: Instant
}
