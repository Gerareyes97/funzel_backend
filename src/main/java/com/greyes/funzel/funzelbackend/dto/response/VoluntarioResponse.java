package com.greyes.funzel.funzelbackend.dto.response;

import com.greyes.funzel.funzelbackend.enums.TipoVoluntario;

import java.time.LocalDateTime;

public record VoluntarioResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        TipoVoluntario tipo,
        String mensaje,
        LocalDateTime fechaRegistro
) {}
