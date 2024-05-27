package com.ray.ideaplugin.dom.converter;

import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.ConvertContext;

import com.ray.ideaplugin.dom.model.Mapper;
import com.ray.ideaplugin.util.JavaUtils;
import com.ray.ideaplugin.util.MapperUtils;


import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;


public class DaoMethodConverter extends ConverterAdaptor<PsiMethod> {

    @Nullable
    @Override
    public PsiMethod fromString(@Nullable @NonNls String id, ConvertContext context) {
        Mapper mapper = MapperUtils.getMapper(context.getInvocationElement());
        return JavaUtils.findMethod(context.getProject(), MapperUtils.getNamespace(mapper), id).orElse(null);
    }

}