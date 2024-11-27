package com.cypher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final AppProperties appProperties;

    @Bean
    RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
        .baseUrl(appProperties.confluenceUrl())
        .build();
    }

}
