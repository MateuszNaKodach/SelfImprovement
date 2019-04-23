package com.github.nowakprojects.timetraveler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Time properties for manipulating application time.
 * If you want to have use of class benefits you should get time only by interface TimeProvider.
 *
 * @see TimeProvider
 */
@EnableConfigurationProperties(CurrentTimeProperties.class)
@ConfigurationProperties(prefix = CurrentTimeProperties.PREFIX)
@Validated
@Setter
@Slf4j
public class CurrentTimeProperties {

    static final String PREFIX = "timetraveler.current-time";

    /**
     * Indicate if time should be fixed during application run.
     */
    @NotNull
    private Boolean fixed = Boolean.FALSE;

    /**
     * Application time zone, by default is system zone.
     * Valid value for Poland is 'Europe/Warsaw'.
     */
    @NotNull
    private ZoneId zone = ZoneId.systemDefault();

    /**
     * Application date, by default is system date.
     * It should be passed in ISO-8601 (yyyy-MM-dd) format, for example 2019-04-24.
     * If not set current system date is used.
     */
    private LocalDate date = null;

    /**
     * Application time, by default is system time.
     * Example values: '"10:15"' or '"10:15:30"' or '"10:15:30.999"'
     * If not set current system time is used.
     */
    private LocalTime time = null;


    Clock getClock() {
        LocalDate clockDate = nonNull(date) ? date : LocalDate.now(zone);
        LocalTime clockTime = nonNull(time) ? time : LocalTime.now(zone);
        return fixed ? Clock.fixed(LocalDateTime.of(clockDate, clockTime).atZone(zone).toInstant(), zone)
                : Clock.system(zone);
    }

    @Component
    @ConfigurationPropertiesBinding
    private static class LocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String value) {
            return isNull(value) ? null : LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    @Component
    @ConfigurationPropertiesBinding
    private static class LocalTimeConverter implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String value) {
            return isNull(value) ? null : LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    @Component
    @ConfigurationPropertiesBinding
    private static class ZoneIdConverter implements Converter<String, ZoneId> {
        @Override
        public ZoneId convert(String value) {
            return isNull(value) ? null : ZoneId.of(value);
        }
    }

    @Component
    @ConfigurationPropertiesBinding
    private static class BooleanConverter implements Converter<String, Boolean> {
        @Override
        public Boolean convert(String value) {
            return value.toLowerCase().equals(Boolean.TRUE.toString()) ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}

