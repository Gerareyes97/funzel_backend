package com.greyes.funzel.funzelbackend.dto.response;

public record PagoDonacionResponse(
        Long id,
        String referencia,
        String paymentUrl
) {}
