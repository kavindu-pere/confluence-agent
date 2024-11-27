package com.cypher.etl.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
    private String editui;
    private String webui;
    private String edituiv2;
    private String tinyui;
}
