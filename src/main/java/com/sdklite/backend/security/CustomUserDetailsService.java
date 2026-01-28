package com.sdklite.backend.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final String usersFilePath;
    private final ObjectMapper objectMapper;

    public CustomUserDetailsService(@Value("${app.security.users-file}") String usersFilePath, ObjectMapper objectMapper) {
        this.usersFilePath = usersFilePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            File file = new File(usersFilePath);
            if (!file.exists()) {
                throw new UsernameNotFoundException("User file not found");
            }

            List<User> users = objectMapper.readValue(file, new TypeReference<List<User>>(){});
            
            return users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .map(this::toUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            
        } catch (IOException e) {
            throw new RuntimeException("Error reading user file", e);
        }
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}