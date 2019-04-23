package com.github.nowakprojects.timetraveler;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@AutoConfigureAfter(value = CurrentTimeProperties.class)
class TimeConfiguration {

    private final CurrentTimeProperties timeProperties;

    public TimeConfiguration(CurrentTimeProperties timeProperties) {
        this.timeProperties = timeProperties;
    }

    @Bean
    Clock getClock() {
        return timeProperties.getClock();
    }

    @Bean
    TimeProvider getClockTimeProvider() {
        return new ClockTimeProvider(getClock());
    }
}
