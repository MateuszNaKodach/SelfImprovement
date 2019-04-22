package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant

class UserActivated(override val occuredAt: Instant) : DomainEvent
