package com.cypher.etl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceResult {
    
    @JsonProperty("results")
    private List<Space> results;

    @JsonProperty("_links")
    private Links links;

    @Data
    @ToString
    public static class Links {
        private String base;
    }
}
