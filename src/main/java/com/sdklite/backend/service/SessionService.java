package com.sdklite.backend.service;

import java.io.IOException;

public interface SessionService {
    void closeSession(String username, String timestamp, String adaptorName) throws IOException;
}
