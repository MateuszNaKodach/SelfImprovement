package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part2

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main(args: Array<String>) {

    //runThread()
    //runCoroutine()
    //runManyThreads()
    runManyCoroutines()
}


suspend fun doWork(){
    delay(2000)
}

fun runManyCoroutines() {
    val result = AtomicInteger()
    for (i in 1..1_500_000) {
        GlobalScope.launch {
            result.getAndIncrement()
        }
    }
    Thread.sleep(1000)
    println(result.get())
}


fun runManyThreads() {
    val result = AtomicInteger()

    for (i in 1..150_000) {
        thread(start = true) {
            result.getAndIncrement()
        }
    }
    Thread.sleep(1000)
    println(result.get())
}

fun runCoroutine() = runBlocking {
    launch {
        delay(1000)
        println("World")
    }
    print("Hello, ")

}

fun runThread() {
    thread {
        sleep(1000)
        println("World")
    }

    print("Hello, ")
    Thread.sleep(1500)
}