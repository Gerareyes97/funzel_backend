package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.AliadoRequest;
import com.greyes.funzel.funzelbackend.dto.response.AliadoResponse;
import com.greyes.funzel.funzelbackend.entity.Aliado;
import com.greyes.funzel.funzelbackend.service.AliadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aliados")
@RequiredArgsConstructor
public class AliadoController {

    private final AliadoService aliadoService;

    @PostMapping
    public AliadoResponse crear(@RequestBody AliadoRequest request) {
        return aliadoService.crear(request);
    }

    @GetMapping
    public List<AliadoResponse> listar() {
        return aliadoService.listar();
    }

    @GetMapping("/{id}")
    public AliadoResponse obtenerPorId(@PathVariable Long id) {
        return aliadoService.obtenerPorId(id);
    }
}