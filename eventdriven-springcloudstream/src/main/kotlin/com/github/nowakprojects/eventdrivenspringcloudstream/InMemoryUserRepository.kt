package com.github.nowakprojects.eventdrivenspringcloudstream

import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class InMemoryUserRepository : UserRepository {

    private val users = ConcurrentHashMap<UUID, User>()

    override fun save(user: User) {
        users[user.uuid] = user
    }

    override fun find(uuid: UUID): User? =
        users[uuid]

}