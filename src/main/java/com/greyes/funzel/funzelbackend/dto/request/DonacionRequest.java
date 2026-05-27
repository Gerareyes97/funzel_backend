package com.greyes.funzel.funzelbackend.dto.request;

import java.math.BigDecimal;

public record DonacionRequest(
        String nombre,
        String email,
        BigDecimal monto,
        String captchaToken
) {}
