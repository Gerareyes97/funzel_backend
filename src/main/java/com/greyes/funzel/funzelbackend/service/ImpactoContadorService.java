package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.ImpactoIncrementoRequest;
import com.greyes.funzel.funzelbackend.dto.response.ImpactoContadorResponse;
import com.greyes.funzel.funzelbackend.entity.ImpactoContador;
import com.greyes.funzel.funzelbackend.enums.TipoImpacto;
import com.greyes.funzel.funzelbackend.repository.ImpactoContadorRepository;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpactoContadorService {

    private final ImpactoContadorRepository impactoContadorRepository;

    public List<ImpactoContadorResponse> listar() {
        return impactoContadorRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    /* ===================== OBTENER POR TIPO ===================== */
    public ImpactoContadorResponse obtenerPorTipo(TipoImpacto tipo) {
        ImpactoContador contador = impactoContadorRepository.findByTipo(tipo)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Impacto no encontrado: " + tipo));

        return mapear(contador);
    }

    /* ===================== INCREMENTAR ===================== */
    public ImpactoContadorResponse incrementar(
            TipoImpacto tipo,
            ImpactoIncrementoRequest request
    ) {
        if (request.cantidad() == null || request.cantidad() <= 0) {
            throw new ValidacionEntidadException("El valor a incrementar debe ser mayor a 0");
        }

        ImpactoContador contador = impactoContadorRepository.findByTipo(tipo)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Impacto no encontrado: " + tipo));

        contador.setValor(contador.getValor() + request.cantidad());

        return mapear(impactoContadorRepository.save(contador));
    }

    /* ===================== MAPPER ===================== */
    private ImpactoContadorResponse mapear(ImpactoContador c) {
        return new ImpactoContadorResponse(
                c.getId(),
                c.getTipo(),
                c.getValor(),
                c.getUltimaActualizacion()
        );
    }
}