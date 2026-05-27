package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.ContactoRequest;
import com.greyes.funzel.funzelbackend.dto.response.ContactoResponse;
import com.greyes.funzel.funzelbackend.service.ContactoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @PostMapping
    public ContactoResponse crear(@RequestBody ContactoRequest request) {
        return contactoService.crear(request);
    }

    @GetMapping
    public List<ContactoResponse> listar() {
        return contactoService.listar();
    }

    @GetMapping("/{id}")
    public ContactoResponse obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id);
    }
}