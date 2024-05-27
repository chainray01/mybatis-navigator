package com.ray.ideaplugin.dom.model;

import com.intellij.util.xml.SubTagList;

import java.util.List;


public interface MapperSelectElement extends MapperParamElement {

    @SubTagList("selectKey")
    List<SelectKey> getSelectKey();

}
