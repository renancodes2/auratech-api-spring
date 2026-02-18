package com.example.auratechApi.services;

import com.example.auratechApi.dtos.ProductRequestDTO;
import com.example.auratechApi.dtos.ProductResponseDTO;
import com.example.auratechApi.exceptions.ImageUploadException;
import com.example.auratechApi.exceptions.ResourceNotFoundException;
import com.example.auratechApi.mappers.ProductMapper;
import com.example.auratechApi.models.CategoryEntity;
import com.example.auratechApi.models.ProductEntity;
import com.example.auratechApi.repositories.CategoryRepository;
import com.example.auratechApi.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageStorageService imageStorageService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductEntity saveProduct(ProductRequestDTO data) {
        CategoryEntity category = this.categoryRepository.findById(data.category()).orElseThrow(() -> new ResourceNotFoundException("Category Not found"));

        List<String> imagesUrls = data
                .imagesUrl()
                .stream()
                .map(this::uploadToCloudinary)
                .toList();

        ProductEntity product = new ProductEntity();

        product.setName(data.name());
        product.setDescription(data.description());
        product.setPrice(data.price());
        product.setStock(data.stock());
        product.setSlug(data.slug());
        product.setImagesUrls(imagesUrls);
        product.setCategory(category);

        return this.productRepository.save(product);

    }

    public List<ProductResponseDTO> findAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO findOneProduct(String id) {
        ProductEntity product = productRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("Product not found. Please check the selection and try again"));

        return mapper.toDto(product);
    }

    private String uploadToCloudinary(MultipartFile file) {
        try {
            return imageStorageService.uploadFile(file, "products").get("url").toString();
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload product image. Please try again.");
        }
    }

}
