package hdzitao.reflect4z.parameter;

import hdzitao.reflect4z.reflect.ObjectResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * jdk8版本的Parameter,主要功能转发给 java.lang.reflect.Parameter
 */
public class ParameterJDK8 implements Parameter {
    private final Object parameter;

    private ParameterJDK8(Object parameter) {
        this.parameter = parameter;
    }


    public static Parameter[] newParameters(Object[] parameters) {
        ParameterJDK8[] parametersJDK8 = new ParameterJDK8[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parametersJDK8[i] = new ParameterJDK8(parameters[i]);
        }

        return parametersJDK8;
    }

    @Override
    public int getIndex() {
        return ObjectResolver.get(this.parameter, "index");
    }

    @Override
    public boolean isNamePresent() {
        return ObjectResolver.invoke(this.parameter, "isNamePresent");
    }

    @Override
    public String getName() {
        return ObjectResolver.invoke(this.parameter, "getName");
    }

    @Override
    public Type getParameterizedType() {
        return ObjectResolver.invoke(this.parameter, "getParameterizedType");
    }

    @Override
    public Class<?> getType() {
        return ObjectResolver.invoke(this.parameter, "getType");
    }

    @Override
    public boolean isImplicit() {
        return ObjectResolver.invoke(this.parameter, "isImplicit");
    }

    @Override
    public boolean isSynthetic() {
        return ObjectResolver.invoke(this.parameter, "isSynthetic");
    }

    @Override
    public boolean isVarArgs() {
        return ObjectResolver.invoke(this.parameter, "isVarArgs");
    }

    @Override
    public int getModifiers() {
        return ObjectResolver.invoke(this.parameter, "getModifiers");
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return ObjectResolver.invoke(this.parameter, "isAnnotationPresent", annotationClass);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return ObjectResolver.invoke(this.parameter, "getAnnotation", annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return ObjectResolver.invoke(this.parameter, "getAnnotations");
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return ObjectResolver.invoke(this.parameter, "getDeclaredAnnotations");
    }
}
