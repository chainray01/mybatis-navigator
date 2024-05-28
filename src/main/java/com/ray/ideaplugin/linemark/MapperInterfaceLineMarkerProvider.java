


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
import com.ray.ideaplugin.util.IconsConstants;
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
        return IconsConstants.SPRING_INJECTION_ICON;
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
                        .create(IconsConstants.SPRING_INJECTION_ICON)
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
                        .create(IconsConstants.SPRING_INJECTION_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        .setTargets(tags)
                        .setTooltipTitle("goto mapper file");
                result.add(builder.createLineMarkerInfo(psiClass.getNameIdentifier()));
            }
        }
    }
}
