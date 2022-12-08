package com.handyscan.backend.backend_service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.handyscan.backend.backend_service.model.Response;
import com.handyscan.backend.backend_service.service.ApplicationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class ApplicationController {
    public UUID uuid = UUID.randomUUID();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ApplicationService restService;

    // @Value("${SEARCH_SERVICE_ENDPOINT}")
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

    @PostMapping("/uploadImageAndProcess")
    @Operation(summary = "Api to upload and start the file processing")
    public Response uploadImageAndProcess(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) throws Exception {
            
        // response.setContentType("text/html;charset=UTF-8");
        // final String fileName = request.getParameter("fileName");
        // final Part filePart = request.getPart("file");

        return restService.storeFileInUserUploads(file.getInputStream(), fileName);
    }
}
