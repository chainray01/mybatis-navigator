package com.ray.ideaplugin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;


public interface TypeAlias extends DomElement {

    @NotNull
    @Attribute("type")
    GenericAttributeValue<PsiClass> getType();

    @NotNull
    @Attribute("alias")
    GenericAttributeValue<String> getAlias();

}
