package com.dqtri.batcher.client;

import feign.RequestInterceptor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@NoArgsConstructor
public class ResourceClientConfig {
    @Value("${services.resource.api.subscription-key}")
    private String subscriptionKey;

    @Value("${services.resource.api.x-api-key}")
    private String xApiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("subscription-key", subscriptionKey);
            requestTemplate.header("x-api-key", xApiKey);
        };
    }
}
