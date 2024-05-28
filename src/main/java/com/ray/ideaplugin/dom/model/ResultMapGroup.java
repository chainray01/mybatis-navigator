package com.ray.ideaplugin.dom.model;

import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;


public interface ResultMapGroup extends DomElement {

    @NotNull
    @Attribute("resultMap")
        //@Convert(ResultMapConverter.class)
    GenericAttributeValue<XmlTag> getResultMap();
}
