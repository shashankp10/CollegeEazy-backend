package com.project.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;


@Service
public class CloudinaryService {
	
	@Autowired
    private Cloudinary cloudinary;

    public void deleteImage(String publicId) throws IOException {
        Map<String, String> options = new HashMap<>();
        options.put("invalidate", "true");
        cloudinary.uploader().destroy(publicId, options);
    }
}
