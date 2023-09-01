package com.project.payload;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class AppConfig {
	private final String CLOUD_NAME = "dgviyqmaz";
    private final String API_KEY = "166985971954285";
    private final String API_SECRET = "MVTCOZSmHhnVIb4pGk_R6pYWTvA";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }
}
