package com.github.nowakprojects.personaleducation.springwithrabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SpringWithRabbitmqApplication
fun main(args: Array<String>) {
    runApplication<SpringWithRabbitmqApplication>(*args)
}
