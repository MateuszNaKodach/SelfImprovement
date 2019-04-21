package com.github.nowakprojects.eventdrivenspringcloudstream

import java.util.*

internal class User private constructor(val id: String) {

    companion object {
        fun new() = User(UUID.randomUUID().toString())
    }

    fun activate() {

    }

    fun deactivate() {

    }

    fun changeNicknameTo(newNickname: String) {
        throw IllegalStateException("Nickname cannot be changed if user is deactivated!")
    }

    fun isActivated() = false

    fun isDeactivated() = false

    fun getNickname() = ""
}