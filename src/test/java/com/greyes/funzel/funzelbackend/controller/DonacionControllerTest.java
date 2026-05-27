package com.greyes.funzel.funzelbackend.controller;

import com.greyes.funzel.funzelbackend.security.RateLimitFilter;
import com.greyes.funzel.funzelbackend.service.CaptchaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DonacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //  Mock del captcha (NO llama a Google)
    @MockBean
    private CaptchaService captchaService;

    // Mock del rate limit (NO bloquea requests)
    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    void crearDonacion_ok() throws Exception {
        // Simula captcha válido
        Mockito.doNothing()
                .when(captchaService)
                .validar(Mockito.anyString(), Mockito.eq("donacion"));

        mockMvc.perform(post("/api/donaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "referencia": "REF-TEST",
                          "nombre": "Juan Perez",
                          "email": "juan@test.com",
                          "monto": 10.00,
                          "captchaToken": "test-token"
                        }
                        """))
                .andExpect(status().isOk());
    }
}