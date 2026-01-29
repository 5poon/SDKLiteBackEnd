package com.sdklite.backend.service;

public interface VersioningService {
    String suggestNextVersion(String vendor, String technology, String name);
}
