package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.TestClockTimeProvider
import com.github.nowakprojects.timetraveler.TimeProvider
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.Instant
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class EventSourcedUserRepositorySpecification : Spek({
    describe("a in memory user repository") {


        val timeProvider: TestClockTimeProvider
                by memoized { TestClockTimeProvider.withFixedTime(LocalTime.of(10, 0)) }
        val repository: UserRepository by memoized { EventSourcedUserRepository(timeProvider) }

        it("should be able to save and load user") {
            val savedUser = User.withNickname("Nickname", timeProvider)
                    .also {
                        it.activate()
                        it.changeNicknameTo("Mateusz")
                    }.also {
                        repository.save(it)
                    }

            timeProvider.setCurrentTimeTo(LocalTime.of(10, 10))
            val loadedUser = repository.find(savedUser.uuid)

            assertNotNull(loadedUser)
            assertEquals(loadedUser, savedUser)
            assertEquals("Mateusz", loadedUser?.getNickname())
            assertTrue(loadedUser?.isActivated()!!)
        }

    }

})
