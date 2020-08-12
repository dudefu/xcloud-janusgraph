package com.xinyi.info.janusgraph.utils;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

import java.util.List;
import java.util.Map;


public class BasicOperation {

    private static Vertex pluto;

    public static void main(String[] args) throws Exception {
//        加载方法一
//        JanusGraph graph = JanusGraphFactory.build()
//                .set("storage.backend","hbase")
//                .set("storage.hostname", "10.24.5.200")
//                .set("index.search.backend","elasticsearch")
//                .set("index.search.hostname","10.24.5.200")
//                .open();

//        加载方法二
//        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
//        GraphTraversalSource g = graph.traversal();

        //创建一个测试图例 "Modern" graph
        TinkerGraph graph = TinkerFactory.createModern();
        GraphTraversalSource g = graph.traversal();

        Vertex v1 = g.addV("person1").property(T.id, 11).property("name", "marko").property("age", 29).next();
        Vertex v2 = g.addV("software1").property(T.id, 13).property("name", "lop").property("lang", "java").next();
        g.addE("created").from(v1).to(v2).property(T.id, 9).property("weight", 0.4);

        //获取所有顶点Graph
        GraphTraversal iterator1 = g.V();  //返回一个迭代器，需要迭代逐一取值
        System.out.println("获取所有顶点Graph:");
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }

        //获取唯一标识符为“1”的顶点
        Vertex vertex_1 = g.V(1).next();
        System.out.println("获取唯一标识符为“1”的顶点: "+vertex_1);

        //获取唯一标识符为“1”的顶点name值
        Object vertex_1_name = g.V(1).values("name").next();
        System.out.println("获取唯一标识符为“1”的顶点name值: "+vertex_1_name);

        //获取唯一标识符为“1”且边属性值为“knows”的边
        GraphTraversal iterator2 = g.V(1).outE("knows");
        System.out.println("获取唯一标识符为“1”且边属性值为“knows”的边: ");
        while(iterator2.hasNext()){
            System.out.println(iterator2.next());
        }

        //获取唯一标识为“1”，边属性为“knows”的顶点name值
        GraphTraversal iterator3 = g.V(1).outE("knows").inV().values("name");
        System.out.println("获取唯一标识为“1”，边属性为“knows”的顶点name值:");
        while(iterator3.hasNext()){
            System.out.println(iterator3.next());
        }

        //同上，注意，当一个使用outE().inV()如图所示的前面的命令，这可以缩短到仅仅out() （类似于inE().outV()和in()传入边缘）
        GraphTraversal iterator4 = g.V(1).out("knows").values("name");
        System.out.println("获取唯一标识为“1”，边属性为“knows”的顶点name值:");
        while(iterator4.hasNext()){
            System.out.println(iterator4.next());
        }

        //获取唯一标识为“1”，边属性为“knows”且年龄大于30的顶点name值
        GraphTraversal iterator5 = g.V(1).out("knows").has("age", P.gt(30)).values("name");
        System.out.println("获取唯一标识为“1”，边属性为“knows”且年龄大于30的顶点name值:");
        while(iterator5.hasNext()){
            System.out.println(iterator5.next());
        }




    }
}
