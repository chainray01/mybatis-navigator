package com.ray.ideaplugin.dom.model;

import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;


public interface PropertyGroup extends DomElement {

    @Attribute("property")
        //@Convert(PropertyConverter.class)
    GenericAttributeValue<XmlAttributeValue> getProperty();
}
