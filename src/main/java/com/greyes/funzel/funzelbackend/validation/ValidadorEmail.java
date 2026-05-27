package com.greyes.funzel.funzelbackend.validation;

public class ValidadorEmail {

    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static void validar(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidacionEntidadException("El email es obligatorio");
        }

        if (!email.matches(REGEX)) {
            throw new ValidacionEntidadException("El email no tiene un formato válido");
        }
    }
}