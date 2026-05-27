package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.GaleriaRequest;
import com.greyes.funzel.funzelbackend.dto.response.GaleriaResponse;
import com.greyes.funzel.funzelbackend.entity.Galeria;
import com.greyes.funzel.funzelbackend.repository.GaleriaRepository;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import com.greyes.funzel.funzelbackend.validation.ValidadorTexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GaleriaService {

    private final GaleriaRepository galeriaRepository;

    public GaleriaResponse crear(GaleriaRequest request) {

        ValidadorTexto.validar(request.titulo());
        ValidadorTexto.validar(request.urlImagen());

        Galeria g = Galeria.builder()
                .titulo(request.titulo())
                .descripcion(request.descripcion())
                .urlImagen(request.urlImagen())
                .categoria(request.categoria())
                .build();

        return mapear(galeriaRepository.save(g));
    }

    public List<GaleriaResponse> listar() {
        return galeriaRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public GaleriaResponse obtenerPorId(Long id) {
        Galeria g = galeriaRepository.findById(id)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Imagen no encontrada"));

        return mapear(g);
    }

    private GaleriaResponse mapear(Galeria g) {
        return new GaleriaResponse(
                g.getId(),
                g.getTitulo(),
                g.getDescripcion(),
                g.getUrlImagen(),
                g.getCategoria(),
                g.getFechaRegistro()
        );
    }
}
