package com.sdklite.backend.service;

import java.io.IOException;

public interface AdaptorExtractionService {
    void extractAdaptor(String vendor, String technology, String name, String version, String username, String timestamp) throws IOException;
}
