package com.ray.ideaplugin.dom.converter;

import com.intellij.util.xml.ConvertContext;


import com.ray.ideaplugin.dom.model.IdDomElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.ray.ideaplugin.dom.model.Mapper;
import java.util.Collection;


public class SqlConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper, ConvertContext context) {
        return mapper.getSqls();
    }

}
