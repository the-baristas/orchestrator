package com.utopia.orchestrator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@CrossOrigin
@RestController
public class OrchestratorController {
    private static final String FLIGHT_SERVICE_PATH = "http://flight-service";

    private final WebClient webClient;

    public OrchestratorController(WebClient webclient) {
        this.webClient = webclient;
    }

    @GetMapping("/airplanes")
    public ResponseEntity<String> findAllAirplanes() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/airplanes/{id}")
    public ResponseEntity<String> findAirplaneById(@PathVariable Long id) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airplanes/{id}", id)
                .retrieve().toEntity(String.class).block();
    }

    @PostMapping("/airplanes")
    public ResponseEntity<String> createAirplane(@RequestBody String json) {
        return webClient.post().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PutMapping("/airplanes")
    public ResponseEntity<String> updateAirplane(@RequestBody String json) {
        return webClient.put().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @DeleteMapping("/airplanes/{id}")
    public ResponseEntity<String> deleteAirplane(@PathVariable Long id) {
        return webClient.delete()
                .uri(FLIGHT_SERVICE_PATH + "/airplanes/{id}", id).retrieve()
                .toEntity(String.class).block();
    }
}
