package com.github.nowakprojects.writingyourownspringbootstarter.time

import java.time.Clock
import java.time.Instant

class FixedTimeProvider internal constructor(systemTimeProvider: SystemTimeProvider) : TimeProvider {

    private val clock = Clock.fixed(systemTimeProvider.getClock().instant(), systemTimeProvider.getClock().zone)

    override fun getCurrentInstant(): Instant = clock.instant()
}