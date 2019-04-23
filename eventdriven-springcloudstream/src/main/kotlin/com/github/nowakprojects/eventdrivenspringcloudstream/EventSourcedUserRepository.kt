package com.github.nowakprojects.eventdrivenspringcloudstream

import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class EventSourcedUserRepository : UserRepository {

    private val users = ConcurrentHashMap<UUID, List<UserDomainEvent>>()

    override fun save(user: User) {
        val newChanges = user.getChanges()
        val persistedChanges = users[user.uuid] ?: listOf()
        users[user.uuid] = newChanges.plus(persistedChanges)
        user.flushChanges()
    }

    override fun find(uuid: UUID): User? =
            if (!users.containsKey(uuid)) {
                null
            } else {
                User.recreateFrom(uuid, users[uuid] ?: listOf())
            }

}
