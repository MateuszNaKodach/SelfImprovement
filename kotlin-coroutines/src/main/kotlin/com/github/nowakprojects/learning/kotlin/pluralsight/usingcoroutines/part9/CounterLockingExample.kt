package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part9

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis


// 1. show counter class and explain what run does
// 2. run the base counter and show it doesn't work
// 3. add the finr grained code and show it works but is much slower
// 4. change it to course grained
// 5. Show the mutex code and show how slow it is
// 6. Finally show the 'atomic' code as a solution in thie case

open class Counter {
    private var counter = 0


    open suspend fun increment() {
        counter++
    }

    open var value: Int
        get() = counter
        set(value) {
            counter = value
        }

    suspend fun run(context: CoroutineContext, numberOfJobs: Int, count: Int, action: suspend () -> Unit): Long {
        // action is repeated by each coroutine
        return measureTimeMillis {
            val jobs = List(numberOfJobs) {
                GlobalScope.launch(context) {
                    repeat(count) { action() }
                }
            }
            jobs.forEach { it.join() }
        }
    }

}

class AtomicCounter : Counter() {
    var counter = AtomicInteger()

    override suspend fun increment() {
        counter.incrementAndGet()
    }

    override var value: Int
        get() = counter.get()
        set(value) {
            counter.set(value)
        }
}

class MutexCounter : Counter() {
    val mutex = Mutex()
    var counter: Int = 0

    override suspend fun increment() {
        mutex.withLock {
            counter++
        }
    }

    override var value: Int
        get() = counter
        set(value) {
            counter = value
        }
}


fun main(args: Array<String>) = runBlocking<Unit> {
    val jobs = 1000 // number of coroutines to launch
    val count = 1000 // work in each coroutine

    var counter = Counter()

    // warm up code
    counter.run(Dispatchers.Default, jobs, count) {
        counter.increment()
    }

    counter.value = 0

    var time = counter.run(Dispatchers.Default, jobs, count) {
        counter.increment()
    }
    logResult("Base counter", jobs, count, time, counter)

    counter.value = 0

    // use single thread context - fine grained
    val ctx = newSingleThreadContext("Counter")
    time = counter.run(Dispatchers.Default, jobs, count) {
        withContext(ctx) {
            counter.increment()
        }
    }
    logResult("Fine grained", jobs, count, time, counter)

    counter.value = 0
    // use single thread context - course grained
    time = counter.run(ctx, jobs, count) {
        counter.increment()
    }
    logResult("Course grained", jobs, count, time, counter)

    counter = AtomicCounter()
    time = counter.run(Dispatchers.Default, jobs, count) {
        counter.increment()
    }
    logResult("Atomic", jobs, count, time, counter)


    counter = MutexCounter()
    time = counter.run(Dispatchers.Default, jobs, count) {
        counter.increment()
    }
    logResult("Mutex", jobs, count, time, counter)


}

private fun logResult(counterType: String, n: Int, k: Int, time: Long, c: Counter) {
    println("${counterType} completed ${n * k} actions in $time ms")
    println("Counter  : ${c.value}")
}

