package com.sdklite.backend.controller;

import com.sdklite.backend.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/session")
@Tag(name = "Session", description = "Operations for session lifecycle and cleanup")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/close")
    @Operation(summary = "Close Session", description = "Releases the adaptor lock and deletes the temporary workspace.")
    public void closeSession(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor identifier") @RequestParam String adaptorName,
            Principal principal) throws IOException {
        
        sessionService.closeSession(principal.getName(), timestamp, adaptorName);
    }
}
