package com.xinyi.info.janusgraph.controller;

import com.xinyi.info.janusgraph.domain.entity.QueryResult;
import com.xinyi.info.janusgraph.domain.entity.vo.PropertyVo;
import com.xinyi.info.janusgraph.domain.service.QueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * origins： 允许可访问的域列表
 * maxAge:准备响应前的缓存持续的最大时间（以秒为单位）
 *
 */

@Api(value = "命令查询接口",tags = "命令查询接口")
@RequestMapping("/gremlin")
@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/query")
    public QueryResult query(String host, int port, String sourceName, String gremlin) {
        return queryService.query(host, port, gremlin, sourceName);
    }

    @GetMapping("/vertex")
    public PropertyVo queryVertex(String host, int port, String sourceName, String id) {
        return queryService.getValueMap(host, port, sourceName, id, true);
    }

    @GetMapping("/edge")
    public PropertyVo queryEdge(String host, int port, String sourceName, String id) {
        return queryService.getValueMap(host, port, sourceName, id, false);
    }


}
