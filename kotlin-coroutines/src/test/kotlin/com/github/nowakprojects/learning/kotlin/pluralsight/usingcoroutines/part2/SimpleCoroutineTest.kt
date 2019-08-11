package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part2

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

//https://app.pluralsight.com/player?course=kotlin-using-coroutines&author=kevin-jones&name=439068f6-0dd2-4507-bf07-df53c08d9a2a&clip=3&mode=live
//testowy001@niepodam.pl niepodam123!


internal class SimpleCoroutineTest : Spek({


    describe("Kotlin Coroutines") {

        it("Suspend function") {
            runBlocking {
                doWork()
                assertThat(1 + 1).isEqualTo(2)
            }
        }

    }

})