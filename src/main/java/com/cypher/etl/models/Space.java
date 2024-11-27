package com.cypher.etl.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Space {
    private String id;
    private String key;
    private String name;
    private String type;
    private String status;
    
    @JsonProperty("_links")
    private Links links;

    @Data
    public static class Links {
        private String webui;
        private String self;
    }
}
