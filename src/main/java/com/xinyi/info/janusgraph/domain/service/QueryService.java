package com.xinyi.info.janusgraph.domain.service;


import com.xinyi.info.janusgraph.domain.entity.QueryResult;
import com.xinyi.info.janusgraph.domain.entity.vo.PropertyVo;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
public interface QueryService {


    QueryResult query(String host, int port, String gremlin, String sourceName);

    PropertyVo getValueMap(String host, int port, String sourceName, String id, boolean vertex);

}
