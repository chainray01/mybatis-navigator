package com.ray.ideaplugin.dom.model;

import com.intellij.util.xml.*;


public interface IdDomElement extends DomElement {

    @Required
    @NameValue
    @Attribute("id")
    GenericAttributeValue<String> getId();

    void setValue(String content);
}
