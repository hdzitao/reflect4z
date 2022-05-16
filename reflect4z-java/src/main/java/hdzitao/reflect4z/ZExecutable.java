package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZAnnotationList;
import hdzitao.reflect4z.list.ZClassList;
import hdzitao.reflect4z.list.ZGenericList;
import hdzitao.reflect4z.list.ZParameterList;
import hdzitao.reflect4z.parameter.ParameterJDK7;
import hdzitao.reflect4z.parameter.ParameterJDK8;
import hdzitao.reflect4z.reflect.AnnotationResolver;
import hdzitao.reflect4z.reflect.JDKVersionResolver;
import hdzitao.reflect4z.reflect.ObjectResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

/**
 * 可执行对象(构造函数 and method)封装抽象类
 */
public abstract class ZExecutable<E extends AnnotatedElement> extends ReflectElement<E>
        implements ZAnnotationElement<E> {

    public ZExecutable(E java) {
        super(java);
    }

    @Override
    public <A extends Annotation> A getJavaAnnotation(Class<A> annotationClass) {
        return AnnotationResolver.getJavaAnnotation(this.java, annotationClass);
    }

    @Override
    public ZAnnotation getAnnotation(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.getAnnotation(this.java, annotationClass);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.isAnnotationPresent(this.java, annotationClass);
    }

    @Override
    public ZAnnotationList getAnnotations() {
        return AnnotationResolver.getAnnotations(this.java);
    }

    @Override
    public ZAnnotationList getDeclaredAnnotations() {
        return AnnotationResolver.getDeclaredAnnotations(this.java);
    }

    /**
     * 获取声明类
     *
     * @return 声明类
     */
    public abstract ZClass getDeclaringClass();

    /**
     * 获取名字
     *
     * @return 名字
     */
    public abstract String getName();

    /**
     * 获取参数类型列表
     *
     * @return 参数类型列表
     */
    public abstract ZClassList getParameterTypes();

    /**
     * 获取参数类型(带泛型信息)列表
     * <p>
     * method泛型可以被子类指定
     * constructor不能继承
     * 所以这个方法交给ZMethod和ZConstructor自己去实现
     *
     * @return 参数类型(带泛型信息)列表
     */
    public abstract ZGenericList getGenericParameterTypes();

    /**
     * 获取参数数量
     *
     * @return 参数数量
     */
    public abstract int getParameterCount();

    /**
     * 获取声明的异常类型列表
     *
     * @return 声明的异常类型列表
     */
    public abstract ZClassList getExceptionTypes();

    /**
     * 执行方法
     *
     * @param obj  实例
     * @param args 参数
     * @param <R>  返回值类型
     * @return 方法执行返回值
     */
    public abstract <R> R invoke(Object obj, Object... args);

    /**
     * 获取参数列表
     *
     * @return 参数列表
     */
    public ZParameterList getParameters() {
        if (JDKVersionResolver.isSinceJDK8()) {
            Object[] parameters = ObjectResolver.invoke(this.java, "getParameters");
            return new ZParameterList(ParameterJDK8.newParameters(parameters));
        } else {
            Class<?>[] parameterTypes = getParameterTypes().java();
            Type[] genericParameterTypes = getGenericParameterTypes().java();
            Annotation[][] annotations = getParameterAnnotations();
            return new ZParameterList(ParameterJDK7.newParameters(parameterTypes, genericParameterTypes, annotations));
        }
    }

    /**
     * 参数注解列表
     *
     * @return 参数注解列表
     */
    protected abstract Annotation[][] getParameterAnnotations();
}
