package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

//FAN-OUT
fun main(args: Array<String>) = runBlocking {
    val producer = produceNumbersWithDelay()
    consumer(1, producer)
    consumer(2, producer)
    println("Launched")
    delay(950)
    producer.cancel()
}

@ExperimentalCoroutinesApi
fun produceNumbersWithDelay() = GlobalScope.produce<Int> {
    var x = 1
    while (true) {
        send(x++)
        delay(100)
    }
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun consumer(id: Int, numbers: ReceiveChannel<Int>) = GlobalScope.produce<Int> {
    numbers.consumeEach {
        println("Processor #$id received $it in thread ${Thread.currentThread().name}")
    }
}