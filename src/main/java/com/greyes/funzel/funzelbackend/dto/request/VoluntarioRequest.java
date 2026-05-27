package com.greyes.funzel.funzelbackend.dto.request;

import com.greyes.funzel.funzelbackend.enums.TipoVoluntario;

public record VoluntarioRequest(
        String nombre,
        String email,
        String telefono,
        TipoVoluntario tipo,
        String mensaje
) {}
