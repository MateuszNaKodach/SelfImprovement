package com.github.nowakprojects.timetraveler;

import java.time.*;

public class TestClockTimeProvider implements TimeProvider {

    private Clock clock;

    private TestClockTimeProvider(Clock clock) {
        this.clock = clock;
    }

    public static TestClockTimeProvider withFixedTime(LocalTime localTime) {
        return withFixedTime(localTime, ZoneId.systemDefault());
    }

    public static TestClockTimeProvider withFixedTime(LocalTime localTime, ZoneId zoneId) {
        return new TestClockTimeProvider(Clock.fixed(currentInstantWithTime(localTime), zoneId));
    }

    @Override
    public LocalTime getCurrentLocalTime() {
        return LocalTime.now(clock);
    }

    @Override
    public ZoneId getCurrentZoneId() {
        return clock.getZone();
    }

    public void setCurrentTimeTo(LocalTime localTime) {
        clock = Clock.fixed(currentInstantWithTime(localTime), clock.getZone());
    }

    private static Instant currentInstantWithTime(LocalTime localTime) {
        return ZonedDateTime.of(LocalDate.now().atTime(localTime).withSecond(0), ZoneId.systemDefault()).toInstant();
    }

    @Override
    public LocalDate getCurrentLocalDate() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone()).toLocalDate();
    }
}
