package com.ray.ideaplugin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface Beans extends DomElement {

    @NotNull
    @SubTagList("bean")
    List<Bean> getBeans();

}
