package com.cypher.etl.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class SpaceResult {
    @JsonProperty("results")
    private List<Space> results;
}
