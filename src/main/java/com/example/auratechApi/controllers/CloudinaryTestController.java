package com.example.auratechApi.controllers;

import com.example.auratechApi.services.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
public class CloudinaryTestController {

    private final ImageStorageService imageStorageService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("============================================");
            Map result = imageStorageService.uploadFile(file, "products");

            System.out.println("==========================" + result.get("url"));

            String url = (String) result.get("url");

            return ResponseEntity.ok(url);

        }catch (Exception e) {
            return ResponseEntity.status(500).body("Erro no upload" + e.getMessage());
        }
    }

}
