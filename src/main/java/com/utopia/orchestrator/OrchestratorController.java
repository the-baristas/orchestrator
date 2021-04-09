package com.utopia.orchestrator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class OrchestratorController {
    private final WebClient webClient;

    public OrchestratorController(WebClient webclient) {
        this.webClient = webclient;
    }

    @GetMapping("/airplanes")
    public ResponseEntity<String> findAllAirplanes() {
        return webClient.get().uri("http://administration-service/airplanes")
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/airplanes/{id}")
    public ResponseEntity<String> findAirplaneById(@PathVariable Long id) {
        return webClient.get()
                .uri("http://administration-service/airplanes/{id}", id)
                .retrieve().toEntity(String.class).block();
    }
}
