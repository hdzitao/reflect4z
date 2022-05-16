package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZClassList;
import hdzitao.reflect4z.list.ZGenericList;
import hdzitao.reflect4z.reflect.GenericResolver;
import hdzitao.reflect4z.reflect.JDKVersionResolver;
import hdzitao.reflect4z.reflect.MethodResolver;
import hdzitao.reflect4z.reflect.ObjectResolver;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * method封装类
 */
public class ZMethod extends ZExecutable<Method> implements InheritedElement, ZModifierElement {
    private Class<?> inheritedType;

    private ZMethod(Method method) {
        super(method);
        this.java.setAccessible(true);
    }

    /**
     * 创建ZMethod
     *
     * @param method Method
     * @return ZMethod
     */
    public static ZMethod forMethod(Method method) {
        return new ZMethod(method);
    }

    @Override
    public ZMethod withInheritedType(Class<?> inheritedType) {
        this.inheritedType = inheritedType;
        return this;
    }

    @Override
    public ZGenericList getGenericParameterZTypes() {
        return new ZGenericList(GenericResolver.resolveParamTypes(this.java, getInheritedType()));
    }

    /**
     * 获取返回类型
     *
     * @return 返回类型
     */
    public ZClass getReturnZType() {
        return ZClass.forClass(this.java.getReturnType());
    }

    /**
     * 获取返回类型(带泛型信息)
     *
     * @return 返回类型(带泛型信息)
     */
    public ZGeneric getGenericReturnZType() {
        return ZGeneric.forType(GenericResolver.resolveReturnType(this.java, getInheritedType()));
    }

    /**
     * 执行方法
     *
     * @param obj  实例
     * @param args 参数
     * @param <R>  返回结果类型
     * @return 方法执行结果
     */
    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <R> R invoke(Object obj, Object... args) {
        return (R) this.java.invoke(obj, args);
    }

    /**
     * 判断方法是否重写
     *
     * @param sub 子类方法
     * @return true 如果sub和此方法是相等或重写关系
     */
    public boolean isOverridden(Method sub) {
        return MethodResolver.isOverridden(this.java, sub);
    }

    @Override
    public Class<?> getInheritedType() {
        return this.inheritedType != null ? this.inheritedType : this.java.getDeclaringClass();
    }

    @Override
    public ZClass getDeclaringZClass() {
        return ZClass.forClass(this.java.getDeclaringClass());
    }

    @Override
    public String getName() {
        return this.java.getName();
    }

    @Override
    public ZClassList getParameterZTypes() {
        return new ZClassList(this.java.getParameterTypes());
    }

    @Override
    public int getParameterCount() {
        return this.java.getParameterTypes().length;
    }

    @Override
    public ZClassList getExceptionZTypes() {
        return new ZClassList(this.java.getExceptionTypes());
    }

    @Override
    protected Annotation[][] getParameterAnnotations() {
        return this.java.getParameterAnnotations();
    }

    @Override
    public ZModifier getModifiers() {
        return new ZModifier(this.java.getModifiers());
    }

    /**
     * 判断method是否是interface default method
     *
     * @return true 如果是接口默认方法
     */
    public boolean isDefault() {
        return JDKVersionResolver.isSinceJDK8() && (boolean) ObjectResolver.invoke(this.java, "isDefault");
    }
}
