package com.utopia.orchestrator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;

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

    @GetMapping("/flights")
    public ResponseEntity<String> findAllFlights() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/flights")
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<String> findFlightById(@PathVariable Long id) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/flights/{id}", id)
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/search/flightsbylocation")
    public ResponseEntity<String> findFlightsByRoute(@RequestParam(name = "originId") String originId,
                                                                @RequestParam(name = "destinationId") String destinationId) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/search/flightsbylocation?originId={originId}&destinationId={destinationId}", originId, destinationId)
                .retrieve().toEntity(String.class).block();
    }

    @PostMapping("/search/flights-query")
    public ResponseEntity<String> findFlightsByRouteAndLocation(@RequestParam(name = "originId") String originId,
                                                                @RequestParam(name = "destinationId") String destinationId,
                                                                @RequestBody String json) {
        return webClient.post().uri(FLIGHT_SERVICE_PATH + "/search/flights-query?originId={originId}&destinationId={destinationId}", originId, destinationId)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/airports")
    public ResponseEntity<String> findAllAirports() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airports")
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/routes")
    public ResponseEntity<String> findAllRoutes() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/routes")
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/airports-containing")
    public ResponseEntity<String> findAirportsContaining(@RequestParam(name = "contains") String contains) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airports-containing?contains={contains}", contains)
                .retrieve().toEntity(String.class).block();
    }
}
