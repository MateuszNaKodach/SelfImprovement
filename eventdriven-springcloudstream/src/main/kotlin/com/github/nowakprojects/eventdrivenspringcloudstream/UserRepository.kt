package com.github.nowakprojects.eventdrivenspringcloudstream

import java.util.*

internal interface UserRepository {
    fun save(user: User)
    fun find(uuid: UUID): User?
}