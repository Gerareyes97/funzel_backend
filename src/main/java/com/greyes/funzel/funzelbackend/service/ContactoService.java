package com.greyes.funzel.funzelbackend.service;

import com.greyes.funzel.funzelbackend.dto.request.ContactoRequest;
import com.greyes.funzel.funzelbackend.dto.response.ContactoResponse;
import com.greyes.funzel.funzelbackend.entity.Aliado;
import com.greyes.funzel.funzelbackend.entity.Contacto;
import com.greyes.funzel.funzelbackend.repository.ContactoRepository;
import com.greyes.funzel.funzelbackend.validation.ValidadorEmail;
import com.greyes.funzel.funzelbackend.validation.ValidadorMensaje;
import com.greyes.funzel.funzelbackend.validation.ValidadorTexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoResponse crear(ContactoRequest request) {

        ValidadorTexto.validar(request.nombre());
        ValidadorEmail.validar(request.email());
        ValidadorMensaje.validar(request.mensaje(), "mensaje");

        Contacto contacto = Contacto.builder()
                .nombre(request.nombre())
                .email(request.email())
                .asunto(request.asunto())
                .mensaje(request.mensaje())
                .build();

        return mapear(contactoRepository.save(contacto));
    }

    public List<ContactoResponse> listar() {
        return contactoRepository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    public ContactoResponse obtenerPorId(Long id) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contacto no encontrado"));

        return mapear(contacto);
    }

    private ContactoResponse mapear(Contacto c) {
        return new ContactoResponse(
                c.getId(),
                c.getNombre(),
                c.getEmail(),
                c.getAsunto(),
                c.getMensaje(),
                c.getFecha()
        );
    }
}
