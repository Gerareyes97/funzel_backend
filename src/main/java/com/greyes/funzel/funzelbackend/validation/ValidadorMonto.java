package com.greyes.funzel.funzelbackend.validation;

import java.math.BigDecimal;

public class ValidadorMonto {

    public static void validar(BigDecimal monto) {
        if (monto == null) {
            throw new ValidacionEntidadException("El monto es obligatorio");
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionEntidadException("El monto debe ser mayor a 0");
        }
    }
}