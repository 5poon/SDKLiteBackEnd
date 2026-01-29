package com.sdklite.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublishRequest {
    @NotBlank(message = "Timestamp is required")
    private String timestamp;
    
    @NotBlank(message = "Adaptor name is required")
    private String adaptorName;
    
    @NotBlank(message = "Vendor is required")
    private String vendor;
    
    @NotBlank(message = "Technology is required")
    private String technology;
    
    @NotBlank(message = "New version is required")
    private String newVersion;
}
