package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant

sealed class UserDomainEvent : DomainEvent {
    data class UserActivated(override val occuredAt: Instant) : UserDomainEvent()
    data class UserDeactivated(override val occuredAt: Instant) : UserDomainEvent()
    data class UserNicknameChanged(val newNickname: String, override val occuredAt: Instant) : UserDomainEvent()
}

