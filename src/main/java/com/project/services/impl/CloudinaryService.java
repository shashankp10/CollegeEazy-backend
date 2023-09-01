package com.project.services.impl;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class CloudinaryService {
	
	private Cloudinary cloudinary;

    public String deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String s = "Image with public ID " + publicId + " has been deleted from Cloudinary.";
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return publicId + "deletion failed";
    }
}
