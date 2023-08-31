package com.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CloudinaryService {
	
	 	@Value("${cloudinary.cloud-name}")
	    private String cloudName;
	    @Value("${cloudinary.api-key}")
	    private String apiKey;
	    @Value("${cloudinary.api-secret}")
	    private String apiSecret;
	    
	    @Autowired
	    private RestTemplate restTemplate;

	    public void deleteImage(String publicId) {
	        String url = String.format("https://api.cloudinary.com/v1_1/%s/delete_by_token", cloudName);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBasicAuth(apiKey, apiSecret);

	        String requestBody = String.format("{\"public_id\":\"%s\"}", publicId);

	        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(requestBody, headers), String.class);
	    }
}
