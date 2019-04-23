package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.TestClockTimeProvider
import com.github.nowakprojects.timetraveler.TimeProvider
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe
import java.time.Instant
import java.time.LocalTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class TimeTravelingWithEventSourcingSpecification : Spek({
    Feature("time traveling with event sourcing") {

        Scenario("events up to specific point in the past") {

            val timeProvider: TestClockTimeProvider
                    by memoized { TestClockTimeProvider.withFixedTime(LocalTime.of(10, 0)) }
            val repository: UserRepository by memoized { EventSourcedUserRepository(timeProvider) }
            val userUUID = UUID.randomUUID()

            Given("current time is 10:00") {
                timeProvider.setCurrentTimeTo(LocalTime.of(10, 0))
            }

            And("user is saved") {
                repository.save(User.withUUID(userUUID, timeProvider))
            }

            When("we activated the user at 10:10") {
                timeProvider.setCurrentTimeTo(LocalTime.of(10, 10))
                repository.find(userUUID)!!
                        .apply {
                            activate()
                            repository.save(this)
                        }
            }

            Then("if we load user data with time set to 10:11 is should be activated") {
                timeProvider.setCurrentTimeTo(LocalTime.of(10, 11))
                val user = repository.find(userUUID)!!
                assertTrue(user.isActivated())
            }

            And("if we load user data with time set to 10:01 is should not be activated") {
                timeProvider.setCurrentTimeTo(LocalTime.of(10, 1))
                val user = repository.find(userUUID)!!
                assertFalse(user.isActivated())
            }


        }

    }


})
