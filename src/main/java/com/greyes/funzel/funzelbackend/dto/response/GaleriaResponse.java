package com.greyes.funzel.funzelbackend.dto.response;

import com.greyes.funzel.funzelbackend.enums.CategoriaGaleria;

import java.time.LocalDateTime;

public record GaleriaResponse(
        Long id,
        String titulo,
        String descripcion,
        String urlImagen,
        CategoriaGaleria categoria,
        LocalDateTime fecha
) {}
