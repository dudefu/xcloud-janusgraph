package com.xinyi.info.janusgraph.utils;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.SchemaStatus;
import org.janusgraph.graphdb.database.management.ManagementSystem;
import static org.janusgraph.core.Multiplicity.SIMPLE;

/**
 * 创建schema
 */
public class Schema {
    public void setSchema(String properties_path) {
        JanusGraph graph = JanusGraphFactory.open(properties_path);
        JanusGraphManagement mgmt = graph.openManagement();

        // JanusGraph schema 的构成有三部分: vertex label，edge label，property key
        // vertex labels: 创建 poi 节点, .make() 用于完成标签定义并返回标签
        VertexLabel poi = mgmt.makeVertexLabel("poi").make();
        VertexLabel tag = mgmt.makeVertexLabel("tag").make();

        // edge labels: 创建 Score 边
        EdgeLabel Score = mgmt.makeEdgeLabel("Score").multiplicity(SIMPLE).make();

        // vertex and edge properties: 设置 点 和 边 可以具有的属性，需要定义他们的数据类型
        PropertyKey itemId = mgmt.makePropertyKey("itemId").dataType(Integer.class).make();
        PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).make();
        PropertyKey uptime = mgmt.makePropertyKey("uptime").dataType(String.class).make();
        PropertyKey score = mgmt.makePropertyKey("score").dataType(Float.class).make();

        // 这里是跟前面配置的ES相关的地方，buildMixedIndex里面的参数与 配置项中间的名称(我的是search) 保持一直即可，ES里面就会生成对应的表
        // global indices 设置索引
        String item_tag_vertex = "item_tag_vertex";  // 这里自己随意设置即可，ES里生成的表名即为 janusgraph_item_tag_vertex
        // 后面的每一个 addKey() 添加的字段都会出现在ES表中，至于后面用buildMixedIndex还是别的，你google一下"janusgraph 索引"看看哪种符合自己的要求即可
        mgmt.buildIndex(item_tag_vertex, Vertex.class).addKey(itemId).addKey(name).addKey(uptime).buildMixedIndex("search");
        String item_tag_edge = "item_tag_edge";  // 我这里建了2张索引表
        mgmt.buildIndex(item_tag_edge, Edge.class).addKey(score).addKey(uptime).buildMixedIndex("search");
        mgmt.commit();

        //注册索引
        try {
            ManagementSystem
                    .awaitGraphIndexStatus(graph, item_tag_vertex)
                    .status(SchemaStatus.REGISTERED)
                    .call();
            ManagementSystem
                    .awaitGraphIndexStatus(graph, item_tag_edge)
                    .status(SchemaStatus.REGISTERED)
                    .call();
            //等待索引ok
            ManagementSystem.awaitGraphIndexStatus(graph, item_tag_vertex).status(SchemaStatus.ENABLED).call();
            ManagementSystem.awaitGraphIndexStatus(graph, item_tag_edge).status(SchemaStatus.ENABLED).call();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graph.close();
    }
}
