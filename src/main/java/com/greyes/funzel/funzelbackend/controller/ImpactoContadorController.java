package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.ImpactoIncrementoRequest;
import com.greyes.funzel.funzelbackend.dto.response.ImpactoContadorResponse;
import com.greyes.funzel.funzelbackend.entity.ImpactoContador;
import com.greyes.funzel.funzelbackend.enums.TipoImpacto;
import com.greyes.funzel.funzelbackend.service.ImpactoContadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/impacto")
@RequiredArgsConstructor
public class ImpactoContadorController {

    private final ImpactoContadorService impactoContadorService;

    @GetMapping
    public List<ImpactoContadorResponse> listar() {
        return impactoContadorService.listar();
    }

    /* ===================== OBTENER POR TIPO ===================== */
    @GetMapping("/{tipo}")
    public ImpactoContadorResponse obtenerPorTipo(@PathVariable TipoImpacto tipo) {
        return impactoContadorService.obtenerPorTipo(tipo);
    }

    /* ===================== INCREMENTAR ===================== */
    @PostMapping("/{tipo}/incrementar")
    public ImpactoContadorResponse incrementar(
            @PathVariable TipoImpacto tipo,
            @RequestBody ImpactoIncrementoRequest request
    ) {
        return impactoContadorService.incrementar(tipo, request);
    }
}