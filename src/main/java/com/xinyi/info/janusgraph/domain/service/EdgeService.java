package com.xinyi.info.janusgraph.domain.service;

import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import java.util.List;

public interface EdgeService {

    ReturnJSON addE(int vid1 , int vid2 ,String ELabel,GraphTraversalSource g) ;

    ReturnJSON deleteE(String idName,GraphTraversalSource g) ;

    ReturnJSON getELabel();

    ReturnJSON addELabel(String labelName);

    ReturnJSON deleteELabel(String labelName);

    ReturnJSON getAllE(GraphTraversalSource g) ;

    ReturnJSON getE(String cardidFrom ,String cardidTo,GraphTraversalSource g);

    ReturnJSON getOutE(String cardid , Integer n ,GraphTraversalSource g);


}
