package com.xinyi.info.janusgraph.domain.service.impl;

import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import com.xinyi.info.janusgraph.domain.service.EdgeService;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.EdgeLabel;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对边的操作
 * @author dudefu
 *
 */
@Service
public class EdgeServiceImpl implements EdgeService {

    @Override
    public ReturnJSON addE(int vid1,int vid2,String labelName) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            g.addE(labelName).from(g.V(vid1)).to(g.V(vid2)).next();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/edge/addE");
            returnJSON.setData("添加边成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/edge/addE");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON deleteE(String name) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            g.E(name).drop().iterate();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/edge/deleteE");
            returnJSON.setData("删除边成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/edge/deleteE");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON getELabel() {
        ReturnJSON returnJSON = new ReturnJSON();
        List<Object> allEdgeLabels = new ArrayList<>();
        try{
            JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
            JanusGraphManagement mgmt = graph.openManagement();
            Iterator iterator = mgmt.getRelationTypes(EdgeLabel.class).iterator();
            while (iterator.hasNext()){
                allEdgeLabels.add(iterator.next().toString());
            }
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/edge/getELabel");
            returnJSON.setData(allEdgeLabels);
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/edge/getELabel");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON deleteELabel(String labelName) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
            JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
            JanusGraphManagement mgmt = graph.openManagement();
            mgmt.getEdgeLabel(labelName).remove();
            mgmt.commit();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/edge/deleteELabel");
            returnJSON.setData("移除边标签成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/edge/deleteELabel");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON addELabel(String labelName) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
            JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
            JanusGraphManagement mgmt = graph.openManagement();
            mgmt.makeEdgeLabel(labelName).make();
            mgmt.commit();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/edge/addELabel");
            returnJSON.setData("新增边标签成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/edge/getELabel");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    /**
     * 获取所有边
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJSON getAllE()  {

        ReturnJSON returnJSON = new ReturnJSON();
        List<Object> allEdges = new ArrayList<>();
        try{
            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            GraphTraversal iterator1 = g.E();  //返回一个迭代器，需要迭代逐一取值
            while(iterator1.hasNext()){
                allEdges.add(iterator1.next().toString());
            }
            returnJSON.setCmd("/v1/edge/getAllE");
            returnJSON.setCode(200);
            returnJSON.setData(allEdges);
        }catch (Exception e){
            returnJSON.setCmd("/v1/edge/getAllE");
            returnJSON.setCode(500);
            returnJSON.setData(e.getMessage());
        }
        return returnJSON;
    }

    /**
     * 获取单条边
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJSON getE(String fromName,String toName)  {

        ReturnJSON returnJSON = new ReturnJSON();
        String Einfo = null;
        try{
            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            GraphTraversal iterator = g.V().has("name",fromName).outE().as("e").inV().has("name",toName).select("e"); 
            while(iterator.hasNext()){
                Einfo = iterator.next().toString();
            }
            returnJSON.setCmd("/v1/edge/getE");
            returnJSON.setCode(200);
            if(null != Einfo){
                returnJSON.setData(Einfo);
            }else{
                returnJSON.setData("");
            }
        }catch (Exception e){
            returnJSON.setCmd("/v1/edge/getE");
            returnJSON.setCode(500);
            returnJSON.setData(e.getMessage());
        }
        return returnJSON;
    }
}
