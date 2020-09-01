package com.xinyi.info.janusgraph.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyValue {
    private String key;
    private String value;
}
