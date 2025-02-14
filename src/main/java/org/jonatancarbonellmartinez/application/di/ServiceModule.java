package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.jonatancarbonellmartinez.services.util.TextFormatter;
import org.jonatancarbonellmartinez.services.util.TextFormatterService;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    TextFormatterService provideTextFormatter() {
        return new TextFormatter();
    }
}
