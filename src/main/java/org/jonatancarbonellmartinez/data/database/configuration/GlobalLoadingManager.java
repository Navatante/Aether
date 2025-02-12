package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Singleton;

@Singleton
public class GlobalLoadingManager {
    private final BooleanProperty globalLoading = new SimpleBooleanProperty(false);
    private int activeLoadCount = 0;

    public synchronized void startLoading() {
        activeLoadCount++;
        updateLoadingState();
    }

    public synchronized void endLoading() {
        activeLoadCount = Math.max(0, activeLoadCount - 1);
        updateLoadingState();
    }

    private void updateLoadingState() {
        Platform.runLater(() -> globalLoading.set(activeLoadCount > 0));
    }

    public BooleanProperty globalLoadingProperty() {
        return globalLoading;
    }
}