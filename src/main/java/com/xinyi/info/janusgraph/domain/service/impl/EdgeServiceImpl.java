package com.xinyi.info.janusgraph.domain.service.impl;

import com.xinyi.info.janusgraph.domain.model.Mxcsgx;
import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import com.xinyi.info.janusgraph.domain.service.EdgeService;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceEdge;
import org.janusgraph.core.EdgeLabel;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对边的操作
 * @author dudefu
 *
 */
@Service
public class EdgeServiceImpl implements EdgeService {

    @Override
    public ReturnJSON addE(int vid1,int vid2,String labelName,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
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
    public ReturnJSON deleteE(String name,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
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
    public ReturnJSON getAllE(GraphTraversalSource g)  {

        ReturnJSON returnJSON = new ReturnJSON();
        List<Object> allEdges = new ArrayList<>();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
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
    public ReturnJSON getE(String cardidFrom,String cardidTo,GraphTraversalSource g)  {

        ReturnJSON returnJSON = new ReturnJSON();
        String Einfo = null;
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            GraphTraversal iterator = g.V().has("cardid",cardidFrom).outE().as("e").inV().has("cardid",cardidFrom).select("e");
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

    @Override
    public ReturnJSON getOutE(String cardid,Integer level ,GraphTraversalSource g) {

        if(null == level){
            level = 1 ;
        }

        ReturnJSON returnJSON = new ReturnJSON();
        List<Object> results = new ArrayList<>();
        List<Mxcsgx> mxcsgxs = new ArrayList<>();
        try{
            GraphTraversal iteratorOutE = g.V().has("cardid",cardid).outE();
            if(level == 2){
                iteratorOutE = g.V().has("cardid",cardid).out().outE();
            }
            if(level == 3){
                iteratorOutE = g.V().has("cardid",cardid).out().out().outE();
            }

            int dataCount = 0 ;
            String lordName = "";
            String LordCertificate = "";
            while(iteratorOutE.hasNext()){
                Mxcsgx mxcsgx = new Mxcsgx();
                Edge edge = (Edge) iteratorOutE.next();
                String lordId = edge.inVertex().id().toString();
                String followId = edge.outVertex().id().toString();
                mxcsgx.setLordId(lordId);
                mxcsgx.setFollowId(followId);
                mxcsgx.setRefType(edge.label());

                if(dataCount == 0 ) {
                    GraphTraversal lordIterator = g.V(lordId).valueMap();
                    while (lordIterator.hasNext()) {
                        LinkedHashMap properties = (LinkedHashMap) lordIterator.next();
                        ArrayList name = (ArrayList) properties.get("name");
                        ArrayList cardId = (ArrayList) properties.get("cardid");
                        lordName = name.get(0).toString();
                        LordCertificate = cardId.get(0).toString();
                        mxcsgx.setLordName(lordName);
                        mxcsgx.setLordCertificateCode("01");
                        mxcsgx.setLordCertificate(LordCertificate);
                    }
                }else{
                    mxcsgx.setLordName(lordName);
                    mxcsgx.setLordCertificateCode("01");
                    mxcsgx.setLordCertificate(LordCertificate);
                }

                GraphTraversal followIterator = g.V(followId).valueMap();
                while(followIterator.hasNext()) {
                    LinkedHashMap properties = (LinkedHashMap) followIterator.next();
                    ArrayList name = (ArrayList) properties.get("name");
                    ArrayList cardId = (ArrayList) properties.get("cardid");
                    mxcsgx.setFollowName(name.get(0).toString());
                    mxcsgx.setFollowCertificateCode("01");
                    mxcsgx.setFollowCertificate(cardId.get(0).toString());
                }

                mxcsgxs.add(mxcsgx);
                dataCount++;
            }
            Map map = new HashMap();
            map.put("dataCount",dataCount);
            results.add(map);
            results.add(mxcsgxs);
            returnJSON.setCmd("/v1/edge/getOutE");
            returnJSON.setCode(200);
            returnJSON.setData(results);
        }catch (Exception e){
            returnJSON.setCmd("/v1/edge/getOutE");
            returnJSON.setCode(500);
            returnJSON.setData(e.getMessage());
        }
        return returnJSON;
    }

}
