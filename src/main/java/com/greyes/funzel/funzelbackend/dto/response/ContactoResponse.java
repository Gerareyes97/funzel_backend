package com.greyes.funzel.funzelbackend.dto.response;

import java.time.LocalDateTime;

public record ContactoResponse(
        Long id,
        String nombre,
        String email,
        String asunto,
        String mensaje,
        LocalDateTime fecha
) {}
