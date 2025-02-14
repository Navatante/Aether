package org.jonatancarbonellmartinez.services;

import org.jonatancarbonellmartinez.application.annotation.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Los Services (servicios) son una capa en la arquitectura de software que encapsula la lógica de negocio reutilizable que no pertenece directamente a ninguna entidad específica. Se utilizan principalmente para:
 *
 * Encapsular lógica de negocio reutilizable
 *
 * Por ejemplo, tu DniService contiene la lógica para calcular la letra del DNI, que podría ser necesaria en diferentes partes de la aplicación
 * El DateService que ya tienes maneja las conversiones de fechas, que son usadas por varios mappers
 *
 *
 * Separar responsabilidades
 *
 * En lugar de poner toda la lógica en los ViewModels o Repositories
 * Permite tener clases más pequeñas y focalizadas (Principio de Responsabilidad Única)
 *
 *
 * Facilitar el testing
 *
 * Al tener la lógica aislada en servicios, es más fácil hacer pruebas unitarias
 * Se pueden mockear los servicios para probar otras capas
 *
 *
 *
 * Cuándo crear un Service:
 *
 * Cuando tienes lógica que se repite en varios lugares
 * Cuando la lógica es compleja y merece estar aislada
 * Cuando necesitas funcionalidad que no pertenece naturalmente a ninguna entidad
 * Cuando quieres abstraer operaciones complejas o integraciones externas
 */

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