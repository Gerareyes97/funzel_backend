package com.greyes.funzel.funzelbackend.validation;

public class ValidadorMensaje {

    public static void validar(String texto, String nombreCampo) {

        if (texto == null || texto.isBlank()) {
            throw new ValidacionEntidadException(
                    "El campo '" + nombreCampo + "' es obligatorio"
            );
        }

        if (texto.length() < 3) {
            throw new ValidacionEntidadException(
                    "El campo '" + nombreCampo + "' debe tener al menos 3 caracteres"
            );
        }

        if (texto.length() > 1000) {
            throw new ValidacionEntidadException(
                    "El campo '" + nombreCampo + "' no puede exceder 1000 caracteres"
            );
        }
    }
}
