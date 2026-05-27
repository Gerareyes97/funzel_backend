package com.greyes.funzel.funzelbackend.validation;

public class ValidadorTelefono {

    private static final String REGEX = "^(\\+503\\s?)?[0-9]{8}$";

    public static void validar(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            return; // Campo opcional
        }

        String limpio = telefono.trim();

        if (!limpio.matches(REGEX)) {
            throw new ValidacionEntidadException(
                    "El teléfono debe ser de El Salvador: +503 XXXXXXXX o XXXXXXXX"
            );
        }
    }
}