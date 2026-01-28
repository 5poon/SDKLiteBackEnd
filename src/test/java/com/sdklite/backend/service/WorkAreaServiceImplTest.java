package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkAreaServiceImplTest {

    @Mock
    private FileService fileService;

    @Mock
    private MetadataSerializerService serializerService;

    @InjectMocks
    private WorkAreaServiceImpl workAreaService;

    @Test
    void saveCounters_ShouldCallDependencies() throws IOException {
        Path mockPath = Paths.get("dummy/path");
        when(fileService.resolveUserTempPath(anyString(), anyString())).thenReturn(mockPath);
        
        workAreaService.saveCounters("user", "123", "adaptor", List.of(new CounterDef()));

        verify(serializerService).serializeCounters(anyList(), any(Writer.class));
        verify(fileService).atomicWrite(any(Path.class), anyString());
    }
}
