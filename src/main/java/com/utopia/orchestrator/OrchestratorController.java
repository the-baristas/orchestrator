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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@CrossOrigin
@RestController
public class OrchestratorController {
    private static final String FLIGHT_SERVICE_PATH = "http://flight-service";
    private static final String BOOKING_SERVICE_PATH = "http://booking-service";
    private static final String USER_SERVICE_PATH = "http://user-service";

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    public OrchestratorController(WebClient webclient,
            RestTemplate restTemplate) {
        this.webClient = webclient;
        this.restTemplate = restTemplate;
    }

    // Airplane Service

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

    @GetMapping("/airplanes/")
    public ResponseEntity<String> findByModelContaining(
            @RequestParam String model) {
        return webClient.get()
                .uri(FLIGHT_SERVICE_PATH + "/airplanes/?model={model}", model)
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

    // Booking Service

    @GetMapping("/bookings")
    public ResponseEntity<String> findAllBookings() {
        return webClient.get().uri(BOOKING_SERVICE_PATH + "/bookings")
                .retrieve().toEntity(String.class).block();
    }

    // User Service

    @GetMapping("/users")
    public ResponseEntity<String> findAllUsers() {
        return restTemplate.getForEntity(USER_SERVICE_PATH + "/users",
                String.class);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<String> findUserById(@PathVariable Long id) {
        return webClient.get().uri(USER_SERVICE_PATH + "/users/{id}", id)
                .retrieve().toEntity(String.class).block();
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<String> findUserByEmail(@PathVariable String email) {
        return webClient.get()
                .uri(USER_SERVICE_PATH + "/users/email/{email}", email)
                .retrieve().toEntity(String.class).block();
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody String json) {
        return webClient.post().uri(USER_SERVICE_PATH + "/users")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id,
            @RequestBody String json) {
        return webClient.put().uri(USER_SERVICE_PATH + "/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return webClient.delete()
                .uri(USER_SERVICE_PATH + "/users/{id}", id).retrieve()
                .toEntity(String.class).block();
    }
}
