package hdzitao.reflect4z.reflect;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * object反射帮助类
 */
public final class ObjectResolver {
    private ObjectResolver() {
    }

    /**
     * 获取静态字段值
     *
     * @param clazz 类
     * @param name  field名字
     * @param <T>   field类型
     * @return field值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T staticGet(Class<?> clazz, String name) {
        Field field = privateGetField(clazz, name);
        checkStatic(clazz, name, field);
        return (T) field.get(null);
    }

    /**
     * 设置静态字段值
     *
     * @param clazz 类
     * @param name  field名字
     * @param value field值
     */
    @SneakyThrows
    public static void staticSet(Class<?> clazz, String name, Object value) {
        Field field = privateGetField(clazz, name);
        checkStatic(clazz, name, field);
        field.set(null, value);
    }

    /**
     * 获取对象字段值
     *
     * @param that 实例
     * @param name field名字
     * @param <T>  field类型
     * @return field值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T get(Object that, String name) {
        return (T) privateGetField(that.getClass(), name).get(that);
    }

    /**
     * 设置对象字段值
     *
     * @param that  实例
     * @param name  field名字
     * @param value field值
     */
    @SneakyThrows
    public static void set(Object that, String name, Object value) {
        privateGetField(that.getClass(), name).set(that, value);
    }

    /**
     * 调用静态方法
     *
     * @param clazz 类
     * @param name  方法名
     * @param args  参数
     * @param <T>   方法返回类型
     * @return 方法返回值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T staticInvoke(Class<?> clazz, String name, Object... args) {
        Method method = privateGetMethod(clazz, name, args);
        checkStatic(clazz, name, method);
        return (T) method.invoke(null, args);
    }

    /**
     * 调用方法
     *
     * @param that 实例
     * @param name 方法名
     * @param args 参数
     * @param <T>  方法返回类型
     * @return 方法返回值
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object that, String name, Object... args) {
        return (T) privateGetMethod(that.getClass(), name, args).invoke(that, args);
    }

    // 私有方法 =============================================================================================

    /**
     * 获取field
     *
     * @param clazz 类
     * @param name  field名
     * @return field
     */
    private static Field privateGetField(Class<?> clazz, String name) {
        Field field = ClassResolver.getField(clazz, name);
        field.setAccessible(true);
        return field;
    }

    /**
     * 获取method
     *
     * @param clazz 类
     * @param name  method名
     * @return method
     */
    private static Method privateGetMethod(Class<?> clazz, String name, Object[] args) {
        Method method = ClassResolver.getMethod(clazz, name, MethodResolver.getArgTypes(args));
        method.setAccessible(true);
        return method;
    }

    /**
     * 判断是否是静态的，非静态直接异常
     *
     * @param clazz  类
     * @param name   名字,用于保存信息
     * @param member field or method
     */
    private static void checkStatic(Class<?> clazz, String name, Member member) {
        if (!Modifier.isStatic(member.getModifiers())) {
            throw new IllegalArgumentException(member + " is not a static Member.");
        }
    }
}
