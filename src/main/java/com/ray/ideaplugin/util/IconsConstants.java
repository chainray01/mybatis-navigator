package com.ray.ideaplugin.util;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;


public interface IconsConstants {

    Icon STATEMENT_LINE_MARKER_ICON = IconLoader.getIcon("/gutter/implementingMethod.svg", IconsConstants.class);

    Icon SPRING_INJECTION_ICON = IconLoader.getIcon("/gutter/implementedMethod.svg", IconsConstants.class);
}