package com.xinyi.info.janusgraph.domain.entity;

import lombok.Data;

@Data
public class GraphVertex extends Element {
    private boolean draggable = true;
    private int category = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphVertex vertex = (GraphVertex) o;
        return this.getId().equals(vertex.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
