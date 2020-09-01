package com.xinyi.info.janusgraph.utils;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

public class CreateEmptyGraph {

    public static void main(String[] args) {

        //创建一个空图
//        TinkerGraph graph = TinkerGraph.open();
        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
        GraphTraversalSource g = graph.traversal();


        //增加顶点标签
        Vertex v1 = g.addV("person").property(T.id, 1).property("name", "marko").property("age", 29).next();
        Vertex v2 = g.addV("software").property(T.id, 3).property("name", "lop").property("lang", "java").next();


        //增加边标签
        g.addE("created").from(v1).to(v2).property(T.id, 9).property("weight", 0.4).next();

        //删除顶点
        g.V().has("name", "marko").drop();

        //删除顶点属性
        g.V(1).properties("name").drop();

        //更新顶点
        g.V(1).property("name","jack");

        //获取所有顶点Graph
        GraphTraversal iterator1 = g.V();  //返回一个迭代器，需要迭代逐一取值
        System.out.println("获取所有顶点Graph:");
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }


        //获取唯一标识符为“1”且边属性值为“knows”的边
        GraphTraversal iterator2 = g.V(1).outE("created");
        System.out.println("获取唯一标识符为“1”且边属性值为“knows”的边: ");
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }

        //遍历操作，查找name值为marko的顶点
        Vertex v3 = g.V().has("name","marko").next();
        System.out.println(v3);
        Vertex v4 = g.V().has("person","name","marko").next();
        System.out.println(v4);

        //顶点到顶点，需要指定边的方向
        Vertex v5 = g.V().has("person","name","marko").outE("created").inV().next();
        System.out.println(v5);

        //within比较器
        Object counts = g.V().has("person","name", P.within("vadas","marko")).values("age").next();
        System.out.println(counts);


    }
}
