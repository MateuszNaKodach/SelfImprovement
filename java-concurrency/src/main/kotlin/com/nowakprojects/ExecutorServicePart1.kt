package com.nowakprojects

import java.util.concurrent.*

fun tutorial() {

    val executorService = Executors.newFixedThreadPool(3)

    (0..3).forEach {
        executorService.execute(runnable("Task $it"))
    }

    executorService.shutdown()
}

fun runnable(message: String): Runnable {
    return Runnable {
        Thread.sleep(1000)
        println("${Thread.currentThread().name}: $message")
    }
}


fun tutorial2() {
    val corePoolSize = 1
    val maxPoolSize = 1
    val keepAliveTime = 3000L
    val queueSize = 1

    val executorService = ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        TimeUnit.MILLISECONDS,
        ArrayBlockingQueue(queueSize)
    )


    (0..2).forEach {
        println("Schedule task $it")
        executorService.execute(runnable("Task $it"))
    }

}


fun tutorialSubmit() {
    val executorService = Executors.newFixedThreadPool(1)
    val future = executorService.submit(Callable { "Callable result from thread ${Thread.currentThread().name}" })

    val result = future.get()
    println("Result $result")

}

fun main() {
    tutorialSubmit()
}
