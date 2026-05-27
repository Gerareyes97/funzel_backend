package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.VoluntarioRequest;
import com.greyes.funzel.funzelbackend.dto.response.VoluntarioResponse;
import com.greyes.funzel.funzelbackend.entity.Voluntario;
import com.greyes.funzel.funzelbackend.service.VoluntarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voluntarios")
@RequiredArgsConstructor
public class VoluntarioController {

    private final VoluntarioService  voluntarioService;


    @PostMapping
    public VoluntarioResponse crear(@RequestBody VoluntarioRequest request) {
        return voluntarioService.crear(request);
    }

    @GetMapping
    public List<VoluntarioResponse> listar() {
        return voluntarioService.listar();
    }

    @GetMapping("/{id}")
    public VoluntarioResponse obtenerPorId(@PathVariable Long id) {
        return voluntarioService.obtenerPorId(id);
    }


}
