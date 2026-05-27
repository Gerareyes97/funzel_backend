package com.greyes.funzel.funzelbackend.dto.response;

import com.greyes.funzel.funzelbackend.enums.TipoImpacto;

import java.time.LocalDateTime;

public record ImpactoContadorResponse(
        Long id,
        TipoImpacto tipo,
        Long valor,
        LocalDateTime ultimaActualizacion
) {}