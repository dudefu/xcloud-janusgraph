package com.xinyi.info.janusgraph.utils;

import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;

public class JanusGraphDemo {

    public static void main(String[] args) throws Exception {

        GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
        // Reuse 'g' across the application
        // and close it on shut-down to close open connections with g.close()


        //        获取所有顶点Graph 4248 4320
//        GraphTraversal iterator1 = g.E();  //返回一个迭代器，需要迭代逐一取值
//        while(iterator1.hasNext()){
//            System.out.println(iterator1.next());
//        }



        System.exit(0);

//        Object herculesAge = g.V().has("name", "marko11").values("age").next();
//        System.out.println("Hercules is " + herculesAge + " years old.");

    }
}
