package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.annotation.*;

import javax.inject.Singleton;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Module
public class DateFormatsModule {
    @Provides
    @Singleton
    @UTCDateTimeFormat
    DateTimeFormatter provideDateTimeFormatterUTC() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneOffset.UTC);
    }

    @Provides
    @Singleton
    @UTCDateOnlyFormat
    DateTimeFormatter provideDateOnlyFormatterUTC() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy")
                .withZone(ZoneOffset.UTC);
    }

    @Provides
    @Singleton
    @UTCTimeOnlyFormat
    DateTimeFormatter provideTimeOnlyFormatterUTC() {
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneOffset.UTC);
    }

    @Provides
    @Singleton
    @LocalDateTimeFormat
    DateTimeFormatter provideDateTimeFormatterLocal() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneId.of("Europe/Madrid"));
    }

    @Provides
    @Singleton
    @LocalDateOnlyFormat
    DateTimeFormatter provideDateOnlyFormatterLocal() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy")
                .withZone(ZoneId.of("Europe/Madrid"));
    }

    @Provides
    @Singleton
    @LocalTimeOnlyFormat
    DateTimeFormatter provideTimeOnlyFormatterLocal() {
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.of("Europe/Madrid"));
    }

}
