package com.xinyi.info.janusgraph.domain.service.impl;

import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import com.xinyi.info.janusgraph.domain.service.VertexService;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 对顶点的操作
 * @author dudefu
 *
 */
@Service
public class VertexServiceImpl implements VertexService {

    @Override
    public ReturnJSON addV(String label,String name, String cardid,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            Vertex v = g.addV(label).property("name", name).property("cardid", cardid).next();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/vertex/addV");
            returnJSON.setData("添加顶点成功，vid = "+v.id());
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/vertex/addV");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON deleteV(int vid,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            g.V(vid).drop().iterate();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/vertex/deleteV");
            returnJSON.setData("删除顶点成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/vertex/deleteV");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON updateV(int vid,String name,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            g.V(vid).property(VertexProperty.Cardinality.single,"name",name).next();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/vertex/updateV");
            returnJSON.setData("更新顶点属性名成功");
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/vertex/updateV");
            returnJSON.setData(e.getMessage());
        }
        return returnJSON ;
    }

    @Override
    public ReturnJSON getV(String cardid,GraphTraversalSource g) {
        ReturnJSON returnJSON = new ReturnJSON();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            String valueMap = g.V().has("cardid",cardid).valueMap().next().toString();
            String vId = g.V().has("cardid",cardid).id().next().toString();
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/vertex/getV");
            if(null != valueMap || vId != null){
                returnJSON.setData(valueMap+", id="+vId);
            }else{
                returnJSON.setData("");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/vertex/getV");
            if(null != e.getMessage()) {
                returnJSON.setData(e.getMessage());
            }else{
                returnJSON.setData("该顶点不存在");
            }
        }
        return returnJSON ;
    }

    /**
     * n为空时，获取所有顶点信息
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJSON getAllVinfo(int n,GraphTraversalSource g) {

        ReturnJSON returnJSON = new ReturnJSON();
        List<Object> infoLists = new ArrayList<>();
        try{
//            GraphTraversalSource g = RemoteGraphConnection.getGraphTraversalSource();
            Long vcount = g.V().count().next();
            infoLists.add("图顶点总数："+vcount);
            GraphTraversal info = g.V().valueMap(true).limit(n);
            while(info.hasNext()){
                infoLists.add(info.next().toString());
            }
            returnJSON.setCode(200);
            returnJSON.setCmd("/v1/vertex/getAllVinfo");
            returnJSON.setData(infoLists);
        }catch (Exception e){
            returnJSON.setCode(500);
            returnJSON.setCmd("/v1/vertex/getAllVinfo");
            returnJSON.setData(e.getMessage());
        }

        return returnJSON ;

    }

}
