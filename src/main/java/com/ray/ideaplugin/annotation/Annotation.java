package com.ray.ideaplugin.annotation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Annotation implements Cloneable {

    public static final Annotation PARAM = new Annotation("@Param", "org.apache.ibatis.annotations.Param");

    public static final Annotation SELECT = new Annotation("@Select", "org.apache.ibatis.annotations.Select");

    public static final Annotation UPDATE = new Annotation("@Update", "org.apache.ibatis.annotations.Update");

    public static final Annotation INSERT = new Annotation("@Insert", "org.apache.ibatis.annotations.Insert");

    public static final Annotation DELETE = new Annotation("@Delete", "org.apache.ibatis.annotations.Delete");

    public static final Annotation ALIAS = new Annotation("@Alias", "org.apache.ibatis.type.Alias");

    public static final Annotation AUTOWIRED = new Annotation("@Autowired", "org.springframework.beans.factory.annotation.Autowired");

    public static final Annotation RESOURCE = new Annotation("@Resource", "javax.annotation.Resource");

    public static final Set<Annotation> STATEMENT_SYMMETRIES = ImmutableSet.of(SELECT, UPDATE, INSERT, DELETE);

    private final String label;

    private final String qualifiedName;

    private Map<String, AnnotationValue> attributePairs;

    public Annotation(@NotNull String label, @NotNull String qualifiedName) {
        this.label = label;
        this.qualifiedName = qualifiedName;
        attributePairs = Maps.newHashMap();
    }

    private Annotation addAttribute(String key, AnnotationValue value) {
        this.attributePairs.put(key, value);
        return this;
    }

    public Annotation withAttribute(@NotNull String key, @NotNull AnnotationValue value) {
        Annotation copy = this.clone();
        copy.attributePairs = Maps.newHashMap(this.attributePairs);
        return copy.addAttribute(key, value);
    }

    public Annotation withValue(@NotNull AnnotationValue value) {
        return withAttribute("value", value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(label);
        if (!Iterables.isEmpty(attributePairs.entrySet())) {
            builder.append(setupAttributeText());
        }
        return builder.toString();
    }

    private String setupAttributeText() {
        Optional<String> singleValue = getSingleValue();
        return singleValue.isPresent() ? singleValue.get() : getComplexValue();
    }

    private String getComplexValue() {
        StringBuilder builder = new StringBuilder("(");
        for (String key : attributePairs.keySet()) {
            builder.append(key);
            builder.append(" = ");
            builder.append(attributePairs.get(key).toString());
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

    @NotNull
    public Optional<PsiClass> toPsiClass(@NotNull Project project) {
        return Optional.ofNullable(JavaPsiFacade.getInstance(project).findClass(getQualifiedName(), GlobalSearchScope.allScope(project)));
    }

    private Optional<String> getSingleValue() {
        try {
            String value = Iterables.getOnlyElement(attributePairs.keySet());
            String builder = "(" + attributePairs.get(value).toString() +
                    ")";
            return Optional.of(builder);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    @NotNull
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    protected Annotation clone() {
        try {
            return (Annotation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException();
        }
    }

    public interface AnnotationValue {
    }

    public static class StringValue implements AnnotationValue {

        private final String value;

        public StringValue(@NotNull String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "\"" + value + "\"";
        }

    }

}
