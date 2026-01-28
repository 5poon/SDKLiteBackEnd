package com.sdklite.backend.service;

import com.sdklite.backend.dto.AdaptorInfo;
import java.util.List;

public interface AdaptorDiscoveryService {
    List<AdaptorInfo> listAllAdaptors();
}
