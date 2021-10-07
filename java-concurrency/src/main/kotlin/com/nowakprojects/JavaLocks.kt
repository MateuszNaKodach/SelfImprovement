package com.nowakprojects

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
    lockBasics()
}

fun lockBasics(){
    val lock = ReentrantLock(false)

    // fixme: Kotlin another behavior?
//    val thread1 = thread(name = "Thread 1") { lockSleepUnlock(lock, 1000) }
//    val thread2 = thread(name = "Thread 2") { lockSleepUnlock(lock, 1000) }
//    val thread3 = thread(name = "Thread 3") { lockSleepUnlock(lock, 1000) }

    val runnable = Runnable { lockSleepUnlock(lock, 1000) }

    val thread1 = Thread(runnable, "Thread 1")
    val thread2 = Thread(runnable, "Thread 2")
    val thread3 = Thread(runnable, "Thread 3")

    thread1.start()
    thread2.start()
    thread3.start()
}

fun lockSleepUnlock(lock: Lock, timeMillis: Long){
    try {
        lock.lock()
        val currentThread = Thread.currentThread().name
        println("$currentThread holds the lock.")
        Thread.sleep(timeMillis)
    } finally {
        lock.unlock()
    }
}
