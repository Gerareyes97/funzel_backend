package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.AliadoRequest;
import com.greyes.funzel.funzelbackend.dto.response.AliadoResponse;
import com.greyes.funzel.funzelbackend.entity.Aliado;
import com.greyes.funzel.funzelbackend.entity.Voluntario;
import com.greyes.funzel.funzelbackend.repository.AliadoRepository;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import com.greyes.funzel.funzelbackend.validation.ValidadorEmail;
import com.greyes.funzel.funzelbackend.validation.ValidadorTexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AliadoService {

    private final AliadoRepository aliadoRepository;

    public AliadoResponse crear(AliadoRequest request) {

        ValidadorTexto.validar(request.nombreEmpresa());
        ValidadorEmail.validar(request.email());

        Aliado aliado = Aliado.builder()
                .nombreEmpresa(request.nombreEmpresa())
                .contacto(request.contacto())
                .email(request.email())
                .telefono(request.telefono())
                .tipo(request.tipo())
                .mensaje(request.mensaje())
                .build();

        return mapear(aliadoRepository.save(aliado));
    }

    public List<AliadoResponse> listar() {
        return aliadoRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public AliadoResponse obtenerPorId(Long id) {
        Aliado aliado = aliadoRepository.findById(id)
                .orElseThrow(() ->
                        new ValidacionEntidadException("Aliado no encontrado"));

        return mapear(aliado);
    }

    private AliadoResponse mapear(Aliado a) {
        return new AliadoResponse(
                a.getId(),
                a.getNombreEmpresa(),
                a.getContacto(),
                a.getEmail(),
                a.getTelefono(),
                a.getTipo(),
                a.getMensaje(),
                a.getFechaRegistro()
        );
    }
}
