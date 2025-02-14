package org.jonatancarbonellmartinez.services;

import org.jonatancarbonellmartinez.application.annotation.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Singleton
public class DateService {
    private static final ZoneId MADRID_ZONE = ZoneId.of("Europe/Madrid");

    private final DateTimeFormatter dateTimeFormatterUTC;
    private final DateTimeFormatter dateOnlyFormatterUTC;
    private final DateTimeFormatter timeOnlyFormatterUTC;
    private final DateTimeFormatter dateTimeFormatterLocal;
    private final DateTimeFormatter dateOnlyFormatterLocal;
    private final DateTimeFormatter timeOnlyFormatterLocal;

    @Inject
    public DateService(
            @UTCDateTimeFormat DateTimeFormatter dateTimeFormatterUTC,
            @UTCDateOnlyFormat DateTimeFormatter dateOnlyFormatterUTC,
            @UTCTimeOnlyFormat DateTimeFormatter timeOnlyFormatterUTC,
            @LocalDateTimeFormat DateTimeFormatter dateTimeFormatterLocal,
            @LocalDateOnlyFormat DateTimeFormatter dateOnlyFormatterLocal,
            @LocalTimeOnlyFormat DateTimeFormatter timeOnlyFormatterLocal) {
        this.dateTimeFormatterUTC = dateTimeFormatterUTC;
        this.dateOnlyFormatterUTC = dateOnlyFormatterUTC;
        this.timeOnlyFormatterUTC = timeOnlyFormatterUTC;
        this.dateTimeFormatterLocal = dateTimeFormatterLocal;
        this.dateOnlyFormatterLocal = dateOnlyFormatterLocal;
        this.timeOnlyFormatterLocal = timeOnlyFormatterLocal;
    }

    // UTC conversion methods
    public Instant convertUnixToUTCDateTime(long unixTimestamp) {
        return Instant.ofEpochSecond(unixTimestamp);
    }

    public LocalDate convertUnixToUTCDate(long unixTimestamp) {
        return Instant.ofEpochSecond(unixTimestamp)
                .atZone(ZoneOffset.UTC)
                .toLocalDate();
    }

    public LocalTime convertUnixToUTCTime(long unixTimestamp) {
        return Instant.ofEpochSecond(unixTimestamp)
                .atZone(ZoneOffset.UTC)
                .toLocalTime();
    }

    // Local (Spain) conversion methods
    public ZonedDateTime convertUTCtoLocalDateTime(Instant utcDateTime) {
        try {
            return utcDateTime.atZone(MADRID_ZONE);
        } catch (DateTimeException e) {
            return null;
        }
    }

    public LocalDate convertUTCtoLocalDate(LocalDate utcDate) {
        try {
            return utcDate.atStartOfDay(ZoneOffset.UTC)
                    .withZoneSameInstant(MADRID_ZONE)
                    .toLocalDate();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public LocalTime convertUTCtoLocalTime(LocalTime utcTime) {
        try {
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            return today.atTime(utcTime)
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(MADRID_ZONE)
                    .toLocalTime();
        } catch (DateTimeException e) {
            return null;
        }
    }

    // UTC to Unix timestamp conversions
    public Long convertUTCDateTimeToUnix(String dateTimeStr) {
        try {
            return Instant.parse(dateTimeStr).getEpochSecond();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public Long convertUTCDateToUnix(LocalDate utcDate) {
        try {
            return utcDate.atStartOfDay(ZoneOffset.UTC)
                    .toEpochSecond();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public Long convertUTCTimeToUnix(LocalTime utcTime) {
        try {
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            return today.atTime(utcTime)
                    .atZone(ZoneOffset.UTC)
                    .toEpochSecond();
        } catch (DateTimeException e) {
            return null;
        }
    }

    // Local to UTC conversions
    public Instant convertLocalToUTCDateTime(LocalDateTime localDateTime) {
        try {
            return localDateTime.atZone(MADRID_ZONE)
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toInstant();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public LocalDate convertLocalToUTCDate(LocalDate localDate) {
        try {
            return localDate.atStartOfDay(MADRID_ZONE)
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toLocalDate();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public LocalTime convertLocalToUTCTime(LocalTime localTime) {
        try {
            LocalDate today = LocalDate.now(MADRID_ZONE);
            return today.atTime(localTime)
                    .atZone(MADRID_ZONE)
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toLocalTime();
        } catch (DateTimeException e) {
            return null;
        }
    }
}