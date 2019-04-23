package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.TimeProvider
import java.util.*

internal class User private constructor(
        val uuid: UUID,
        private var nickname: String,
        @Transient
        private var timeProvider: TimeProvider,
        private var state: State = State.INITIALIZED
) {

    private val changes = mutableListOf<UserDomainEvent>()

    private enum class State {
        INITIALIZED, ACTIVATED, DEACTIVATED
    }

    companion object {
        fun withUUID(uuid: UUID, timeProvider: TimeProvider) = User(uuid, "", timeProvider)

        fun withNickname(nickname: String, timeProvider: TimeProvider) = User(UUID.randomUUID(), nickname, timeProvider)

        fun recreateFrom(uuid: UUID, domainEvents: List<UserDomainEvent>, timeProvider: TimeProvider) =
                domainEvents.fold(withUUID(uuid, timeProvider)) { acc: User, userDomainEvent: UserDomainEvent -> acc.handleEvent(userDomainEvent) }
    }

    init {
        userNicknameChanged(UserDomainEvent.UserNicknameChanged(nickname, timeProvider.currentInstant))
    }

    fun handleEvent(event: UserDomainEvent): User =
            when (event) {
                is UserDomainEvent.UserActivated -> userActivated(event)
                is UserDomainEvent.UserDeactivated -> userDeactivated(event)
                is UserDomainEvent.UserNicknameChanged -> userNicknameChanged(event)
            }


    fun activate() {
        if (isActivated()) {
            throw IllegalStateException()
        }
        userActivated(UserDomainEvent.UserActivated(timeProvider.currentInstant))
    }

    private fun userActivated(event: UserDomainEvent.UserActivated) = apply {
        state = State.ACTIVATED
        changes.add(event)
    }

    fun deactivate() {
        if (isDeactivated()) {
            throw IllegalStateException()
        }
        userDeactivated(UserDomainEvent.UserDeactivated(timeProvider.currentInstant))
    }

    private fun userDeactivated(event: UserDomainEvent.UserDeactivated) = apply {
        state = State.DEACTIVATED
        changes.add(event)
    }

    fun changeNicknameTo(newNickname: String) {
        if (isDeactivated()) {
            throw IllegalStateException("Nickname cannot be changed if user is deactivated!")
        }
        userNicknameChanged(UserDomainEvent.UserNicknameChanged(newNickname, timeProvider.currentInstant))
    }

    fun userNicknameChanged(event: UserDomainEvent.UserNicknameChanged) = apply {
        nickname = event.newNickname
        changes.add(event)
    }

    fun isActivated() = state == State.ACTIVATED

    fun isDeactivated() = state == State.DEACTIVATED

    fun getChanges() = listOf<UserDomainEvent>(*changes.toTypedArray())

    fun getNickname() = nickname
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    fun flushChanges() {
        changes.clear()
    }

}
