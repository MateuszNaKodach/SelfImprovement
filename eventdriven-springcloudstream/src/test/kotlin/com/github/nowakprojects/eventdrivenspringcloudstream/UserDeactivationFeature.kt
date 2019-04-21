package com.github.nowakprojects.eventdrivenspringcloudstream

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object UserDeactivationFeature : Spek(
        {
            Feature("User deactivation") {

                val user by memoized { User.new() }

                Scenario("Deactivated user cannot change nickname") {

                    var thrownException: IllegalStateException? = null

                    Given("user is deactivated") {
                        user.deactivate()
                    }

                    When("user try to change nickname") {
                        try {
                            user.changeNicknameTo("Mateusz")
                        } catch (e: IllegalStateException) {
                            thrownException = e
                        }
                    }

                    Then("is not allowed") {
                        thrownException != null
                    }

                }

                Scenario("Activated user can change nickname") {

                    Given("user is activated") {
                        user.activate()
                    }

                    When("user try to change nickname") {
                        user.changeNicknameTo("Mateusz")
                    }

                    Then("the user nickname should be changed") {
                        assertEquals(user.getNickname(), "Mateusz")
                    }

                }

                Scenario("New user can be activated") {

                    When("try to activate new user") {
                        user.activate()
                    }

                    Then("user should be activated") {
                        assertTrue(user.isActivated())
                    }

                }


                Scenario("Activated user can be deactivated") {

                    Given("user is activated") {
                        user.activate()
                    }

                    When("try to deactivate") {
                        user.deactivate()
                    }

                    Then("user should be deactivated") {
                        assertTrue(user.isDeactivated())
                    }


                }

                Scenario("Activated user cannot be activated") {

                    var thrownException: IllegalStateException? = null

                    Given("user is activated") {
                        user.activate()
                    }

                    When("try to activate") {
                        try {
                            user.activate()
                        } catch (e: IllegalStateException) {
                            thrownException = e
                        }
                    }

                    Then("is not allowed") {
                        thrownException != null
                    }


                }

                Scenario("Deactivated user cannot be deactivated") {

                    var thrownException: IllegalStateException? = null

                    Given("user is deactivated") {
                        user.deactivate()
                    }

                    When("try to deactivate") {
                        try {
                            user.deactivate()
                        } catch (e: IllegalStateException) {
                            thrownException = e
                        }
                    }

                    Then("is not allowed") {
                        thrownException != null
                    }

                }
            }
        }
)