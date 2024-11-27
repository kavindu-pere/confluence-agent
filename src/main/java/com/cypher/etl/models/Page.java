package com.cypher.etl.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    private String parentType;
    private String ownerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createdAt;
    private String authorId;
    private Integer position;
    private Version version;
    private String lastOwnerId;
    private String title;
    private String status;
    private Body body;
    private String parentId;
    private String spaceId;
    private String id;
    @JsonProperty("_links")
    private Links links;
}
