package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant

class UserDeactivated(override val occuredAt: Instant) : DomainEvent
