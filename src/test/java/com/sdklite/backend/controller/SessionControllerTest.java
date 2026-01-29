package com.sdklite.backend.controller;

import com.sdklite.backend.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Test
    @WithMockUser(username = "admin")
    void closeSession_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/session/close")
                .param("timestamp", "123")
                .param("adaptorName", "testAdaptor"))
                .andExpect(status().isOk());

        verify(sessionService).closeSession(anyString(), anyString(), anyString());
    }
}
