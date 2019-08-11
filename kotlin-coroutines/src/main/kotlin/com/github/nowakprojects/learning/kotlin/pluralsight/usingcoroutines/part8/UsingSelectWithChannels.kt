package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part8

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun producer1() = GlobalScope.produce<String> {
    while (true) {
        delay(200)
        send("from producer 1")
    }
}

fun producer2() = GlobalScope.produce<String> {
    while (true) {
        delay(300)
        send("from producer 2")
    }
}

suspend fun selector(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>) {

    select<Unit> {
        message1.onReceive { println(it) }
        message2.onReceive { println(it) }
    }

}

fun main(args: Array<String>) = runBlocking {
    val m1 = producer1()
    val m2 = producer2()

    repeat(15) {
        selector(m1, m2)
    }
}