package com.sdklite.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class CustomUserDetailsServiceTest {

    @Test
    void loadUserByUsername_ShouldReturnUser_WhenUserExists() throws Exception {
        // Setup temp user file
        String json = """
            [{"username":"testuser","password":"{noop}pass","roles":["USER"]}]
            """;
        Path tempFile = Files.createTempFile("users", ".json");
        Files.writeString(tempFile, json);

        CustomUserDetailsService service = new CustomUserDetailsService(tempFile.toString(), new ObjectMapper());
        
        UserDetails user = service.loadUserByUsername("testuser");
        
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertTrue(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        
        Files.deleteIfExists(tempFile);
    }
}
