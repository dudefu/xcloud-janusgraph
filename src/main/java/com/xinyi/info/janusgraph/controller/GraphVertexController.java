package com.xinyi.info.janusgraph.controller;


import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import com.xinyi.info.janusgraph.domain.service.VertexService;
import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dudefu
 * @since 2020-08-06
 */
@Api(value = "顶点处理接口",tags = "顶点处理接口")
@RestController
@RequestMapping("/vertex")
public class GraphVertexController {

    @Autowired
    VertexService vertexService ;

    private static GraphTraversalSource g;

    static {
        try {
            g = RemoteGraphConnection.getGraphTraversalSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建带标签和属性的顶点
     * @return
     * @throws Exception
     */
    @PostMapping("/addV")
    @ApiOperation(value = "创建带标签和属性的顶点",notes = "创建带标签和属性的顶点")
    public ReturnJSON addV(
            @ApiParam(name = "VLabel", value = "顶点标签", required = true) @RequestParam(required = true) String VLabel,
            @ApiParam(name = "name", value = "姓名", required = true) @RequestParam(required = true) String name,
            @ApiParam(name = "cardid", value = "身份证号码", required = true) @RequestParam(required = true) String cardid) {

        return vertexService.addV(VLabel,name,cardid,g);

    }

    /**
     * 删除顶点
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteV")
    @ApiOperation(value = "删除顶点",notes = "删除顶点")
    public ReturnJSON deleteV(
            @ApiParam(name = "vid", value = "顶点ID", required = true) @RequestParam(required = true) int vid) {

        return vertexService.deleteV(vid, g);

    }

    /**
     * 更新顶点属性名
     * @return
     * @throws Exception
     */
    @PostMapping("/updateV")
    @ApiOperation(value = "更新顶点属性名",notes = "更新顶点属性名")
    public ReturnJSON updateV(
            @ApiParam(name = "vid", value = "顶点ID", required = true) @RequestParam(required = true) int vid,
            @ApiParam(name = "name", value = "newName", required = true) @RequestParam(required = true) String name) {

        return vertexService.updateV(vid,name,g);

    }

    /**
     * 获取单个顶点
     * @return
     * @throws Exception
     */
    @GetMapping("/getV")
    @ApiOperation(value = "获取单个顶点",notes = "获取单个顶点")
    public ReturnJSON getV(
            @ApiParam(name = "cardid", value = "身份证号码", required = true) @RequestParam(required = true) String cardid) {

        return vertexService.getV(cardid,g);

    }

    /**
     * n为空时，获取所有顶点
     * @return
     * @throws Exception
     */
    @GetMapping("/getAllVinfo")
    @ApiOperation(value = "获取所有顶点信息",notes = "n为空时，获取所有顶点")
    public ReturnJSON getAllVinfo(
            @ApiParam(name = "n", value = "limit查询限制条数", required = true) @RequestParam(required = true) int n) {

        return vertexService.getAllVinfo(n,g);

    }

}
