package com.theatermgnt.theatermgnt.fileStorage.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileStorageService {
    Cloudinary cloudinary;

    public Map upload(MultipartFile file) throws IOException {
        // Check if file is empty
        if (file.isEmpty()) {
            throw new IOException("Failed to upload file");
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/"))){
            throw new IOException("Failed to upload file");
        }
        String publicId = UUID.randomUUID().toString();

        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", publicId,
                "folder", "theatermgnt",
                "resource_type", "image",
                "overwrite", true
        );
        try{
            Map result = cloudinary.uploader().upload(file.getBytes(), options);
            return result;
        }catch (Exception e){
            throw new IOException("Failed to upload file");
        }
    }
    public Map delete(String publicId) throws IOException {

        try{
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"));
            return result;
        }catch (Exception e){
            throw new IOException("Failed to delete file");
        }
    }
}
