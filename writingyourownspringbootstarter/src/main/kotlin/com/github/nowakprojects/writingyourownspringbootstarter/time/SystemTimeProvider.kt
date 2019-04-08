package com.github.nowakprojects.writingyourownspringbootstarter.time

import java.time.Clock
import java.time.Instant

internal object SystemTimeProvider : TimeProvider {

    private val clock = Clock.systemDefaultZone()

    override fun getCurrentInstant():Instant = clock.instant()
    fun getClock():Clock = clock
}