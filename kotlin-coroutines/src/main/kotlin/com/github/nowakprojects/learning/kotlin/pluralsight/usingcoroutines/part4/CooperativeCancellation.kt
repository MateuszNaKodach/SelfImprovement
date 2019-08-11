package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part4

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        val context = CoroutineName("name") + coroutineContext

        try {
            repeat(20) {
                print(".")
                Thread.sleep(1)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled")
        } finally {
        }
    }

    delay(1)
    job.cancelAndJoin()
    println("done")

}