package org.jonatancarbonellmartinez.services;

import org.jonatancarbonellmartinez.application.annotation.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Singleton
public class DateService {
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

    // Métodos para UTC
    public String convertUnixToUTCDateTime(long unixTimestamp) {
        return dateTimeFormatterUTC.format(Instant.ofEpochSecond(unixTimestamp));
    }

    public String convertUnixToUTCDate(long unixTimestamp) {
        return dateOnlyFormatterUTC.format(Instant.ofEpochSecond(unixTimestamp));
    }

    public String convertUnixToUTCTime(long unixTimestamp) {
        return timeOnlyFormatterUTC.format(Instant.ofEpochSecond(unixTimestamp));
    }

    // Métodos para hora local (España)
    public String convertUTCtoLocalDateTime(String utcDateTimeStr) {
        try {
            // Parsear el string UTC a LocalDateTime
            LocalDateTime utcDateTime = LocalDateTime.parse(utcDateTimeStr, dateTimeFormatterUTC);
            // Convertir a ZonedDateTime en UTC
            ZonedDateTime utcZoned = utcDateTime.atZone(ZoneOffset.UTC);
            // Convertir a zona horaria de Madrid
            ZonedDateTime madridTime = utcZoned.withZoneSameInstant(ZoneId.of("Europe/Madrid"));
            // Formatear a string
            return dateTimeFormatterLocal.format(madridTime);
        } catch (DateTimeException e) {
            return null;
        }
    }

    public String convertUTCtoLocalDate(String utcDateStr) {
        try {
            // Añadimos hora 00:00 para la conversión
            String dateWithTime = utcDateStr + " 00:00";
            LocalDateTime utcDateTime = LocalDateTime.parse(dateWithTime, dateTimeFormatterUTC);
            ZonedDateTime utcZoned = utcDateTime.atZone(ZoneOffset.UTC);
            ZonedDateTime madridTime = utcZoned.withZoneSameInstant(ZoneId.of("Europe/Madrid"));
            return dateOnlyFormatterLocal.format(madridTime);
        } catch (DateTimeException e) {
            return null;
        }
    }

    public String convertUTCtoLocalTime(String utcTimeStr) {
        try {
            // Usamos la fecha actual para la conversión
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            String fullDateTime = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " " + utcTimeStr;
            LocalDateTime utcDateTime = LocalDateTime.parse(fullDateTime, dateTimeFormatterUTC);
            ZonedDateTime utcZoned = utcDateTime.atZone(ZoneOffset.UTC);
            ZonedDateTime madridTime = utcZoned.withZoneSameInstant(ZoneId.of("Europe/Madrid"));
            return timeOnlyFormatterLocal.format(madridTime);
        } catch (DateTimeException e) {
            return null;
        }
    }

    // Para UTC
    public Long convertUTCDateTimeToUnix(String dateTimeStr) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatterUTC);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
            return zonedDateTime.toEpochSecond();
        } catch (DateTimeException e) {
            // TODO meter CustomLogger
            return null;
        }
    }

    public Long convertUTCDateToUnix(String dateStr) {
        try {
            String dateWithTime = dateStr + " 00:00";
            LocalDateTime localDateTime = LocalDateTime.parse(dateWithTime, dateTimeFormatterUTC);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
            return zonedDateTime.toEpochSecond();
        } catch (DateTimeException e) {
            return null;
        }
    }

    public Long convertUTCTimeToUnix(String timeStr) {
        try {
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            String fullDateTime = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " " + timeStr;
            LocalDateTime localDateTime = LocalDateTime.parse(fullDateTime, dateTimeFormatterUTC);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
            return zonedDateTime.toEpochSecond();
        } catch (DateTimeException e) {
            return null;
        }
    }

    // Convertir de String Local a String UTC
    public String convertLocalToUTCDateTime(String localDateTimeStr) {
        try {
            // Parsear el string local a LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr, dateTimeFormatterLocal);
            // Convertir a ZonedDateTime en zona Madrid
            ZonedDateTime madridTime = localDateTime.atZone(ZoneId.of("Europe/Madrid"));
            // Convertir a UTC
            ZonedDateTime utcTime = madridTime.withZoneSameInstant(ZoneOffset.UTC);
            // Formatear a string
            return dateTimeFormatterUTC.format(utcTime);
        } catch (DateTimeException e) {
            return null;
        }
    }

    public String convertLocalToUTCDate(String localDateStr) {
        try {
            // Añadimos hora 00:00 para la conversión
            String dateWithTime = localDateStr + " 00:00";
            LocalDateTime localDateTime = LocalDateTime.parse(dateWithTime, dateTimeFormatterLocal);
            ZonedDateTime madridTime = localDateTime.atZone(ZoneId.of("Europe/Madrid"));
            ZonedDateTime utcTime = madridTime.withZoneSameInstant(ZoneOffset.UTC);
            return dateOnlyFormatterUTC.format(utcTime);
        } catch (DateTimeException e) {
            return null;
        }
    }

    public String convertLocalToUTCTime(String localTimeStr) {
        try {
            // Usamos la fecha actual para la conversión
            LocalDate today = LocalDate.now(ZoneId.of("Europe/Madrid"));
            String fullDateTime = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " " + localTimeStr;
            LocalDateTime localDateTime = LocalDateTime.parse(fullDateTime, dateTimeFormatterLocal);
            ZonedDateTime madridTime = localDateTime.atZone(ZoneId.of("Europe/Madrid"));
            ZonedDateTime utcTime = madridTime.withZoneSameInstant(ZoneOffset.UTC);
            return timeOnlyFormatterUTC.format(utcTime);
        } catch (DateTimeException e) {
            return null;
        }
    }
}
