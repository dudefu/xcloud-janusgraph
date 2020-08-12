package com.xinyi.info.janusgraph.domain.service;

import com.xinyi.info.janusgraph.domain.model.ReturnJSON;

import java.util.List;

public interface EdgeService {

    ReturnJSON addE(int vid1 , int vid2 ,String ELabel) ;

    ReturnJSON deleteE(String idName) ;

    ReturnJSON getELabel();

    ReturnJSON addELabel(String labelName);

    ReturnJSON deleteELabel(String labelName);

    ReturnJSON getAllE() ;

    ReturnJSON getE(String fromName ,String toName);

}
