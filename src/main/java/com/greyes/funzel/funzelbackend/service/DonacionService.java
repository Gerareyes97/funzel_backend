package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.DonacionRequest;
import com.greyes.funzel.funzelbackend.dto.request.SerfinsaCallbackRequest;
import com.greyes.funzel.funzelbackend.dto.response.DonacionResponse;
import com.greyes.funzel.funzelbackend.dto.response.PagoDonacionResponse;
import com.greyes.funzel.funzelbackend.entity.Donacion;
import com.greyes.funzel.funzelbackend.enums.EstadoDonacion;
import com.greyes.funzel.funzelbackend.repository.DonacionRepository;
import com.greyes.funzel.funzelbackend.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final DonacionRepository donacionRepository;
    private final SerfinsaService serfinsaService;

    public PagoDonacionResponse crearPago(DonacionRequest request) {

        ValidadorTexto.validar(request.nombre());
        ValidadorEmail.validar(request.email());
        ValidadorMonto.validar(request.monto());

        Donacion donacion = Donacion.builder()
                .referencia(generarReferencia())
                .nombre(request.nombre())
                .email(request.email())
                .monto(request.monto())
                .estado(EstadoDonacion.PENDIENTE)
                .build();

        Donacion guardada = donacionRepository.save(donacion);

        String paymentUrl = serfinsaService.crearUrlPago(guardada);

        guardada.setSerfinsaUrlPago(paymentUrl);
        donacionRepository.save(guardada);

        return new PagoDonacionResponse(
                guardada.getId(),
                guardada.getReferencia(),
                paymentUrl
        );
    }

    public void confirmarPagoSerfinsa(SerfinsaCallbackRequest request) {

        if (request.ClientIdTransaction() == null) {
            throw new ValidacionEntidadException("Transacción inválida");
        }

        Donacion donacion = donacionRepository.findById(request.ClientIdTransaction())
                .orElseThrow(() -> new ValidacionEntidadException("Donación no encontrada"));

        if ("00".equals(request.Codigo())) {
            donacion.setEstado(EstadoDonacion.CONFIRMADA);
            donacion.setCodigoAutorizacion(request.NumeroAutorizacion());
            donacion.setTarjetaMascara(request.MaskPan());
            donacion.setCardHolder(request.CardHolder());
            donacion.setFechaPagoSerfinsa(request.Fecha());
            donacion.setFechaConfirmacion(LocalDateTime.now());
        } else {
            donacion.setEstado(EstadoDonacion.RECHAZADA);
        }

        donacionRepository.save(donacion);
    }

    public List<DonacionResponse> listar() {
        return donacionRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public DonacionResponse obtenerPorId(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Donación no encontrada"));

        return mapear(donacion);
    }

    private DonacionResponse mapear(Donacion donacion) {
        return new DonacionResponse(
                donacion.getId(),
                donacion.getReferencia(),
                donacion.getNombre(),
                donacion.getEmail(),
                donacion.getMonto(),
                donacion.getEstado(),
                donacion.getCodigoAutorizacion(),
                donacion.getTarjetaMascara(),
                donacion.getFechaCreacion(),
                donacion.getFechaConfirmacion()
        );
    }

    private String generarReferencia() {
        return "DON-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
