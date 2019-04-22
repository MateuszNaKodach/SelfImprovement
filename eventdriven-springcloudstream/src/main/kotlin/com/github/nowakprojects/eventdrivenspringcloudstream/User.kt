package com.github.nowakprojects.eventdrivenspringcloudstream

import java.time.Instant
import java.util.*

internal class User private constructor(
        val uuid: UUID,
        private var nickname: String,
        private var state: State = State.INITIALIZED
) {


    private enum class State {
        INITIALIZED, ACTIVATED, DEACTIVATED
    }


    companion object {
        fun withNickname(nickname: String) = User(UUID.randomUUID(), nickname)
    }

    fun activate() {
        if (isActivated()) {
            throw IllegalStateException()
        }
        state = State.ACTIVATED
    }

    fun deactivate() {
        if (isDeactivated()) {
            throw IllegalStateException()
        }
        state = State.DEACTIVATED
    }

    fun changeNicknameTo(newNickname: String) {
        if (isDeactivated()) {
            throw IllegalStateException("Nickname cannot be changed if user is deactivated!")
        }
        userNicknameChanged(UserNicknameChanged(newNickname, Instant.now()))
    }

    fun userNicknameChanged(event: UserNicknameChanged){

    }

    fun isActivated() = state == State.ACTIVATED

    fun isDeactivated() = state == State.DEACTIVATED

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


}