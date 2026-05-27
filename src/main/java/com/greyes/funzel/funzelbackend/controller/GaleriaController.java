package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.GaleriaRequest;
import com.greyes.funzel.funzelbackend.dto.response.GaleriaResponse;
import com.greyes.funzel.funzelbackend.entity.Galeria;
import com.greyes.funzel.funzelbackend.service.GaleriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galeria")
@RequiredArgsConstructor
public class GaleriaController {

    private final GaleriaService galeriaService;

    @PostMapping
    public GaleriaResponse crear(@RequestBody GaleriaRequest request) {
        return galeriaService.crear(request);
    }

    @GetMapping
    public List<GaleriaResponse> listar() {
        return galeriaService.listar();
    }

    @GetMapping("/{id}")
    public GaleriaResponse obtenerPorId(@PathVariable Long id) {
        return galeriaService.obtenerPorId(id);
    }
}
