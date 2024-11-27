package com.cypher.etl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Space {
    private String id;
    private String key;
    private String name;
    private String type;
    private String status;
    private String description;
    private String icon;
    
    @JsonProperty("createdAt")
    private ZonedDateTime createdAt;
    
    @JsonProperty("authorId")
    private String authorId;
    
    @JsonProperty("homepageId")
    private String homepageId;
    
    @JsonProperty("_links")
    private Links links;

    @Data
    @ToString
    public static class Links {
        private String webui;
    }
}
