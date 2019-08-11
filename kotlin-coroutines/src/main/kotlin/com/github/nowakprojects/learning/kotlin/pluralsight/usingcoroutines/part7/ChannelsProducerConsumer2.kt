package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.consumeEachIndexed
import kotlinx.coroutines.channels.produce

fun main(args: Array<String>) = runBlocking {
    val channel = produceNumbersJob()
    val job = GlobalScope.launch(newSingleThreadContext("ChannelConsumer")) {
        channel.consumeEachIndexed {
            println("receive #${it.index}: ${it.value} on ${Thread.currentThread().name}")
        }
    }
    job.join()
    println("DONE")
}

@ExperimentalCoroutinesApi
fun produceNumbersJob() = GlobalScope.produce<Int> {
    launch {
        for (x in 1..5) {
            println("send $x on ${Thread.currentThread().name}")
            send(x)
        }
    }
}