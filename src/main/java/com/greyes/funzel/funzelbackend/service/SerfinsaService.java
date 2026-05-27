package com.greyes.funzel.funzelbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greyes.funzel.funzelbackend.config.SerfinsaProperties;
import com.greyes.funzel.funzelbackend.entity.Donacion;
import com.greyes.funzel.funzelbackend.validation.ValidacionEntidadException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class SerfinsaService {

    private final SerfinsaProperties properties;
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    public String crearUrlPago(Donacion donacion) {

        try {
            String endpoint = properties.baseUrl() + "/api/PayApi/TokeyTran";

            MultiValueMap<String, String> params = getParams(donacion);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<>(params, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(endpoint, request, String.class);

            JsonNode json = objectMapper.readTree(response.getBody());

            boolean satisfactorio = json.path("Satisfactorio").asBoolean(false);

            if (!satisfactorio) {
                throw new ValidacionEntidadException(
                        "Serfinsa rechazó la creación del pago: " + response.getBody()
                );
            }

            String urlPost = json.path("Datos").path("UrlPost").asText(null);

            if (urlPost == null || urlPost.isBlank()) {
                throw new ValidacionEntidadException(
                        "Serfinsa no devolvió URL de pago válida"
                );
            }

            if (urlPost.startsWith("http")) {
                return urlPost;
            }

            return properties.baseUrl() + "/" + urlPost;

        } catch (ValidacionEntidadException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error conectando con Serfinsa", ex);
        }
    }

    private MultiValueMap<String, String> getParams(Donacion donacion) {

        String redirectUrl = properties.urlRedirect()
                + "?id=" + donacion.getId()
                + "&ref=" + donacion.getReferencia();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("TokeyComercio", properties.token());
        params.add("IdTransaccionCliente", donacion.getId().toString());
        params.add("Monto", donacion.getMonto().setScale(2).toPlainString());
        params.add("ConceptoPago", "Donacion FUNZEL");
        params.add("Adicionales", donacion.getReferencia());
        params.add("UrlOrigin", properties.urlOrigin());
        params.add("UrlRedirect", redirectUrl);

        return params;
    }
}