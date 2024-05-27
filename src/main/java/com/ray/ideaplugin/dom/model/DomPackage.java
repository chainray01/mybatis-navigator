package com.ray.ideaplugin.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;


public interface DomPackage extends DomElement {

    @NotNull
    @Attribute("name")
    GenericAttributeValue<String> getName();

}
