package com.greyes.funzel.funzelbackend.dto.request;

import com.greyes.funzel.funzelbackend.enums.CategoriaGaleria;

public record GaleriaRequest(
        String titulo,
        String descripcion,
        String urlImagen,
        CategoriaGaleria categoria
) {}