package com.github.nowakprojects.timetraveler;

import java.time.*;

class ClockTimeProvider implements TimeProvider {

    private final Clock clock;

    ClockTimeProvider(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LocalTime getCurrentLocalTime() {
        return LocalTime.now(clock);
    }

    @Override
    public ZoneId getCurrentZoneId(){
        return clock.getZone();
    }

    @Override
    public LocalDate getCurrentLocalDate() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone()).toLocalDate();
    }
}
