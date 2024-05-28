package com.ray.ideaplugin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;


public interface ResultMap extends GroupFour, IdDomElement {

    @NotNull
    @Attribute("extends")
        //@Convert(ResultMapConverter.class)
    GenericAttributeValue<XmlAttributeValue> getExtends();

    @NotNull
    @Attribute("type")
        // @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getType();

}