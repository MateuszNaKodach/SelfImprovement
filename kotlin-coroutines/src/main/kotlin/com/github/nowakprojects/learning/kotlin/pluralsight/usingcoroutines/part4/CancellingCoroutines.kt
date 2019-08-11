package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part4

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val job = launch {
        repeat(1000) {
            print(".")
           yield()
        }
    }

    delay(100)

    //job.cancel()
    //job.join()

    job.cancelAndJoin()

    println("done")

}