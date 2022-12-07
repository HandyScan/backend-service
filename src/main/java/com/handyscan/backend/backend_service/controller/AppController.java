package com.handyscan.backend.backend_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class AppController {
    public UUID uuid = UUID.randomUUID();

    @Autowired
    RestTemplate restTemplate;

    @Value("${SEARCH_SERVICE_ENDPOINT}")
    private String search_service_endpoint;

    @GetMapping("/sup")
    @Operation(summary = "Api responds with sup and a unique identifier")
    public String sup() {
        return "Sup " +  search_service_endpoint + uuid.toString();
    }

    @GetMapping("/callApi")
    @Operation(summary = "Api responds with sup and a unique identifier")
    public String sup(@RequestParam String endpoint) {
        System.out.println("Endpoint is " + endpoint);
        String response = restTemplate.getForObject(endpoint, String.class);
        System.out.println("Response is " + response);
        return response;
    }
}
