package com.dqtri.batcher.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceClientCredential {
    public static final String BEARER = "Bearer";

    public String getAccessToken() {
        return String.format("%s %s", BEARER, "key");
    }
}
