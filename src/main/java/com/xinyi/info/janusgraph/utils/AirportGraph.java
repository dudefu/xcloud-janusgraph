package com.xinyi.info.janusgraph.utils;

import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.janusgraph.core.Cardinality;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AirportGraph {

    public static void main(String[] args) throws Exception {

//        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
//        GraphTraversalSource g = graph.traversal();

//        导入航空线测试数据
//        InputStream inputStream = new FileInputStream("air-routes-latest.graphml");
//        graph.io(IoCore.graphml()).reader().create().readGraph(inputStream,graph);

//        获取所有顶点Graph
//        GraphTraversal iterator1 = g.V();  //返回一个迭代器，需要迭代逐一取值
//        System.out.println("获取所有顶点Graph:");
//        while(iterator1.hasNext()){
//            System.out.println(iterator1.next());
//        }

        //获取边
//        GraphTraversal getE = g.E().limit(10);
//        System.out.println("获取10条边：");
//        while(getE.hasNext()){
//            System.out.println(getE.next());
//        }
//        Vertex v1 = g.addV("person").property("name","marko1").next();
//        Vertex v2 = g.addV("person").property("name","stephen1").next();
//        g.V(v1).addE("knows").to(v2).property("weight",0.75).iterate();

//        GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
//        g.V(4144).property(VertexProperty.Cardinality.single,"name","张飞").next();

        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
        JanusGraphManagement mgmt = graph.openManagement();
        mgmt.getEdgeLabel("情人").remove();
        System.exit(0);
    }
}
