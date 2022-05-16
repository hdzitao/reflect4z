package hdzitao.reflect4z;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解封装类
 */
public class ZAnnotation extends ReflectElement<Annotation> {
    private ZAnnotation(Annotation annotation) {
        super(annotation);
    }

    /**
     * 创建ZAnnotation
     *
     * @param annotation 注解
     * @return ZAnnotation封装的注解
     */
    public static ZAnnotation forAnnotation(Annotation annotation) {
        return new ZAnnotation(annotation);
    }

    /**
     * 判断注解类型
     *
     * @param clazz 注解类
     * @return true 如果是类型clazz的注解
     */
    public boolean is(Class<? extends Annotation> clazz) {
        return clazz.equals(this.java.annotationType());
    }

    /**
     * 强转注解类型,获取注解实例
     *
     * @param clazz 期待强转的注解类型
     * @param <T>   注解类型
     * @return 强转后的注解实例
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T as(Class<T> clazz) {
        return is(clazz) ? (T) this.java : null;
    }

    /**
     * 获取注解属性值
     *
     * @param name 属性名字
     * @param <T>  属性类型
     * @return 属性值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        Method method = this.java.annotationType().getDeclaredMethod(name);
        return (T) method.invoke(this.java);
    }

    /**
     * 获取属性value的值
     *
     * @param <T> value属性的类型
     * @return value属性值
     */
    public <T> T value() {
        return get("value");
    }

    /**
     * 获取注解类型
     *
     * @return 注解类型
     */
    public ZClass annotationZType() {
        return ZClass.forClass(this.java.annotationType());
    }

}
