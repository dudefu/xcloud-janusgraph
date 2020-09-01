package com.xinyi.info.janusgraph.controller;


import com.xinyi.info.janusgraph.domain.model.ReturnJSON;
import com.xinyi.info.janusgraph.domain.service.EdgeService;
import com.xinyi.info.janusgraph.domain.service.VertexService;
import com.xinyi.info.janusgraph.remote.connect.RemoteGraphConnection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dudefu
 * @since 2020-08-06
 */
@Api(value = "边处理接口",tags = "边处理接口")
@RestController
@RequestMapping("/edge")
public class GraphEdgeController {

    @Autowired
    EdgeService edgeService ;

    private static GraphTraversalSource g;

    static {
        try {
            g = RemoteGraphConnection.getGraphTraversalSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增边
     * @return
     * @throws Exception
     */
    @PostMapping("/addE")
    @ApiOperation(value = "新增边",notes = "新增边")
    public ReturnJSON addE(
            @ApiParam(name = "vid1", value = "from顶点ID", required = true) @RequestParam(required = true) int vid1,
            @ApiParam(name = "vid2", value = "to顶点ID", required = true) @RequestParam(required = true) int vid2,
            @ApiParam(name = "ELabel", value = "边标签", required = true) @RequestParam(required = true) String ELabel)  {

        return  edgeService.addE(vid1,vid2,ELabel,g);

    }

    /**
     * 删除边
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteE")
    @ApiOperation(value = "删除边",notes = "删除边")
    public ReturnJSON deleteE(
            @ApiParam(name = "idName", value = "边的ID", required = true) @RequestParam(required = true) String idName)  {

        return  edgeService.deleteE(idName,g);

    }

    /**
     * 新增边标签
     * @return
     * @throws Exception
     */
    @PostMapping("/addELabel")
    @ApiOperation(value = "新增边标签",notes = "新增边标签")
    public ReturnJSON addELabel(
            @ApiParam(name = "ELabel", value = "边标签", required = true) @RequestParam(required = true) String ELabel)  {

        return  edgeService.addELabel(ELabel);

    }

    /**
     * 移除边标签
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteELabel")
    @ApiOperation(value = "移除边标签",notes = "移除边标签")
    public ReturnJSON deleteELabel(
            @ApiParam(name = "ELabel", value = "顶点ID", required = true) @RequestParam(required = true) String ELabel)  {
        return  edgeService.deleteELabel(ELabel);

    }

    /**
     * 获取所有边标签
     * @return
     * @throws Exception
     */
    @GetMapping("/getELabel")
    @ApiOperation(value = "获取所有边标签",notes = "获取所有边标签")
    public ReturnJSON getELabel()  {

        return  edgeService.getELabel();

    }

    /**
     * 获取所有边
     * @return
     * @throws Exception
     */
    @GetMapping("/getAllE")
    @ApiOperation(value = "获取所有边",notes = "遍历图所有的边，并逐条打印")
    public ReturnJSON getAllE() {

        return  edgeService.getAllE(g);

    }

    /**
     * 获取单条边
     * @return
     * @throws Exception
     */
    @GetMapping("/getE")
    @ApiOperation(value = "获取单条边",notes = "获取单条边")
    public ReturnJSON getE(
            @ApiParam(name = "cardidFrom", value = "主身份证号码", required = true) @RequestParam(required = true) String cardidFrom,
            @ApiParam(name = "cardidTo", value = "从身份证号码", required = true) @RequestParam(required = true) String cardidTo) {

        return  edgeService.getE(cardidFrom, cardidTo,g);

    }

    /**
     * 获取单条边
     * @return
     * @throws Exception
     */
    @GetMapping("/getOutE")
    @ApiOperation(value = "根据身份证号码获取某个顶点所有外向边",notes = "根据身份证号码获取某个顶点所有外向边")
    public ReturnJSON getOutE(
            @ApiParam(name = "cardid", value = "身份证号码", required = true) @RequestParam(required = true) String cardid,
            @ApiParam(name = "level", value = "层级", required = false) @RequestParam(required = false) Integer level) {

        return  edgeService.getOutE(cardid,level,g);

    }

}
