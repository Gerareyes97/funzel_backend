package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.VoluntarioRequest;
import com.greyes.funzel.funzelbackend.dto.response.VoluntarioResponse;
import com.greyes.funzel.funzelbackend.entity.Voluntario;
import com.greyes.funzel.funzelbackend.repository.VoluntarioRepository;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import com.greyes.funzel.funzelbackend.validation.ValidadorEmail;
import com.greyes.funzel.funzelbackend.validation.ValidadorTexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioResponse crear(VoluntarioRequest request) {

        ValidadorTexto.validar(request.nombre());
        ValidadorEmail.validar(request.email());

        Voluntario v = Voluntario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .telefono(request.telefono())
                .tipo(request.tipo())
                .mensaje(request.mensaje())
                .build();

        return mapear(voluntarioRepository.save(v));
    }

    public List<VoluntarioResponse> listar() {
        return voluntarioRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public VoluntarioResponse obtenerPorId(Long id) {
        Voluntario v = voluntarioRepository.findById(id)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Voluntario no encontrado"));

        return mapear(v);
    }

    private VoluntarioResponse mapear(Voluntario v) {
        return new VoluntarioResponse(
                v.getId(),
                v.getNombre(),
                v.getEmail(),
                v.getTelefono(),
                v.getTipo(),
                v.getMensaje(),
                v.getFechaRegistro()
        );
    }
}