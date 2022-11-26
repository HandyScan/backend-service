package com.handyscan.backend.backend_service.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppController {
    public UUID uuid = UUID.randomUUID();
    @GetMapping("/sup")
    public String sup(){
        return "Sup " + uuid.toString();
    }
}
