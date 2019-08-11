package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main(args: Array<String>) = runBlocking {
    val producer = produceNumbersInf()
    val squares = squareNumbers(producer)

    (1..5).forEach {
        println("receive #$it: ${squares.receive()} on ${Thread.currentThread().name}")
    }

}

@ExperimentalCoroutinesApi
fun produceNumbersInf() = GlobalScope.produce<Int> {
    var x = 1
    while (true) {
        send(x++)
        println("Send $x")
    }
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun squareNumbers(numbers: ReceiveChannel<Int>) = GlobalScope.produce<Int> {
    numbers.consumeEach {
        send(it * it)
    }
}