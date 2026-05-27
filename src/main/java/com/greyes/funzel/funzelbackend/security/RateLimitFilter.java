package com.greyes.funzel.funzelbackend.security;

import com.greyes.funzel.funzelbackend.exception.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, RequestData> requests = new ConcurrentHashMap<>();

    @Value("${funzel.rate-limit.general}")
    private int generalLimit;

    @Value("${funzel.rate-limit.contacto}")
    private int contactoLimit;

    @Value("${funzel.rate-limit.donacion}")
    private int donacionLimit;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String ip = obtenerIp(request);
        String path = request.getRequestURI();

        if (path.equals("/api/donaciones/serfinsa/callback")) {
            filterChain.doFilter(request, response);
            return;
        }

        int limit = generalLimit;

        if (path.startsWith("/api/contactos")) {
            limit = contactoLimit;
        }

        if (path.startsWith("/api/donaciones")) {
            limit = donacionLimit;
        }

        String key = ip + ":" + path;
        RequestData data = requests.computeIfAbsent(
                key,
                k -> new RequestData()
        );

        long now = Instant.now().getEpochSecond();

        // Reset cada 60 segundos
        if (now - data.timestamp > 60) {
            data.reset(now);
        }

        data.count++;

        if (data.count > limit) {
            throw new TooManyRequestsException(
                    "Demasiadas solicitudes. Intenta más tarde."
            );
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerIp(HttpServletRequest request) {

        String xf = request.getHeader("X-Forwarded-For");

        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

    static class RequestData {

        int count = 0;
        long timestamp = Instant.now().getEpochSecond();

        void reset(long now) {
            count = 0;
            timestamp = now;
        }
    }
}