package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant
import java.util.*

internal class User private constructor(
        val uuid: UUID,
        private var nickname: String,
        private var state: State = State.INITIALIZED
) {

    private val changes = mutableListOf<UserDomainEvent>()

    private enum class State {
        INITIALIZED, ACTIVATED, DEACTIVATED
    }

    companion object {
        fun withUUID(uuid: UUID) = User(uuid, "")

        fun withNickname(nickname: String) = User(UUID.randomUUID(), nickname)

        fun recreateFrom(uuid: UUID, domainEvents: List<UserDomainEvent>) =
                domainEvents.fold(withUUID(uuid)) { acc: User, userDomainEvent: UserDomainEvent -> acc.handleEvent(userDomainEvent) }
    }

    init {
        userNicknameChanged(UserDomainEvent.UserNicknameChanged(nickname, Instant.now()))
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
        userActivated(UserDomainEvent.UserActivated(Instant.now()))
    }

    private fun userActivated(event: UserDomainEvent.UserActivated) = apply {
        state = State.ACTIVATED
        changes.add(event)
    }

    fun deactivate() {
        if (isDeactivated()) {
            throw IllegalStateException()
        }
        userDeactivated(UserDomainEvent.UserDeactivated(Instant.now()))
    }

    private fun userDeactivated(event: UserDomainEvent.UserDeactivated) = apply {
        state = State.DEACTIVATED
        changes.add(event)
    }

    fun changeNicknameTo(newNickname: String) {
        if (isDeactivated()) {
            throw IllegalStateException("Nickname cannot be changed if user is deactivated!")
        }
        userNicknameChanged(UserDomainEvent.UserNicknameChanged(newNickname, Instant.now()))
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
