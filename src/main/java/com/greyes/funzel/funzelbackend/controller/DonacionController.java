package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.dto.request.DonacionRequest;
import com.greyes.funzel.funzelbackend.dto.request.SerfinsaCallbackRequest;
import com.greyes.funzel.funzelbackend.dto.response.DonacionResponse;
import com.greyes.funzel.funzelbackend.dto.response.PagoDonacionResponse;
import com.greyes.funzel.funzelbackend.service.CaptchaService;
import com.greyes.funzel.funzelbackend.service.DonacionService;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
public class DonacionController {

    private final DonacionService donacionService;
    private final CaptchaService captchaService;

    @PostMapping("/pago")
    public PagoDonacionResponse crearPago(@RequestBody DonacionRequest request) {
       // captchaService.validar(request.captchaToken(), "donacion");
        return donacionService.crearPago(request);
    }

    @PostMapping("/serfinsa/callback")
    public ResponseEntity<String> callbackSerfinsa(
            @RequestBody SerfinsaCallbackRequest request
    ) {
        donacionService.confirmarPagoSerfinsa(request);
        return ResponseEntity.ok("OK");
    }

    @GetMapping
    public List<DonacionResponse> listar() {
        return donacionService.listar();
    }

    @GetMapping("/{id}")
    public DonacionResponse obtenerPorId(@PathVariable Long id) {
        return donacionService.obtenerPorId(id);
    }
}