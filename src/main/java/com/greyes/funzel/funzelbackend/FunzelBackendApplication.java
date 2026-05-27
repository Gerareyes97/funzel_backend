package com.greyes.funzel.funzelbackend;

import com.greyes.funzel.funzelbackend.config.SerfinsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SerfinsaProperties.class)
public class FunzelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunzelBackendApplication.class, args);
    }
}
