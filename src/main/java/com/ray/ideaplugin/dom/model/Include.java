package com.ray.ideaplugin.dom.model;

import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;


public interface Include extends DomElement {

    @Attribute("refid")
        //@Convert(SqlConverter.class)
    GenericAttributeValue<XmlTag> getRefId();

}
