package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part7

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

fun main(args: Array<String>) {
    run()
    runBlocking {
        delay(5000)
    }
}

const val NUMBER_OF_WORKERS = 10
const val TOTAL_WORK = 20

data class Work(val x: Long = 0, val y: Long = 0, val z: Long = 0)

private object RandomRangeSingleton : Random()

suspend fun worker(input: Channel<Work>, output: Channel<Work>) {
    input.consumeEach {
        val result = it.copy(z = it.x * it.y)
        delay(result.z)
        output.send(result)
    }
}

fun run() {
    val input = Channel<Work>()
    val output = Channel<Work>()

    repeat(NUMBER_OF_WORKERS) {
        GlobalScope.launch {
            worker(input, output)
        }
    }

    GlobalScope.launch {
        sendLotsOfWork(input)
    }

    GlobalScope.launch {
        receiveLotsOfResults(output)
    }
}

suspend fun sendLotsOfWork(input: Channel<Work>) {
    repeat(TOTAL_WORK) {
        input.send(Work((0L..100).random(), (0L..10).random()))
    }
}

suspend fun receiveLotsOfResults(results: Channel<Work>) {
    results.consumeEach {
        println("${it.x}*${it.y} = ${it.z}")
    }
}

fun ClosedRange<Long>.random() = (RandomRangeSingleton.nextInt((endInclusive.toInt() + 1) - start.toInt()) + start)