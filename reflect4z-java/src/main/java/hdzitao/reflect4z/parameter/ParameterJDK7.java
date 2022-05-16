package hdzitao.reflect4z.parameter;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * jdk7版本的Parameter,主要功能自己实现
 * 一半功能暂未实现
 */
public class ParameterJDK7 implements Parameter {
    @Getter
    private final int index;

    @Getter
    private final Class<?> type;

    @Getter
    private final Type parameterizedType;

    private final Map<Class<? extends Annotation>, Annotation> annotations;

    private ParameterJDK7(int index, Class<?> type, Type genericParameterType, Annotation[] annotations) {
        this.index = index;
        this.type = type;
        this.parameterizedType = genericParameterType;
        this.annotations = new LinkedHashMap<>();
        for (Annotation annotation : annotations) {
            this.annotations.put(annotation.annotationType(), annotation);
        }
    }

    public static Parameter[] newParameters(Class<?>[] parameterTypes, Type[] genericParameterTypes, Annotation[][] parameterAnnotations) {
        ParameterJDK7[] parameters = new ParameterJDK7[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            Type genericParameterType = genericParameterTypes[i];
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            parameters[i] = new ParameterJDK7(i, parameterType, genericParameterType, parameterAnnotation);
        }

        return parameters;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.annotations.containsKey(annotationClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return (A) this.annotations.get(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.annotations.values().toArray(new Annotation[0]);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return getAnnotations();
    }

    @Override
    public boolean isNamePresent() {
        throw new UnsupportedOperationException("isNamePresent");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("getName");
    }

    @Override
    public boolean isImplicit() {
        throw new UnsupportedOperationException("isImplicit");
    }

    @Override
    public boolean isSynthetic() {
        throw new UnsupportedOperationException("isSynthetic");
    }

    @Override
    public boolean isVarArgs() {
        throw new UnsupportedOperationException("isVarArgs");
    }

    @Override
    public int getModifiers() {
        throw new UnsupportedOperationException("getModifiers");
    }
}
