package hdzitao.reflect4z.reflect;

import java.lang.reflect.Method;

/**
 * 方法反射帮助类
 */
public final class MethodResolver {
    private MethodResolver() {
    }

    /**
     * 分析参数
     *
     * @param args  参数数组
     * @param index 参数索引
     * @param <T>   返回类型
     * @return 返回args[index]并强转成T类型 or 越界null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getArgs(Object[] args, int index) {
        try {
            return (T) args[index];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return null;
        }
    }

    /**
     * 参数对应的class数组
     *
     * @param args 参数值
     * @return 参数类型
     */
    public static Class<?>[] getArgTypes(Object... args) {
        // null转空
        if (args == null) {
            return new Class<?>[0];
        }

        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            Object parameter = args[i];
            classes[i] = parameter != null ? parameter.getClass() : null;
        }
        return classes;
    }

    /**
     * 判断两组参数对应位置的类型是否有继承关系
     *
     * @param origins 一组参数
     * @param gives   另一组参数
     * @return true 如果两组参数长度相同并且对应位置的参数类型是相等或继承关系
     */
    public static boolean isInheritedArgTypes(Class<?>[] origins, Class<?>[] gives, boolean box) {
        // 匹配参数
        if (origins.length == gives.length) { // 参数长度
            // 参数类型
            for (int i = 0; i < origins.length; i++) {
                Class<?> origin = origins[i];
                Class<?> give = gives[i];
                if (!ClassResolver.isInherited(origin, give, box)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断两组参数对应位置的类型是否相等
     *
     * @param origins 一组参数
     * @param gives   另一组参数
     * @return true 如果两组参数长度相同并且对应位置的参数类型是相等的
     */
    public static boolean isEqualsArgTypes(Class<?>[] origins, Class<?>[] gives, boolean box) {
        if (origins.length == gives.length) { // 参数长度
            // 参数类型
            for (int i = 0; i < origins.length; i++) {
                // 原始类型用包装类型代替
                Class<?> origin = origins[i];
                Class<?> give = gives[i];
                if (!ClassResolver.isEquals(origin, give, box)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断两个method相等或重写
     *
     * @param father 父类方法
     * @param sub    子类方法
     * @return true 如果相等或重写
     */
    public static boolean isOverridden(Method father, Method sub) {
        if (father == sub) {
            return true;
        }

        Class<?> fatherClass = father.getDeclaringClass();
        Class<?> subClass = sub.getDeclaringClass();
        return ClassResolver.isInherited(fatherClass, subClass, false)
                //&& father.getReturnType().equals(sub.getReturnType())
                && father.getName().equals(sub.getName())
                && isEqualsArgTypes(father.getParameterTypes(), sub.getParameterTypes(), false);
    }

}
