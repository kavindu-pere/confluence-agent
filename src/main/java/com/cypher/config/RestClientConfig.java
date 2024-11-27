package com.cypher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.cypher.AppProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final AppProperties appProperties;

    @Bean
    RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
        .baseUrl(appProperties.confluenceUrl())
        .defaultHeader("Authorization", "Basic a2F2aW5kdXBlcmVyYTIwMTBAZ21haWwuY29tOkFUQVRUM3hGZkdGMGpXOVJiNVhtYjNwZm9YMHEzWDVCODdTRUxKOExtd3dXRzN0cllXbGQ1OGd6Sm9obmUxTVlpRTh1MEItVHFva1VsSWRCZmNaM2s1TlJRbEtlNjJLUlRpYXU5eU8wM3JLd1JSRXdmajhxNUtRWnJKSEo5dXBieXpzWmFSNFBPOG5ia0E5WU1LOFJPc21TdGk0dzR1VHVGVDAtM2I4NGc1SHFzZ2pMeHVlLU4xdz00Qjg0NDYxMQ==")
        .build();
    }

}
