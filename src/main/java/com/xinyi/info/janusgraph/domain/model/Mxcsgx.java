package com.xinyi.info.janusgraph.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="返回结果")
@Data
public class Mxcsgx {
	@ApiModelProperty(value ="PCH")
    private String pch;

    @ApiModelProperty(value ="LORD_CERTIFICATE_CODE")
    private String lordCertificateCode;

    @ApiModelProperty(value ="FOLLOW_CERTIFICATE")
    private String followCertificate;

    @ApiModelProperty(value ="LORD_NAME")
    private String lordName;

    @ApiModelProperty(value ="LORD_TABLENAME")
    private String lordTablename;

    @ApiModelProperty(value ="LORD_ID")
    private String lordId;

    @ApiModelProperty(value ="FOLLOW_ID")
    private String followId;

    @ApiModelProperty(value ="LORD_CERTIFICATE")
    private String lordCertificate;

    @ApiModelProperty(value ="REF_END_TIME")
    private String refEndTime;

    @ApiModelProperty(value ="FOLLOW_NAME")
    private String followName;

    @ApiModelProperty(value ="REF_ADDRESS_CODE")
    private String refAddressCode;

    @ApiModelProperty(value ="FOLLOW_CERTIFICATE_CODE")
    private String followCertificateCode;

    @ApiModelProperty(value ="REF_TYPE")
    private String refType;

    @ApiModelProperty(value ="REF_ADDRESS_NAME")
    private String refAddressName;

    @ApiModelProperty(value ="REF_START_TIME")
    private String refStartTime;

    @ApiModelProperty(value ="REF_DESC")
    private String refDesc;

    @ApiModelProperty(value ="REF_DESC_CODE")
    private String refDescCode;

    @ApiModelProperty(value ="REF_POLICE_CODE")
    private String refPoliceCode;

    @ApiModelProperty(value ="FOLLOW_NUMBER1")
    private String followNumber1;

    @ApiModelProperty(value ="FOLLOW_TABLENAME")
    private String followTablename;

    @ApiModelProperty(value ="FOLLOW_NUMBER2")
    private String followNumber2;

    @ApiModelProperty(value ="MXLX")
    private String mxlx;


}