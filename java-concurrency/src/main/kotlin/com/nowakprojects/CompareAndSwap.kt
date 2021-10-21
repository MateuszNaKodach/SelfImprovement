package com.nowakprojects

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

fun main(){
    val atomicBoolean = AtomicInteger(false)
    atomicBoolean.incrementAndGet(false);
}
