package com.xinyi.info.janusgraph.utils;

import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;

public class SanGuoDemo {

    public static void main(String[] args) throws Exception {

        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
//        GraphTraversalSource g = graph.traversal();
        GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
        JanusGraphManagement mgmt = graph.openManagement();



        //创建顶点标签
        mgmt.makeVertexLabel("person").make();
        mgmt.makeVertexLabel("country").make();
        mgmt.makeVertexLabel("weapon").make();
        mgmt.getVertexLabels();


        //创建边标签
        EdgeLabel brother = mgmt.makeEdgeLabel("brother").make();
        mgmt.makeEdgeLabel("battled").make();
        mgmt.makeEdgeLabel("belongs").make();
        mgmt.makeEdgeLabel("use").make();
        mgmt.getRelationTypes(EdgeLabel.class);


        //创建属性
        PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).cardinality(Cardinality.SET).make();
        mgmt.buildIndex("nameUnique", Vertex.class).addKey(name).unique().buildCompositeIndex();
        PropertyKey age = mgmt.makePropertyKey("age").dataType(Integer.class).make();
//        mgmt.buildIndex("age2", Vertex.class).addKey(age).buildMixedIndex("search");
        mgmt.getGraphIndexes(Vertex.class);
        mgmt.commit();

        //添加顶点
        Vertex v1 = g.addV("person").property("name", "刘备").property("age", 45).next();
        Vertex v2 = g.addV("person").property("name", "关羽").property("age", 42).next();
        Vertex v3 = g.addV("person").property("name", "张飞").property("age", 40).next();
        Vertex v4 = g.addV("person").property("name", "吕布").property("age", 38).next();

        Vertex v5 = g.addV("country").property("name","蜀国").next();

        Vertex v6 = g.addV("weapon").property("name","方天画戟").next();
        Vertex v7 = g.addV("weapon").property("name","双股剑").next();
        Vertex v8 = g.addV("weapon").property("name","青龙偃月刀").next();
        Vertex v9 = g.addV("weapon").property("name","丈八蛇矛").next();

        //添加关系
        g.addE("brother").from(g.V(v1)).to(g.V(v2)).next();
        g.addE("brother").from(g.V(v1)).to(g.V(v3)).next();
        g.addE("brother").from(g.V(v3)).to(g.V(v1)).next();
        g.addE("brother").from(g.V(v2)).to(g.V(v1)).next();
        g.addE("brother").from(g.V(v2)).to(g.V(v3)).next();
        g.addE("brother").from(g.V(v3)).to(g.V(v2)).next();

        g.addE("use").from(g.V(v1)).to(g.V(v7)).next();
        g.addE("use").from(g.V(v3)).to(g.V(v9)).next();
        g.addE("use").from(g.V(v2)).to(g.V(v8)).next();
        g.addE("use").from(g.V(v4)).to(g.V(v6)).next();

        g.addE("belongs").from(g.V(v1)).to(g.V(v5)).next();
        g.addE("belongs").from(g.V(v3)).to(g.V(v5)).next();
        g.addE("belongs").from(g.V(v2)).to(g.V(v5)).next();

        g.addE("battled").from(g.V(v4)).to(g.V(v1)).next();
        g.addE("battled").from(g.V(v4)).to(g.V(v3)).next();
        g.addE("battled").from(g.V(v4)).to(g.V(v2)).next();
        g.addE("battled").from(g.V(v1)).to(g.V(v4)).next();
        g.addE("battled").from(g.V(v3)).to(g.V(v4)).next();
        g.addE("battled").from(g.V(v2)).to(g.V(v4)).next();

//        g.tx().commit();

        System.exit(0);
    }
}
