package com.greyes.funzel.funzelbackend.validation;

public class ValidadorTexto {

    private static final String REGEX = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";

    public static void validar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionEntidadException("El nombre es obligatorio");
        }
        if (!nombre.matches(REGEX)) {
            throw new ValidacionEntidadException("El nombre contiene caracteres inválidos");
        }
    }
}