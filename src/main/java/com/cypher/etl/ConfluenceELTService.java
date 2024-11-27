package com.cypher.etl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cypher.etl.models.SpaceResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfluenceELTService {

    private final RestClient restClient;

    public SpaceResult getSpaces() {
        return restClient.get()
                .uri("/wiki/rest/api/space")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(SpaceResult.class);
    }
}
