package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main(args: Array<String>) = runBlocking {

    val channel = produceNumbers()
    repeat(5) {
        val value = channel.receive()
        println("receive: $value on ${Thread.currentThread().name}")
    }
}

fun produceNumbers(): Channel<Int> {
    val channel = Channel<Int>()

    val job = GlobalScope.launch {
        for (x in 1..5) {
            println("send $x on ${Thread.currentThread().name}")
            channel.send(x)
        }
    }
    return channel
}