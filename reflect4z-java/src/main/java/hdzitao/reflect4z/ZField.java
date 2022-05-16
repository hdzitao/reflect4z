package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZAnnotationList;
import hdzitao.reflect4z.reflect.AnnotationResolver;
import hdzitao.reflect4z.reflect.GenericResolver;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * field封装类
 */
public class ZField extends ReflectElement<Field>
        implements ZAnnotationElement<Field>, InheritedElement, ZModifierElement {

    /**
     * 子类信息
     */
    private Class<?> inheritedType;

    private ZField(Field field) {
        super(field);
        this.java.setAccessible(true);
    }

    /**
     * 创建ZField
     *
     * @param field Field
     * @return ZField
     */
    public static ZField forField(Field field) {
        return new ZField(field);
    }

    @Override
    public ZField withInheritedType(Class<?> inheritedType) {
        this.inheritedType = inheritedType;
        return this;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return AnnotationResolver.getJavaAnnotation(this.java, annotationClass);
    }

    @Override
    public ZAnnotation getZAnnotation(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.getAnnotation(this.java, annotationClass);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.isAnnotationPresent(this.java, annotationClass);
    }

    @Override
    public ZAnnotationList getZAnnotations() {
        return AnnotationResolver.getAnnotations(this.java);
    }

    @Override
    public ZAnnotationList getDeclaredZAnnotations() {
        return AnnotationResolver.getDeclaredAnnotations(this.java);
    }

    /**
     * 获取声明类
     *
     * @return 声明类
     */
    public ZClass getDeclaringZClass() {
        return ZClass.forClass(this.java.getDeclaringClass());
    }

    /**
     * 获取名字
     *
     * @return 名字
     */
    public String getName() {
        return this.java.getName();
    }

    /**
     * 获取字段类型
     *
     * @return field类型
     */
    public ZClass getZType() {
        return ZClass.forClass(this.java.getType());
    }

    /**
     * 获取字段泛型
     *
     * @return 字段泛型
     */
    public ZGeneric getGenericZType() {
        return ZGeneric.forType(GenericResolver.resolveFieldType(this.java, getInheritedType()));
    }

    /**
     * 获取属性值
     *
     * @param obj 实例
     * @param <F> field类型
     * @return 属性值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <F> F get(Object obj) {
        return (F) this.java.get(obj);
    }

    /**
     * 设置属性值
     *
     * @param obj   实例
     * @param value 属性值
     */
    @SneakyThrows
    public void set(Object obj, Object value) {
        this.java.set(obj, value);
    }

    @Override
    public Class<?> getInheritedType() {
        return this.inheritedType != null ? this.inheritedType : this.java.getDeclaringClass();
    }

    @Override
    public ZModifier getModifiers() {
        return new ZModifier(this.java.getModifiers());
    }
}
