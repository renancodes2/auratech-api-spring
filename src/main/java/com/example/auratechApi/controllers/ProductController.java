package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.ProductRequestDTO;
import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.models.ProductEntity;
import com.example.auratechApi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> createProduct(@ModelAttribute ProductRequestDTO data) {

        return  ResponseEntity.ok(this.productService.saveProduct(data));

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> findOneProduct(@PathVariable("id") String id) {

        return ResponseEntity.ok(this.productService.findOneProduct(id));
    }


}
