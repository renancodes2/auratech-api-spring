package com.example.auratechApi.controllers;

import com.example.auratechApi.dtos.ProductRequestDTO;
import com.example.auratechApi.model.ProductEntity;
import com.example.auratechApi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> createProduct(@ModelAttribute ProductRequestDTO data) {

        return  ResponseEntity.ok(this.productService.saveProduct(data));


    }


}
