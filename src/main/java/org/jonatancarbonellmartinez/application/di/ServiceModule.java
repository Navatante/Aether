package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import org.jonatancarbonellmartinez.services.DniService;
import org.jonatancarbonellmartinez.services.util.TextFormatter;
import org.jonatancarbonellmartinez.services.util.TextFormatterService;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    TextFormatterService provideTextFormatter() {
        return new TextFormatter();
    }

    @Provides
    @Singleton
    public DniService provideDniService() {
        return new DniService();
    }
}
