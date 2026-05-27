package com.greyes.funzel.funzelbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "serfinsa")
public record SerfinsaProperties(
        boolean testMode,
        String tokenTest,
        String tokenLive,
        String sandboxUrl,
        String liveUrl,
        String urlOrigin,
        String urlRedirect
) {

    public String baseUrl() {
        return testMode ? sandboxUrl : liveUrl;
    }

    public String token() {
        return testMode ? tokenTest : tokenLive;
    }
}
