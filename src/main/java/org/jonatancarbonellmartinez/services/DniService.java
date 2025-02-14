package org.jonatancarbonellmartinez.services;

import javax.inject.Singleton;

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