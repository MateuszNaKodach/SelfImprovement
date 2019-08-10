package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part2

import kotlin.system.measureTimeMillis

const val SEQUENTIAL_THRESHOLD = 5000

fun compute(array: IntArray, low: Int, high: Int): Long {
    // println("low: $low, high: $high on ${Thread.currentThread().name}")
    return if (high - low < SEQUENTIAL_THRESHOLD) {
        (low until high)
            .map { array[it].toLong() }
            .sum()
    } else {
        val mid = low + (high - low) / 2
        val left = compute(array, low, mid)
        val right = compute(array, mid, high)
        left + right
    }
}

fun main(args: Array<String>) {
    val list = mutableListOf<Int>()
    var limit = 20_000_000

    while (limit > 0) {
        list.add(limit--)
    }
    var result = 0L
    var time = 0L

    time = measureTimeMillis {
        result = compute(list.toIntArray(), 0, list.size)
    }

    println("$result in ${time}ms")
}
