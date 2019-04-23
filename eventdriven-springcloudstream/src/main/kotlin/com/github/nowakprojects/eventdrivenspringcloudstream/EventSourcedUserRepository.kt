package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.TimeProvider
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class EventSourcedUserRepository(private val timeProvider: TimeProvider) : UserRepository {

    private val users = ConcurrentHashMap<UUID, List<UserDomainEvent>>()

    override fun save(user: User) {
        val newChanges = user.getChanges()
        val persistedChanges = getEventsUpTo(user.uuid, timeProvider.currentInstant)
        users[user.uuid] = newChanges.plus(persistedChanges)
        user.flushChanges()
    }

    override fun find(uuid: UUID): User? =
            find(uuid, timeProvider.currentInstant)

    private fun find(uuid: UUID, timestamp: Instant): User? =
            if (!users.containsKey(uuid)) {
                null
            } else {
                User.recreateFrom(uuid, getEventsUpTo(uuid, timestamp), timeProvider)
            }

    private fun getEventsUpTo(uuid: UUID, timestamp: Instant) =
            (users[uuid] ?: listOf()).filter { !it.occuredAt.isAfter(timestamp) }
}
