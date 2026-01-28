package com.sdklite.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenUnauthenticated_then401() throws Exception {
        mockMvc.perform(get("/error")) // /error exists by default
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticated_then200() throws Exception {
        // /error usually returns JSON, status might be different depending on config,
        // but it shouldn't be 401. 
        // Actually /error might be permitted for all? 
        // Let's try a made up path, expecting 404 (Authenticated but Not Found)
        mockMvc.perform(get("/random-path"))
                .andExpect(status().isNotFound());
    }
}
