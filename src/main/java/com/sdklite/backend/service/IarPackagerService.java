package com.sdklite.backend.service;

import java.io.IOException;
import java.nio.file.Path;

public interface IarPackagerService {
    void packageConfig(Path sourceDir, Path targetIarPath) throws IOException;
}
