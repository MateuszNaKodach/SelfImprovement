package com.github.nowakprojects.eventdrivenspringcloudstream

import com.github.nowakprojects.timetraveler.CurrentTimeProperties
import com.github.nowakprojects.timetraveler.TimeProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct

@EnableScheduling
@EnableBinding(Source::class)
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
internal class RandomUsers(val repository: UserRepository, val timeProvider: TimeProvider) {

    var usersCount = AtomicInteger(0)

    @Scheduled(fixedRate = 2000L)
    fun saveRandomUsers() {
        User.withNickname("Nickname${usersCount.getAndIncrement()}", timeProvider)
                .also { user ->
                    repository.save(user)
                }
    }
}
