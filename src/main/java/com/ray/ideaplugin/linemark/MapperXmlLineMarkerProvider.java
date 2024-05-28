package com.ray.ideaplugin.linemark;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.Function;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import com.ray.ideaplugin.dom.model.IdDomElement;
import com.ray.ideaplugin.util.IconsConstants;
import com.ray.ideaplugin.util.JavaUtils;
import com.ray.ideaplugin.util.MapperUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

/**
 * Mapper Xml 行标记
 *
 * @author : zhengrf
 * @date : 2019-01-04
 */
public class MapperXmlLineMarkerProvider implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (!isMybatisMapperPsi(element)) {
            return null;
        }
        DomElement domElement = DomUtil.getDomElement(element);
        if (domElement.exists()) {
            Optional<PsiMethod> psiMethod = JavaUtils.findMethod(element.getProject(), (IdDomElement) domElement);
            return new LineMarkerInfo<PsiElement>(element, element.getTextRange(),
                    this.getIcon(), this.getTooltipProvider(psiMethod),
                    this.getNavigationTarget(psiMethod), GutterIconRenderer.Alignment.CENTER,
                    () -> "java dao");
        }
        return null;
    }

    private Function<? super PsiElement, String> getTooltipProvider(Optional<PsiMethod> psiMethod) {
        return (Function<PsiElement, String>) psiElement -> "java dao." + psiMethod.get().getName();
    }


    private GutterIconNavigationHandler getNavigationTarget(Optional<PsiMethod> psiMethod) {
        return (e, elt) -> {
            ((Navigatable) psiMethod.get().getNavigationElement()).navigate(true);
        };
    }

    private Icon getIcon() {
        return IconsConstants.STATEMENT_LINE_MARKER_ICON;
    }

    private boolean isMybatisMapperPsi(@NotNull PsiElement element) {
        return element instanceof XmlTag
                && MapperUtils.isElementWithinMybatisFile(element)
                && MapperUtils.isTargetType(element);
    }

}
