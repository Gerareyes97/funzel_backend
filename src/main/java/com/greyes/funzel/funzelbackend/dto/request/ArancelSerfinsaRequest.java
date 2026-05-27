package com.greyes.funzel.funzelbackend.dto.request;

import java.math.BigDecimal;

public record ArancelSerfinsaRequest(
        String Concepto,
        BigDecimal Valor
) {}
