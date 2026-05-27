package com.greyes.funzel.funzelbackend.dto.response;

import com.greyes.funzel.funzelbackend.enums.TipoAliado;

import java.time.LocalDateTime;

public record AliadoResponse(
        Long id,
        String nombreEmpresa,
        String contacto,
        String email,
        String telefono,
        TipoAliado tipo,
        String mensaje,
        LocalDateTime fechaRegistro
) {}