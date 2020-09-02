package com.xinyi.info.janusgraph.domain.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="顶点实体")
@Data
public class VertexEntity {

    @ApiModelProperty(value ="姓名")
    private String name;

    @ApiModelProperty(value ="身份证号码")
    private String cardid;

}
