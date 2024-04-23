package com.hppystay.hotelreservation.common.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ResourceController {

    @GetMapping("/favicon.ico")
    public ResponseEntity<Resource> favicon() throws IOException {
        Resource resource = new ClassPathResource("/static/favicon.ico");

        return ResponseEntity.ok()
                .body(resource);
    }
}
