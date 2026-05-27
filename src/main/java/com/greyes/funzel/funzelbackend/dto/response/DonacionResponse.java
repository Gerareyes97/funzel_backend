package com.greyes.funzel.funzelbackend.dto.response;

import com.greyes.funzel.funzelbackend.enums.EstadoDonacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonacionResponse(
        Long id,
        String referencia,
        String nombre,
        String email,
        BigDecimal monto,
        EstadoDonacion estado,
        String codigoAutorizacion,
        String tarjetaMascara,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaConfirmacion
) {}