package com.github.nowakprojects.timetraveler;

import java.time.*;

public interface TimeProvider {
    LocalTime getCurrentLocalTime();

    LocalDate getCurrentLocalDate();

    ZoneId getCurrentZoneId();

    default LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.of(getCurrentLocalDate(), getCurrentLocalTime());
    }

    default LocalDateTime getStartOfCurrentDay() {
        return getCurrentLocalDate().atStartOfDay();
    }

    default ZonedDateTime getCurrentZonedDateTime() {
        return getCurrentLocalDateTime().atZone(getCurrentZoneId());
    }

    default Instant getCurrentInstant() {
        return getCurrentZonedDateTime().toInstant();
    }
}
