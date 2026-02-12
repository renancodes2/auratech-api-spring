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
            if (file.isEmpty()) {
                throw new IOException("Failed to upload: The provided file is empty.");
            }

            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "auto"
                    )
            );

            String secureUrl = cloudinary.url().secure(true).generate(uploadResult.get("public_id").toString());
            uploadResult.put("url", secureUrl);
            uploadResult.put("secure_url", secureUrl);

            return uploadResult;
        } catch (IOException e) {
            throw new IOException("Could not complete the image upload. Please check your connection or file format.");
        }
    }
}
