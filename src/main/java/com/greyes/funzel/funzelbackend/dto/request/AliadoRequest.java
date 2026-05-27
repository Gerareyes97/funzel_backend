package com.greyes.funzel.funzelbackend.dto.request;

import com.greyes.funzel.funzelbackend.enums.TipoAliado;

public record AliadoRequest(
        String nombreEmpresa,
        String contacto,
        String email,
        String telefono,
        TipoAliado tipo,
        String mensaje
) {}