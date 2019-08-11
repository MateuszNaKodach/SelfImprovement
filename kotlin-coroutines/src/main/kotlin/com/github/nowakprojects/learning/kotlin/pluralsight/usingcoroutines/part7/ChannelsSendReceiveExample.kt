package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>()

    val job = launch {
        for (x in 1..5) {
            println("send $x")
            channel.send(x)
        }
    }

    repeat(5) {
        val value = channel.receive()
        println("receive: $value")
    }
    job.join()
}