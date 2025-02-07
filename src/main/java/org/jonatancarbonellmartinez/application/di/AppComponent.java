package org.jonatancarbonellmartinez.application.di;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        DatabaseModule.class,
        RepositoryModule.class,
        ViewModelModule.class
})
public interface AppComponent {
    MainCoordinator mainCoordinator();
}
