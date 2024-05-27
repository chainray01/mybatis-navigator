/*
 * Copyright (C) 2019-2021 cofe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */


package com.ray.ideaplugin.linemark;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;

import com.ray.ideaplugin.dom.model.IdDomElement;
import com.ray.ideaplugin.dom.model.Mapper;
import com.ray.ideaplugin.service.ProjectService;
import com.ray.ideaplugin.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.ray.ideaplugin.util.JavaUtils.isElementWithinInterface;

/**
 * 接口标记
 */
public class MapperInterfaceLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    public String getName() {
        return "MapperInterfaceLineMarkerProvider";
    }

    @Override
    public Icon getIcon() {
        return Icons.SPRING_INJECTION_ICON;
    }

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (element instanceof PsiClass && ((PsiClass) element).isInterface()) {
            markerInterface(((PsiClass) element), result);
        } else if (element instanceof PsiMethod && isElementWithinInterface(element)) {
            markerMethod(((PsiMethod) element).getContainingClass(), ((PsiMethod) element), result);
        }
    }

    private void markerMethod(PsiClass psiClass, PsiMethod method, Collection<? super RelatedItemLineMarkerInfo<PsiElement>> result) {
        if (method.getNameIdentifier() != null) {
            CommonProcessors.CollectProcessor<IdDomElement> processor = new CommonProcessors.CollectProcessor<>();
            ProjectService.getInstance(psiClass.getProject()).process(method, processor);
            Collection<IdDomElement> results = processor.getResults();
            if (!results.isEmpty()) {
                List<XmlTag> tags = results.stream().map(DomElement::getXmlTag).collect(Collectors.toList());
                NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder
                        .create(Icons.SPRING_INJECTION_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        .setTargets(tags)
                        .setTooltipTitle("mapper."+method.getName());
                result.add(builder.createLineMarkerInfo(method.getNameIdentifier()));
            }
        }
    }

    private void markerInterface(PsiClass psiClass, Collection<? super RelatedItemLineMarkerInfo<PsiElement>> result) {
        if (psiClass.getNameIdentifier() != null) {
            CommonProcessors.CollectProcessor<Mapper> processor = new CommonProcessors.CollectProcessor<>();
            ProjectService.getInstance(psiClass.getProject()).process(psiClass, processor);
            Collection<Mapper> results = processor.getResults();
            if (!results.isEmpty()){
                List<XmlTag> tags = results.stream().map(x -> x.getXmlTag()).collect(Collectors.toList());
                NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder
                        .create(Icons.SPRING_INJECTION_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        .setTargets(tags)
                        .setTooltipTitle("goto mapper file");
                result.add(builder.createLineMarkerInfo(psiClass.getNameIdentifier()));
            }
        }
    }
}
