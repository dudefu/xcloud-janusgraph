package com.xinyi.info.janusgraph.utils;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

import java.util.Iterator;

import static org.janusgraph.core.Multiplicity.MANY2ONE;
import static org.janusgraph.core.Multiplicity.MULTI;

/**
 * JanusGraph建模
 * @author defu.du
 * @date 2019-0720
 * @Copyright (C)1997-2018 深圳信义科技 All rights reserved.
 */
public class JanusGraphSchemaCreate {

    public static void main(String[] args) throws BackendException {

        //创建一个空图
        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
        GraphTraversalSource g = graph.traversal();
        JanusGraphManagement JGmanager = graph.openManagement();
//
//        //顶点标签创建
//        JGmanager.makeVertexLabel("person").make();
//        JGmanager.makeVertexLabel("software").make();
//        JGmanager.commit();
//
//        //顶点标签的查看
//        //单个查看
//        VertexLabel person = JGmanager.getVertexLabel("person");
//        System.out.println(person);
//        VertexLabel software = JGmanager.getVertexLabel("software");
//        System.out.println(software);
//        //查看所有
//        Iterable<VertexLabel> iterable = JGmanager.getVertexLabels();
//        Iterator<VertexLabel> iterator = iterable.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//
//        //顶点标签的修改
//        VertexLabel person1 = JGmanager.getVertexLabel("person1");
//        JGmanager.changeName(person1,"person");
//        JGmanager.commit();
//
//        //查看修改后的顶点标签
//        System.out.println("person:"+JGmanager.getVertexLabel("person"));
//        System.out.println("person1:"+JGmanager.getVertexLabel("person1"));
//
//        //删除顶点标签
//        JGmanager.getVertexLabel("person").remove();
//        JGmanager.commit();
//
//
//        //边标签的创建
//        JGmanager.makeEdgeLabel("follow").multiplicity(MULTI).make();
//        JGmanager.makeEdgeLabel("mother").multiplicity(MANY2ONE).make();
//          JGmanager.commit();
//
//        //边标签的查看
//        System.out.println("follow:"+JGmanager.getEdgeLabel("follow"));
//        System.out.println(JGmanager.getRelationTypes(EdgeLabel.class));
//
//        //边标签的删除
//        JGmanager.getEdgeLabel("follow").remove();
//        System.out.println(JGmanager.getRelationTypes(EdgeLabel.class));
//
//        //属性键的创建
//        JGmanager.makePropertyKey("name").dataType(String.class).cardinality(Cardinality.SINGLE).make();
//
//        //属性键的查看
//        System.out.println(JGmanager.getPropertyKey("name"));;
//
//        //属性键的修改
//        PropertyKey name = JGmanager.getPropertyKey("name");
//        JGmanager.changeName(name,"firstName");
//        System.out.println(JGmanager.getPropertyKey("firstName"));
//
//        //属性键的删除
//          JGmanager.getPropertyKey("firstName").remove();
//          System.out.println(JGmanager.getPropertyKey("firstName"));
//
//          //删除等这个图
//          JanusGraphFactory.drop(graph);

//        System.out.println(g.V().properties().key().dedup());

        System.exit(0);

    }
}
