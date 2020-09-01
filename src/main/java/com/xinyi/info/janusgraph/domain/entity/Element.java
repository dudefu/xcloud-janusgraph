package com.xinyi.info.janusgraph.domain.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Element {
    private String id;
    private String label;
    private List<GraphProperty> properties = new ArrayList<>(10);

    public void putProperty(GraphProperty property) {
        this.properties.add(property);
    }

}
