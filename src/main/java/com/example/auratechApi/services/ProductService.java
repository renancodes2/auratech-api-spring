package com.example.auratechApi.services;

import com.cloudinary.Cloudinary;
import com.example.auratechApi.dtos.ProductRequestDTO;
import com.example.auratechApi.exceptions.ResourceNotFoundException;
import com.example.auratechApi.mappers.ProductMapper;
import com.example.auratechApi.model.CategoryEntity;
import com.example.auratechApi.model.ProductEntity;
import com.example.auratechApi.repositories.CategoryRepository;
import com.example.auratechApi.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

//    Map result = imageStorageService.uploadFile(file, "products");
//
//            System.out.println("==========================" + result.get("url"));
//
//    String url = (String) result.get("url");

    private final ProductRepository productRepository;
    private final ImageStorageService imageStorageService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductEntity saveProduct(ProductRequestDTO data) {

        CategoryEntity category = this.categoryRepository.findById(data.category()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<String> imagesUrl = data
                .imagesUrl()
                .stream()
                .map(img -> uploadToCloudinary(img))
                .toList();

        ProductEntity product = new ProductEntity();

        product.setName(data.name());
        product.setDescription(data.description());
        product.setPrice(data.price());
        product.setStock(data.stock());
        product.setSlug(data.slug());
        product.setImagesUrl(imagesUrl);
        product.setCategory(category);

        return this.productRepository.save(product);

    }

    private String uploadToCloudinary(MultipartFile file) {
        try {
            return imageStorageService.uploadFile(file, "products").get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
