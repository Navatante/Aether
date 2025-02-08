package org.jonatancarbonellmartinez.application.coordinator;

/**
 * Para manejar el cleanup de múltiples coordinadores, podemos crear una interfaz base que defina el comportamiento de cleanup.
 */

public interface Cleanable {
    void cleanup();
}
