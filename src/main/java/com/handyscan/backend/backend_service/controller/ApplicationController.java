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
import com.handyscan.backend.backend_service.repository.UserRecordRepository;
import com.handyscan.backend.backend_service.service.ApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class ApplicationController {
    public UUID uuid = UUID.randomUUID();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ApplicationService restService;

    @Autowired 
    UserRecordRepository userRecordRepository; 

    @GetMapping("/sup")
    @Operation(summary = "Api responds with sup and a unique identifier")
    public String sup() {
        return "Sup " + uuid.toString();
    }

    @PostMapping("/uploadImageAndProcess")
    @Operation(summary = "Api to upload and start the file processing")
    public Response uploadImageAndProcess(
        @RequestParam("file") MultipartFile file, 
        @RequestParam("fileName") String fileName,
        @RequestParam("collection") String collection,
        @RequestParam("userName") String userName) 
            throws Exception {
        log.info("Recieved request for uploading file {} for user {}, collection {}", fileName, userName, collection);
        return restService.storeFileInUserUploads(file.getInputStream(), fileName, userName, collection);
    }
}
