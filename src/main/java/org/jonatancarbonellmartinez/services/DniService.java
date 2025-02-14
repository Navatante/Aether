package org.jonatancarbonellmartinez.services;

import javax.inject.Singleton;

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
public class DniService {
    private static final String LETRA_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    /**
     * Genera la letra del DNI basándose en el número proporcionado.
     * @param numeroDni número del DNI sin letra
     * @return DNI completo con la letra
     */
    public String generarDniCompleto(String numeroDni) {
        try {
            // Eliminar cualquier espacio en blanco
            numeroDni = numeroDni.trim();

            // Verificar que solo contiene números
            if (!numeroDni.matches("\\d{8}")) {
                throw new IllegalArgumentException("El DNI debe contener exactamente 8 dígitos");
            }

            // Convertir a integer y calcular el resto
            int numero = Integer.parseInt(numeroDni);
            int resto = numero % 23;

            // Obtener la letra correspondiente y concatenar
            char letra = LETRA_DNI.charAt(resto);

            return numeroDni + letra;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número de DNI no es válido");
        }
    }

    /**
     * Valida si un DNI completo (números + letra) es válido.
     * @param dniCompleto DNI con letra
     * @return true si el DNI es válido, false en caso contrario
     */
    public boolean validarDni(String dniCompleto) {
        if (dniCompleto == null || dniCompleto.length() != 9) {
            return false;
        }

        String numero = dniCompleto.substring(0, 8);
        char letraProporcionada = Character.toUpperCase(dniCompleto.charAt(8));

        try {
            String dniGenerado = generarDniCompleto(numero);
            return dniGenerado.charAt(8) == letraProporcionada;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}