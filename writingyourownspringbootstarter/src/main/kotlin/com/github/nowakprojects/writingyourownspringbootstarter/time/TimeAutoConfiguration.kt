package com.github.nowakprojects.writingyourownspringbootstarter.time

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(TimeProperties::class)
class TimeAutoConfiguration internal constructor(private val timeProperties: TimeProperties) {

    @Bean
    fun getTimeProvider() = if(timeProperties.fixed) FixedTimeProvider(SystemTimeProvider) else SystemTimeProvider

}