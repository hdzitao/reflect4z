package hdzitao.reflect4z.reflect;

import hdzitao.reflect4z.ZAnnotation;
import hdzitao.reflect4z.list.ZAnnotationList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 注解帮助类
 */
public final class AnnotationResolver {
    private AnnotationResolver() {
    }

    /**
     * 原始注解
     *
     * @param annotationClass 想获取的注解类型
     * @param <A>             注解类型
     * @return 注解实例或null
     */
    public static <A extends Annotation> A getJavaAnnotation(AnnotatedElement java, Class<A> annotationClass) {
        return java.getAnnotation(annotationClass);
    }

    /**
     * 注解 ZAnnotation封装
     *
     * @param annotationClass 想获取的注解类型
     * @return ZAnnotation封装的注解实例
     */
    public static ZAnnotation getAnnotation(AnnotatedElement java, Class<? extends Annotation> annotationClass) {
        return ZAnnotation.forAnnotation(getJavaAnnotation(java, annotationClass));
    }

    /**
     * 判断有没有指定注解
     *
     * @param annotationClass 注解类型
     * @return true 如果被annotationClass注解
     */
    public static boolean isAnnotationPresent(AnnotatedElement java, Class<? extends Annotation> annotationClass) {
        return java.getAnnotation(annotationClass) != null;
    }

    /**
     * 获取全部注解
     *
     * @return 全部注解
     */
    public static ZAnnotationList getAnnotations(AnnotatedElement java) {
        return new ZAnnotationList(java.getAnnotations());
    }

    /**
     * 获取全部注解(不包含继承的)
     *
     * @return 全部注解(不包含继承的)
     */
    public static ZAnnotationList getDeclaredAnnotations(AnnotatedElement java) {
        return new ZAnnotationList(java.getDeclaredAnnotations());
    }
}
