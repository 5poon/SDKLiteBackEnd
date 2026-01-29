package com.sdklite.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersioningServiceImpl implements VersioningService {

    @Value("${app.adaptor.base-path}")
    private String adaptorBasePath;

    @Override
    public String suggestNextVersion(String vendor, String technology, String name) {
        Path path = Paths.get(adaptorBasePath, vendor, technology, name);
        File dir = path.toFile();

        if (!dir.exists() || !dir.isDirectory()) {
            return "01.01.01";
        }

        File[] versionDirs = dir.listFiles(File::isDirectory);
        if (versionDirs == null || versionDirs.length == 0) {
            return "01.01.01";
        }

        List<String> versions = Arrays.stream(versionDirs)
                .map(File::getName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        String latest = versions.get(0);
        return incrementVersion(latest);
    }

    private String incrementVersion(String version) {
        String[] parts = version.split("\\.");
        if (parts.length == 0) return version + ".1";

        try {
            int last = Integer.parseInt(parts[parts.length - 1]);
            parts[parts.length - 1] = String.format("%02d", last + 1);
            return String.join(".", parts);
        } catch (NumberFormatException e) {
            return version + ".1";
        }
    }
}
