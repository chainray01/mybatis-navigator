package com.ray.ideaplugin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;


public interface SelectKey extends MapperWhereElement {
    @NotNull
    @Attribute("resultType")
        // @Convert(AliasConverter.class)
    GenericAttributeValue<PsiClass> getResultType();
}
