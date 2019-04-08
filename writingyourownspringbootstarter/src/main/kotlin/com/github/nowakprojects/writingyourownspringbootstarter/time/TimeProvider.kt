package com.github.nowakprojects.writingyourownspringbootstarter.time

import java.time.Instant

interface TimeProvider {
    fun getCurrentInstant(): Instant
}