package com.xinyi.info.janusgraph.domain.entity.vo;

import com.xinyi.info.janusgraph.domain.entity.Element;
import com.xinyi.info.janusgraph.domain.entity.GraphProperty;
import com.xinyi.info.janusgraph.domain.entity.KeyValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyVo {
    private String id;
    private String label;
    private boolean vertex;
    List<KeyValue> keyValues = new ArrayList<KeyValue>(50);

    public PropertyVo(Element element) {
        this.id = element.getId();
        this.label = element.getLabel();
        List<GraphProperty> properties = element.getProperties();
        for (GraphProperty property : properties) {
            String key = property.getKey();
            List<String> value = property.getValue();
            for (String v : value) {
                keyValues.add(new KeyValue(key, v));
            }
        }
    }
}
