package com.ray.ideaplugin.alias;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import com.ray.ideaplugin.dom.model.DomPackage;
import com.ray.ideaplugin.util.MapperUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;


public class ConfigPackageAliasResolver extends PackageAliasResolver {

    public ConfigPackageAliasResolver(Project project) {
        super(project);
    }

    @NotNull
    @Override
    public Collection<String> getPackages(@Nullable PsiElement element) {
        final ArrayList<String> result = Lists.newArrayList();
        MapperUtils.processConfiguredPackage(project, new Processor<DomPackage>() {
            @Override
            public boolean process(DomPackage pkg) {
                result.add(pkg.getName().getStringValue());
                return true;
            }
        });
        return result;
    }

}
