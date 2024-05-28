package com.ray.ideaplugin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface MapperParamElement extends MapperWhereElement, IdDomElement {

    @SubTagList("bind")
    List<Bind> getBinds();

    @NotNull
    @Attribute("parameterMap")
        //@Convert(ParameterMapConverter.class)
    GenericAttributeValue<XmlTag> getParameterMap();

    @Attribute("id")
        // @Convert(DaoMethodConverter.class)
    GenericAttributeValue<String> getId();

    @NotNull
    @Attribute("parameterType")
        //@Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getParameterType();
}
