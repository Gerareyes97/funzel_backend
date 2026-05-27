package com.greyes.funzel.funzelbackend.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    @Value("${security.captcha.secret}")
    private String secret;

    @Value("${security.captcha.min-score}")
    private double minScore;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public void validar(String token, String action) {

        RestTemplate restTemplate = new RestTemplate();

        String url = VERIFY_URL +
                "?secret=" + secret +
                "&response=" + token;

        CaptchaResponse response =
                restTemplate.postForObject(url, null, CaptchaResponse.class);

        if (response == null ||
                !response.success ||
                response.score < minScore ||
                !action.equals(response.action)) {

            throw new RuntimeException("Captcha inválido");
        }
    }

    @Data
    private static class CaptchaResponse {
        private boolean success;
        private double score;
        private String action;
        @JsonProperty("challenge_ts")
        private String challengeTs;
        private String hostname;
    }
}