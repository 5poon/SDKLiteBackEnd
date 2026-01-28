package com.sdklite.backend.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class Lock {
    private String adaptorId;
    private String username;
    private Instant acquiredAt;
}
