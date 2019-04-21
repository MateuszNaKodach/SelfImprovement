package com.github.nowakprojects.eventdrivenspringcloudstream

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class InMemoryUserRepositorySpecification : Spek({
    describe("a in memory user repository") {

        val repository: UserRepository by memoized { InMemoryUserRepository() }

        it("should be able to save and load user") {
            val savedUser = User.withNickname("Nickname")
                    .also {
                        it.activate()
                        it.changeNicknameTo("Mateusz")
                    }.also {
                        repository.save(it)
                    }

            val loadedUser = repository.find(savedUser.uuid)

            assertNotNull(loadedUser)
            assertEquals(loadedUser, savedUser)
            assertEquals(loadedUser?.getNickname(), "Mateusz")
            assertTrue(loadedUser?.isActivated()!!)
        }

    }

})