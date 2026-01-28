package com.sdklite.backend.service;

import com.sdklite.backend.dto.AdaptorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AdaptorDiscoveryServiceImpl implements AdaptorDiscoveryService {

    private final Path adaptorsRoot;

    public AdaptorDiscoveryServiceImpl(@Value("${app.file.root-path:example}") String rootPath) {
        this.adaptorsRoot = Paths.get(rootPath).resolve("adaptors").toAbsolutePath().normalize();
    }

    @Override
    public List<AdaptorInfo> listAllAdaptors() {
        List<AdaptorInfo> adaptors = new ArrayList<>();
        if (!Files.exists(adaptorsRoot)) {
            return adaptors;
        }

        try (Stream<Path> vendorStream = Files.list(adaptorsRoot)) {
            vendorStream.filter(Files::isDirectory).forEach(vendorPath -> {
                String vendor = vendorPath.getFileName().toString();
                
                try (Stream<Path> techStream = Files.list(vendorPath)) {
                    techStream.filter(Files::isDirectory).forEach(techPath -> {
                        String tech = techPath.getFileName().toString();
                        
                        try (Stream<Path> nameStream = Files.list(techPath)) {
                            nameStream.filter(Files::isDirectory).forEach(namePath -> {
                                String name = namePath.getFileName().toString();
                                
                                try (Stream<Path> versionStream = Files.list(namePath)) {
                                    versionStream.filter(Files::isDirectory).forEach(versionPath -> {
                                        String version = versionPath.getFileName().toString();
                                        Path iarPath = versionPath.resolve(name + ".iar");
                                        
                                        if (Files.exists(iarPath)) {
                                            adaptors.add(AdaptorInfo.builder()
                                                    .vendor(vendor)
                                                    .technology(tech)
                                                    .name(name)
                                                    .version(version)
                                                    .path(iarPath.toString())
                                                    .build());
                                        }
                                    });
                                } catch (IOException ignored) {}
                            });
                        } catch (IOException ignored) {}
                    });
                } catch (IOException ignored) {}
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan adaptors directory", e);
        }

        return adaptors;
    }
}
