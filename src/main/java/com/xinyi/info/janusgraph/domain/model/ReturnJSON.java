package com.xinyi.info.janusgraph.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="返回结果")
public class ReturnJSON<T>
{
	@ApiModelProperty(value="状态码")
    private Integer code = 200 ;// 是否成功 200：成功
	
	@ApiModelProperty(value="路径")
    private String cmd = "";
	
	@ApiModelProperty(value="请求数据")
    private T data = null;

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getCmd()
    {
        return cmd;
    }

    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
