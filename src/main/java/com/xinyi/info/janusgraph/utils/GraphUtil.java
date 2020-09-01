package com.xinyi.info.janusgraph.utils;

import com.xinyi.info.janusgraph.domain.entity.GraphEdge;
import com.xinyi.info.janusgraph.domain.entity.GraphVertex;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class GraphUtil {


    public static GraphVertex convert(Vertex vertex) {
        GraphVertex graphVertex = new GraphVertex();
        graphVertex.setId(vertex.id().toString());
        graphVertex.setLabel(vertex.label());
        return graphVertex;
    }

    public static GraphEdge convert(Edge edge) {
        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setId(edge.id().toString());
        graphEdge.setLabel(edge.label());

        Vertex inVertex = edge.inVertex();
        Vertex outVertex = edge.outVertex();
        GraphVertex inGraphVertex = convert(inVertex);
        GraphVertex outGraphVertex = convert(outVertex);
        graphEdge.setSource(outGraphVertex);
        graphEdge.setTarget(inGraphVertex);
        return graphEdge;
    }

}
