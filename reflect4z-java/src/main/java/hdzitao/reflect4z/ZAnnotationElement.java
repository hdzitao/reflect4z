package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZAnnotationList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 可以被注解的元素
 */
public interface ZAnnotationElement<E extends AnnotatedElement> extends Reflect<E> {

    /**
     * 原始注解
     *
     * @param annotationClass 想获取的注解类型
     * @param <A>             注解类型
     * @return 注解实例或null
     */
    <A extends Annotation> A getAnnotation(Class<A> annotationClass);

    /**
     * 注解 ZAnnotation封装
     *
     * @param annotationClass 想获取的注解类型
     * @return ZAnnotation封装的注解实例
     */
    ZAnnotation getZAnnotation(Class<? extends Annotation> annotationClass);

    /**
     * 判断有没有指定注解
     *
     * @param annotationClass 注解类型
     * @return true 如果被annotationClass注解
     */
    boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

    /**
     * 获取全部注解
     *
     * @return 全部注解
     */
    ZAnnotationList getZAnnotations();

    /**
     * 获取全部注解(不包含继承的)
     *
     * @return 全部注解(不包含继承的)
     */
    ZAnnotationList getDeclaredZAnnotations();
}
