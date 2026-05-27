package com.greyes.funzel.funzelbackend.dto.request;

public record CaptchaRequest(
        String token,
        String action
) {}