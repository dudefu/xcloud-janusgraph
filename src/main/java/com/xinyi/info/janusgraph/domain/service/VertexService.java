package com.xinyi.info.janusgraph.domain.service;

import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

public interface VertexService {

    ReturnJSON addV(String label, String name , int age , GraphTraversalSource g) ;

    ReturnJSON deleteV(int vid,GraphTraversalSource g) ;

    ReturnJSON getV(String name,GraphTraversalSource g);

    ReturnJSON updateV(int vid,String name,GraphTraversalSource g);

    ReturnJSON getAllVinfo(int n,GraphTraversalSource g) ;

}
