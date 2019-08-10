package com.github.nowakprojects.learning.kotlin.resocoder.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun main() {
    exampleWithContext()
}

fun delayed(function: () -> Unit) {
    Thread.sleep(1000)
    function()
}


suspend fun delayedCoroutine(function: () -> Unit) {
    delay(1000)
    function()
}

fun exampleBlocking() {
    println("one")
    delayed { println("two") }
    println("three")
}

fun exampleCoroutineBlocking() = runBlocking {
    println("one")
    delayedCoroutine { println("two") }
    println("three")
}

fun exampleBlockingDispatcher() {
    runBlocking(Dispatchers.Default) {
        println("one - from thread ${Thread.currentThread().name}")
        delayedCoroutine {
            println("two - from thread ${Thread.currentThread().name}")
        }
    }
    println("three - from thread ${Thread.currentThread().name}")
}

fun exampleLaunchGlobal() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")
    GlobalScope.launch {
        delayedCoroutine {
            println("two - from thread ${Thread.currentThread().name}")
        }
    }
    println("three - from thread ${Thread.currentThread().name}")
}


fun exampleLaunchGlobalWaiting() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")
    val job = GlobalScope.launch {
        delayedCoroutine {
            println("two - from thread ${Thread.currentThread().name}")
        }
    }
    println("three - from thread ${Thread.currentThread().name}")
    job.join()
}

fun exampleLaunchCoroutineScope() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    val customDispatcher = Executors.newFixedThreadPool(2)
        .asCoroutineDispatcher()

    launch(customDispatcher) {
        delayedCoroutine {
            println("two - from thread ${Thread.currentThread().name}")
        }
    }
    println("three - from thread ${Thread.currentThread().name}")
    (customDispatcher.executor as ExecutorService).shutdown()
}


suspend fun calculateHardThings(startNum: Int): Int {
    delay(1000)
    return startNum * 10
}

fun exampleAsyncAwait() = runBlocking {
    val timeTaken = measureTimeMillis {
        val deferred1 = async {
            calculateHardThings(10)
        }
        val deferred2 = async {
            calculateHardThings(20)
        }
        val deferred3 = async {
            calculateHardThings(30)
        }

        val sum = deferred1.await() + deferred2.await() + deferred3.await()
        println("async/await result = $sum")
    }
    println("Time taken: $timeTaken")
}


fun exampleWithContext() = runBlocking {
    val timeTaken = measureTimeMillis {
        val result1 = withContext(Dispatchers.Default) {
            calculateHardThings(10)
        }
        val result2 = withContext(Dispatchers.Default) {
            calculateHardThings(20)
        }
        val result3 = withContext(Dispatchers.Default) {
            calculateHardThings(30)
        }

        val sum = result1 + result2 + result3
        println("async/await result = $sum")
    }
    println("Time taken: $timeTaken")
}
