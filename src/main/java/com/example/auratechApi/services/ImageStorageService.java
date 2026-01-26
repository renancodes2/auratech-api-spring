package com.example.auratechApi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final Cloudinary cloudinary;


    public Map<String, Object> uploadFile(MultipartFile file, String folder) throws IOException {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "auto",
                            "use_filename", true,
                            "unique_filename", true
                    )
            );

            return uploadResult;

        } catch (IOException e) {
            throw new IOException("Failed to upload file to Cloudinary: " + e.getMessage());
        }
    }
}
