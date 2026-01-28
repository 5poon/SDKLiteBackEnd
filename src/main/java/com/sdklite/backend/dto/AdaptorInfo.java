package com.sdklite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdaptorInfo {
    private String vendor;
    private String technology;
    private String name;
    private String version;
    private String path;
}
