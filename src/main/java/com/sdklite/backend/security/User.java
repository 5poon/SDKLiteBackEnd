package com.sdklite.backend.security;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class User {
    private String username;
    private String password;
    private List<String> roles;
    private Map<String, Integer> quotas;
}
