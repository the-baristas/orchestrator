package com.utopia.orchestrator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(exposedHeaders = "Authorization")

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/airplanes")
    public ResponseEntity<String> findAllAirplanes() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/airplanes/{id}")
    public ResponseEntity<String> findAirplaneById(@PathVariable Long id) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airplanes/{id}", id)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/airplanes/")
    public ResponseEntity<String> findByModelContaining(
            @RequestParam String model) {
        return webClient.get()
                .uri(FLIGHT_SERVICE_PATH + "/airplanes/?model={model}", model)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/airplanes")
    public ResponseEntity<String> createAirplane(@RequestBody String json) {
        return webClient.post().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/airplanes")
    public ResponseEntity<String> updateAirplane(@RequestBody String json) {
        return webClient.put().uri(FLIGHT_SERVICE_PATH + "/airplanes")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/airplanes/{id}")
    public ResponseEntity<String> deleteAirplane(@PathVariable Long id) {
        return webClient.delete()
                .uri(FLIGHT_SERVICE_PATH + "/airplanes/{id}", id).retrieve()
                .toEntity(String.class).block();
    }

    // FLIGHT SERVICE

    // get all flights
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/flights")
    public ResponseEntity<String> findAllFlights() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/flights")
                .retrieve().toEntity(String.class).block();
    }

    // post new flight
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/routes/{routeId}/flights")
    public ResponseEntity<String> createFlight(@RequestBody String json, @PathVariable Long routeId) {
        return webClient.post().uri(FLIGHT_SERVICE_PATH + "/routes/{routeId}/flights", routeId)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    // get single flight
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/flights/{id}")
    public ResponseEntity<String> findFlightById(@PathVariable Long id) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/flights/{id}", id)
                .retrieve().toEntity(String.class).block();
    }

    // update flight
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/flights/{id}")
    public ResponseEntity<String> updateFlight(@RequestBody String json, @PathVariable Long id) {
        return webClient.put().uri(FLIGHT_SERVICE_PATH + "/flights/{id}", id)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/flights/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        return webClient.delete()
                .uri(FLIGHT_SERVICE_PATH + "/flights/{id}", id).retrieve()
                .toEntity(String.class).block();
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/search/flightsbylocation")
    public ResponseEntity<String> findFlightsByRoute(@RequestParam(name = "originId") String originId,
                                                                @RequestParam(name = "destinationId") String destinationId) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/search/flightsbylocation?originId={originId}&destinationId={destinationId}", originId, destinationId)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/search/location-query")
    public ResponseEntity<String> findFlightsByLocationQuery(@RequestParam(name = "query") String query) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/search/location-query?query={query}", query)
                .retrieve().toEntity(String.class).block();
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/search/flights-query")
    public ResponseEntity<String> findFlightsByRouteAndLocation(@RequestParam(name = "originId") String originId,
                                                                @RequestParam(name = "destinationId") String destinationId,
                                                                @RequestBody String json) {
        return webClient.post().uri(FLIGHT_SERVICE_PATH + "/search/flights-query?originId={originId}&destinationId={destinationId}", originId, destinationId)
                .contentType(MediaType.APPLICATION_JSON).bodyValue(json)
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/airports")
    public ResponseEntity<String> findAllAirports() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airports")
                .retrieve().toEntity(String.class).block();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/routes")
    public ResponseEntity<String> findAllRoutes() {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/routes")
                .retrieve().toEntity(String.class).block();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/airports-containing")
    public ResponseEntity<String> findAirportsContaining(@RequestParam(name = "contains") String contains) {
        return webClient.get().uri(FLIGHT_SERVICE_PATH + "/airports-containing?contains={contains}", contains)
                .retrieve().toEntity(String.class).block();
    }
    // User Service

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String json) {
        return restTemplate.exchange("http://user-service/login",
                HttpMethod.POST, new HttpEntity<String>(json), Object.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<String> findAllUsers(
            @RequestHeader HttpHeaders headers) {
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return restTemplate.exchange(USER_SERVICE_PATH + "/users",
                HttpMethod.GET, request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<String> findUserById(
            @RequestHeader HttpHeaders headers, @PathVariable Long id) {
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return restTemplate.exchange(USER_SERVICE_PATH + "/users/" + id,
                HttpMethod.GET, request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/users/username/{username}")
    public ResponseEntity<String> findUserByUsername(
            @RequestHeader HttpHeaders headers, @PathVariable String username) {
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return restTemplate.exchange(
                USER_SERVICE_PATH + "/users/username/" + username,
                HttpMethod.GET, request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/users/email/{email}")
    public ResponseEntity<String> findUserByEmail(
            @RequestHeader HttpHeaders headers, @PathVariable String email) {
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return restTemplate.exchange(
                USER_SERVICE_PATH + "/users/email/" + email, HttpMethod.GET,
                request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestHeader HttpHeaders headers,
                                             @RequestBody String json) {
        HttpEntity<String> request = new HttpEntity<String>(json, headers);
        return restTemplate.exchange(USER_SERVICE_PATH + "/users",
                HttpMethod.POST, request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader HttpHeaders headers,
                                             @RequestBody String json, @PathVariable Long id) {
        HttpEntity<String> request = new HttpEntity<String>(json, headers);
        return restTemplate.exchange(USER_SERVICE_PATH + "/users/" + id,
                HttpMethod.PUT, request, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@RequestHeader HttpHeaders headers,
                                             @PathVariable Long id) {
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return restTemplate.exchange(USER_SERVICE_PATH + "/users/" + id,
                HttpMethod.DELETE, request, String.class);
    }

    // Booking Service

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/bookings")
    public ResponseEntity<String> findAllBookings() {
        return webClient.get().uri(BOOKING_SERVICE_PATH + "/bookings")
                .retrieve().toEntity(String.class).block();
    }


    // Handle exceptions where the status code, headers, and body are known.
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(
            HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode())
                .headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString());
    }

    // Handle exceptions where the status code, headers, and body are unknown.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUncaughtException(Exception exception) {
        System.out.printf("An unknown error occurred.", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

}