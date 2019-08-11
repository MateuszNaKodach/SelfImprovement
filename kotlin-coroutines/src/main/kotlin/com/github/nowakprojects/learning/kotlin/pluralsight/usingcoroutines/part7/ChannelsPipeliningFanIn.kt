package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main(args: Array<String>) = runBlocking<Unit> {
    val channel = Channel<String>()
    launch(coroutineContext) {
        sendString(channel, "Text 1", 200L)
    }
    launch(coroutineContext) {
        sendString(channel, "Text 2", 500L)
    }

    repeat(6) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()

}

suspend fun sendString(channel: Channel<String>, stringToSend: String, interval: Long) {
    while (true) {
        delay(interval)
        channel.send(stringToSend)
    }
}
