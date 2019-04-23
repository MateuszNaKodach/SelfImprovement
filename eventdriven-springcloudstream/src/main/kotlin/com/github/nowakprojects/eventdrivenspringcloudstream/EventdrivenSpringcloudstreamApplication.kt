package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.CurrentTimeProperties
import com.github.nowakprojects.timetraveler.TimeProvider
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.Publisher
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackageClasses = [TimeProvider::class, EventdrivenSpringcloudstreamApplication::class])
@EnableConfigurationProperties(CurrentTimeProperties::class)
class EventdrivenSpringcloudstreamApplication

fun main(args: Array<String>) {
    runApplication<EventdrivenSpringcloudstreamApplication>(*args)
}

@Configuration
internal class AppConfiguration {

    @Bean
    fun userRepository(timeProvider: TimeProvider, eventPublisher: EventPublisher): UserRepository =
            EventSourcedUserRepository(timeProvider, eventPublisher)
}

@Configuration
internal class RandomUsers(val repository: UserRepository, val timeProvider: TimeProvider, val eventPublisher: EventPublisher) {

    private val log = LoggerFactory.getLogger(this::class.java)

    var usersCount = AtomicInteger(0)


    @Scheduled(fixedRate = 2000L)
    fun saveRandomUsers() {
        User.withNickname("Nickname${usersCount.getAndIncrement()}", timeProvider)
                .also { user ->
                    repository.save(user)
                }
    }

    @Scheduled(fixedRate = 1000L)
    fun publishString() {
        publish("SampleString")
    }

    @Publisher(channel = Source.OUTPUT)
    fun publish(sample: String) =
            sample.also {
                log.info("about to send: {}", it)
            }
}
