package com.nowakprojects

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
    lockBasics()
}

fun lockBasics(){
    val lock = ReentrantLock(true)

    try{
        thread(name = "Thread 1") { lockSleepUnlock(lock, 1000) }
        thread(name = "Thread 2") { lockSleepUnlock(lock, 1000) }
        thread(name = "Thread 3") { lockSleepUnlock(lock, 1000) }
    } catch (e: Exception){
        println("Error occurred $e")
    }
}

fun lockSleepUnlock(lock: Lock, timeMillis: Long){
    try {
        lock.lock()
        val currentThread = Thread.currentThread().name
        println("$currentThread holds the lock.")
        Thread.sleep(timeMillis)
    }catch (e: Exception){
        println("Error occurred $e")
      throw e
    } finally {
        lock.unlock()
    }
}
