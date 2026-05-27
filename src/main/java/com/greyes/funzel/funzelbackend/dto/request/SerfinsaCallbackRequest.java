package com.greyes.funzel.funzelbackend.dto.request;

import java.math.BigDecimal;
import java.util.List;

public record SerfinsaCallbackRequest(
        String Codigo,
        Long ClientIdTransaction,
        String NumeroAutorizacion,
        BigDecimal Monto,
        String Fecha,
        String MaskPan,
        String CardHolder,
        List<ArancelSerfinsaRequest> Aranceles
) {}
