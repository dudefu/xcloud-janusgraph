package com.xinyi.info.janusgraph.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphProperty {
    private VertexProperty.Cardinality cardinality;
    private String key;
    private List<String> value = new ArrayList<>(5);

    public void addValue(String value) {
        this.value.add(value);
    }
}
