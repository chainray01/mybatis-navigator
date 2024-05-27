package com.ray.ideaplugin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.ray.ideaplugin.dom.converter.AliasConverter;
import org.jetbrains.annotations.NotNull;


public interface Select extends MapperParamElement, ResultMapGroup {

    @NotNull
    @Attribute("resultType")
    @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getResultType();
}