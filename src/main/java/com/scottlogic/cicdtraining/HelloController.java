package com.scottlogic.cicdtraining;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final String appVersion;

    public HelloController(@Value("${app.version:dev}") String appVersion) {
        this.appVersion = appVersion;
    }

    @GetMapping("/")
    public String home() {
        return "Hello from cicd-training-2026!";
    }

    @GetMapping("/api/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "World") String name) {
        return Map.of("message", "Hello, " + name + "!");
    }

    /** Simple endpoint for smoke tests / load balancer health checks after a deploy. */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "version", appVersion);
    }
}
