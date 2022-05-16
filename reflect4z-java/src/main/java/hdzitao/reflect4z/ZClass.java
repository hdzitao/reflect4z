package hdzitao.reflect4z;

import hdzitao.reflect4z.list.*;
import hdzitao.reflect4z.reflect.AnnotationResolver;
import hdzitao.reflect4z.reflect.ClassResolver;
import hdzitao.reflect4z.reflect.ObjectResolver;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;

/**
 * Class的封装类
 */
public class ZClass extends ReflectElement<Class<?>>
        implements ZAnnotationElement<Class<?>>, Dynamic {

    /**
     * 创建ZClass
     *
     * @param clazz 类
     * @return ZClass封装的Class
     */
    public static ZClass forClass(Class<?> clazz) {
        return new ZClass(clazz);
    }

    /**
     * 用classLoader加载名字为name的类，并根据initialize执行初始化
     *
     * @param name        类名
     * @param initialize  是否初始化
     * @param classLoader 类加载器
     * @return ZClass封装的Class
     */
    @SneakyThrows
    public static ZClass forName(String name, boolean initialize, ClassLoader classLoader) {
        return new ZClass(Class.forName(name, initialize, classLoader));
    }

    /**
     * 用classLoader加载并初始化名字为name的类
     *
     * @param name        类名
     * @param classLoader 类加载器
     * @return ZClass封装的Class
     */
    @SneakyThrows
    public static ZClass forName(String name, ClassLoader classLoader) {
        return new ZClass(Class.forName(name, true, classLoader));
    }

    /**
     * 用默认类加载器加载并初始化名字为name的类
     *
     * @param name 类名
     * @return ZClass封装的Class
     */
    @SneakyThrows
    public static ZClass forName(String name) {
        return new ZClass(Class.forName(name));
    }

    private ZClass(Class<?> clazz) {
        super(clazz);
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
     * new一个ZObject
     *
     * @param args 构造函数参数
     * @param <T>  object类型
     * @return ZObject封装的一个新new的实例
     */
    @SuppressWarnings("unchecked")
    public <T> ZObject<T> newZObject(Object... args) {
        return (ZObject<T>) ZObject.forObject(newInstance(args));
    }

    /**
     * 自动获取构造方法new一个实例
     *
     * @param args 构造函数参数
     * @param <T>  object类型
     * @return 一个新new的实例
     */
    public <T> T newInstance(Object... args) {
        return ClassResolver.newInstance(this.java, args);
    }

    /**
     * 获取构造方法
     *
     * @param argTypes 参数类型列表
     * @return 构造函数
     */
    public ZConstructor getConstructor(Class<?>... argTypes) {
        return ZConstructor.forConstructor(ClassResolver.getConstructor(this.java, argTypes));
    }

    /**
     * 获取全部构造方法
     *
     * @return 全部构造方法
     */
    public ZConstructorList getConstructors() {
        return new ZConstructorList(this.java.getDeclaredConstructors());
    }



    /**
     * 获取字段
     *
     * @param name field名字
     * @return field
     */
    public ZField getField(String name) {
        return ZField.forField(ClassResolver.getField(this.java, name)).withInheritedType(this.java);
    }

    /**
     * 获取当前类字段
     *
     * @param name field名字
     * @return field
     */
    public ZField getDeclaredField(String name) {
        try {
            return ZField.forField(this.java.getDeclaredField(name)).withInheritedType(this.java);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * 全部字段
     *
     * @return 全部fields
     */
    public ZFieldList getFields() {
        return new ZFieldList(ClassResolver.getFields(this.java)).withInheritedType(this.java);
    }

    /**
     * 当前类的全部字段
     *
     * @return 当前类的全部字段
     */
    public ZFieldList getDeclaredFields() {
        return new ZFieldList(this.java.getDeclaredFields()).withInheritedType(this.java);
    }

    /**
     * 获取方法
     *
     * @param name     method名字
     * @param argsType 参数类型列表
     * @return method
     */
    public ZMethod getMethod(String name, Class<?>... argsType) {
        return ZMethod.forMethod(ClassResolver.getMethod(this.java, name, argsType)).withInheritedType(this.java);
    }

    /**
     * 获取当前类的方法
     *
     * @param name     method名字
     * @param argsType 参数类型列表
     * @return method
     */
    public ZMethod getDeclaredMethod(String name, Class<?>... argsType) {
        try {
            return ZMethod.forMethod(this.java.getDeclaredMethod(name, argsType)).withInheritedType(this.java);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 全部方法
     *
     * @return 全部methods
     */
    public ZMethodList getMethods() {
        return new ZMethodList(ClassResolver.getMethods(this.java)).withInheritedType(this.java);
    }

    /**
     * 当前类全部方法
     *
     * @return 当前类全部methods
     */
    public ZMethodList getDeclaredMethods() {
        return new ZMethodList(this.java.getDeclaredMethods()).withInheritedType(this.java);
    }

    /**
     * 获取继承的接口
     *
     * @return 全部实现的接口
     */
    public ZClassList getInterfaces() {
        return new ZClassList(ClassResolver.getInterfaces(this.java));
    }

    /**
     * 获取父类
     *
     * @return 父类集合
     */
    public ZClassList getFathers() {
        return new ZClassList(ClassResolver.getFathers(this.java));
    }

    /**
     * 获取包信息
     *
     * @return package
     */
    public Package getPackage() {
        return this.java.getPackage();
    }

    /**
     * 判断父类关系
     *
     * @param father 可能的父类
     * @return true 如果father是当前类的分类
     */
    public boolean is(Class<?> father) {
        return ClassResolver.isInherited(father, this.java, false);
    }

    /**
     * ClassLoader
     * ps:暂时没有不封装的计划
     *
     * @return ClassLoader
     */
    public ClassLoader getClassLoader() {
        return this.java.getClassLoader();
    }

    /**
     * 是否是数组
     *
     * @return true 如果是数组
     */
    public boolean isArray() {
        return this.java.isArray();
    }

    /**
     * 数组的底层类型
     *
     * @return 数组元素类型
     */
    public ZClass getComponentType() {
        return ZClass.forClass(this.java.getComponentType());
    }

    /**
     * 是否是接口
     *
     * @return true 如果是接口
     */
    public boolean isInterface() {
        return this.java.isInterface();
    }

    /**
     * 是否是注解
     *
     * @return true 如果是注解
     */
    public boolean isAnnotation() {
        return this.java.isAnnotation();
    }

    /**
     * 是否是枚举
     *
     * @return true 如果是枚举
     */
    public boolean isEnum() {
        return this.java.isEnum();
    }

    /**
     * 是否原始类型
     *
     * @return true 如果是原始类型
     */
    public boolean isPrimitive() {
        return this.java.isPrimitive();
    }

    /**
     * 获取完整的类名
     *
     * @return 类名
     */
    public String getName() {
        return this.java.getName();
    }

    /**
     * 获取类名
     *
     * @return 类名
     */
    public String getSimpleName() {
        return this.java.getSimpleName();
    }

    @Override
    public <R> R attr(String name) {
        return ObjectResolver.staticGet(this.java, name);
    }

    @Override
    public void attr(String name, Object value) {
        ObjectResolver.staticSet(this.java, name, value);
    }

    @Override
    public <T> T send(String methodName, Object... args) {
        return ObjectResolver.staticInvoke(this.java, methodName, args);
    }
}
