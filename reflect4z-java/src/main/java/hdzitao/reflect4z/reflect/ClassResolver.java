package hdzitao.reflect4z.reflect;

import lombok.SneakyThrows;

import java.lang.reflect.*;
import java.util.*;

/**
 * 类反射帮助类
 */
public final class ClassResolver {
    private ClassResolver() {
    }

    /**
     * 自动获取构造方法new一个实例
     *
     * @param clazz 类
     * @param args  构造函数参数
     * @param <T>   类型
     * @return clazz的一个新实例
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T newInstance(Class<?> clazz, Object... args) {
        if (clazz.isInterface()          // interface
                || clazz.isAnnotation()  // 注解
                || clazz.isPrimitive()   // 原始类
                || clazz.isEnum()) {     // 枚举
            // 不能实例化的class
            throw new IllegalArgumentException(clazz.getName() + " cannot be instantiated");
        } else if (clazz.isArray()) {    // 数组
            // 参数转int[]
            int[] intArgs = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                intArgs[i] = (int) args[i];
            }
            return (T) Array.newInstance(clazz, intArgs);
        } else {
            Class<?>[] argTypes = MethodResolver.getArgTypes(args);
            Constructor<?> constructor = getConstructor(clazz, argTypes);
            constructor.setAccessible(true);
            return (T) constructor.newInstance(args);
        }

    }

    /**
     * 构造方法
     *
     * @param clazz    类
     * @param argTypes 构造函数参数类型
     * @return 构造函数 or null
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... argTypes) {
        List<Constructor<?>> candidates = new ArrayList<>();
        // 精确查找
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (voteConstructorCandidates(constructor, argTypes, candidates)) {
                return constructor;
            }
        }
        // 查找有继承关系的
        Constructor<?> constructor = selectCandidates(candidates);
        if (constructor == null) {
            throw new IllegalArgumentException("Can't find " +
                    clazz.getName() + ".<init> with "
                    + Arrays.toString(argTypes));
        }
        return constructor;
    }

    /**
     * 字段
     *
     * @param clazz 类
     * @param name  field名字
     * @return field or null
     */
    public static Field getField(Class<?> clazz, String name) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException ignore) {
            }
        } while ((current = current.getSuperclass()) != null);

        throw new IllegalArgumentException("Can't find " + clazz.getName() + "." + name);
    }

    /**
     * 全部字段
     *
     * @param clazz 类
     * @return 所有field
     */
    public static Field[] getFields(Class<?> clazz) {
        Class<?> current = clazz;
        List<Field> fields = new LinkedList<>();
        do {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
        } while ((current = current.getSuperclass()) != null);

        return fields.toArray(new Field[0]);
    }

    /**
     * 方法
     *
     * @param clazz    类
     * @param name     方法名字
     * @param argsType 参数类型
     * @return method or null
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... argsType) {
        List<Method> candidates = new ArrayList<>();
        // public方法候选 with interface default method
        for (Method method : clazz.getMethods()) {
            if (voteMethodCandidates(method, name, argsType, candidates)) {
                return method;
            }
        }
        // 候选方法选举
        Method candidate;
        if ((candidate = selectCandidates(candidates)) != null) {
            return candidate;
        }
        // protected方法候选
        List<Method> privateMethods = new ArrayList<>();
        Class<?> current = clazz;
        do {
            for (Method method : current.getDeclaredMethods()) {
                int modifiers = method.getModifiers();
                // 不再处理public方法
                if (Modifier.isPublic(modifiers)) {
                    continue;
                }
                if (!Modifier.isProtected(modifiers)) {
                    // 保留private(或default)方法，暂时不处理
                    privateMethods.add(method);
                } else if (voteMethodCandidates(method, name, argsType, candidates)) {
                    // 选举protected
                    return method;
                }
            }
        } while ((current = current.getSuperclass()) != null);
        // 候选方法选举
        if ((candidate = selectCandidates(candidates)) != null) {
            return candidate;
        }
        // private方法选举
        for (Method method : privateMethods) {
            if (voteMethodCandidates(method, name, argsType, candidates)) {
                return method;
            }
        }
        // 候选方法选举
        if ((candidate = selectCandidates(candidates)) != null) {
            return candidate;
        }

        throw new IllegalArgumentException("Can't find " +
                clazz.getName() + "." + name + " with " +
                Arrays.toString(argsType));
    }

    /**
     * 全部方法
     *
     * @param clazz 类
     * @return 全部method
     */
    public static Method[] getMethods(Class<?> clazz) {
        // 添加所有public方法
        Set<Method> methods = new LinkedHashSet<>();
        Class<?> current = clazz;
        do {
            methods.addAll(Arrays.asList(current.getDeclaredMethods()));
        } while ((current = current.getSuperclass()) != null);
        // interface default method
        try {
            Method isDefault = Method.class.getMethod("isDefault");
            for (Method method : clazz.getMethods()) {
                if ((boolean) isDefault.invoke(method)) {
                    methods.add(method);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignore) {
        }

        return methods.toArray(new Method[0]);
    }

    /**
     * 继承的接口
     *
     * @param clazz 类
     * @return 所有实现的接口
     */
    public static Class<?>[] getInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        Class<?> current = clazz; // 当前遍历的类
        do {
            interfaces.addAll(Arrays.asList(current.getInterfaces()));
        } while ((current = current.getSuperclass()) != null);

        return interfaces.toArray(new Class<?>[0]);
    }

    /**
     * 父类
     *
     * @param clazz 类
     * @return 全部父类
     */
    public static Class<?>[] getFathers(Class<?> clazz) {
        List<Class<?>> fathers = new ArrayList<>();
        Class<?> current = clazz;
        while ((current = current.getSuperclass()) != null) {
            fathers.add(current);
        }

        return fathers.toArray(new Class[0]);
    }

    /**
     * 判断两个类是否相
     *
     * @param clazz  类
     * @param others 另一个类
     * @param box    原始类型是否转包装类型
     * @return true 如果相等
     */
    public static boolean isEquals(Class<?> clazz, Class<?> others, boolean box) {
        if (box) {
            clazz = boxing(clazz);
            others = boxing(others);
        }

        return clazz.equals(others);
    }

    /**
     * 判断两个类是否相等或继承
     *
     * @param father 父类
     * @param sub    子类
     * @param box    原始类型是否转包装类型
     * @return true 如果相等或继承
     */
    public static boolean isInherited(Class<?> father, Class<?> sub, boolean box) {
        // 原始类型和null不存在继承关系
        if (father.isPrimitive() && sub == null) {
            return false;
        }
        // 引用类型和null默认是继承关系
        if (!father.isPrimitive() && sub == null) {
            return true;
        }

        if (box) {
            father = boxing(father);
            sub = boxing(sub);
        }

        // 判断父子关系
        return father.isAssignableFrom(sub);
    }

    // 私有方法 ========================================================================================

    /**
     * 选举method，完全符合直接返回，部分符合加入候选
     *
     * @param method              方法
     * @param givenName           方法名
     * @param givenParameterTypes 参数类型
     * @param candidates          候选类表
     * @return true 如果完全匹配
     */
    private static boolean voteMethodCandidates(Method method, String givenName, Class<?>[] givenParameterTypes, List<Method> candidates) {
        return voteCandidates(method.getName(), method.getParameterTypes(), givenName, givenParameterTypes, method, candidates);
    }

    /**
     * 选举method，完全符合直接返回，部分符合加入候选
     *
     * @param constructor         方法
     * @param givenParameterTypes 参数类型
     * @param candidates          候选类表
     * @return true 如果完全匹配
     */
    private static boolean voteConstructorCandidates(Constructor<?> constructor, Class<?>[] givenParameterTypes, List<Constructor<?>> candidates) {
        return voteCandidates(null, constructor.getParameterTypes(), null, givenParameterTypes, constructor, candidates);
    }

    /**
     * @param methodName           方法名
     * @param methodParameterTypes 参数类型
     * @param givenName            方法名
     * @param givenParameterTypes  参数类型
     * @param method               方法
     * @param candidates           候选列表
     * @param <T>                  method或构造函数
     * @return true 如果完全匹配
     */
    private static <T> boolean voteCandidates(String methodName, Class<?>[] methodParameterTypes,
                                              String givenName, Class<?>[] givenParameterTypes,
                                              T method, List<T> candidates) {
        if (method instanceof Constructor || methodName.equals(givenName)) {
            if (MethodResolver.isEqualsArgTypes(methodParameterTypes, givenParameterTypes, true)) {
                return true;
            }
            // 名字相同但不精确,加入候选列表
            if (MethodResolver.isInheritedArgTypes(methodParameterTypes, givenParameterTypes, true)) {
                candidates.add(method);
            }
        }

        return false;
    }

    /**
     * 取候选，要求唯一
     *
     * @param candidates 候选列表
     * @param <T>        method或构造函数
     * @return 候选 or null
     */
    private static <T> T selectCandidates(List<T> candidates) {
        if (candidates.size() == 1) {
            return candidates.get(0);
        } else if (candidates.size() > 1) {
            throw new IllegalArgumentException("Too many implements:" + candidates);
        } else {
            return null;
        }
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_MAP = new HashMap<>();

    static {
        PRIMITIVE_MAP.put(void.class, Void.class);

        PRIMITIVE_MAP.put(int.class, Integer.class);
        PRIMITIVE_MAP.put(short.class, Short.class);
        PRIMITIVE_MAP.put(byte.class, Byte.class);
        PRIMITIVE_MAP.put(long.class, Long.class);
        PRIMITIVE_MAP.put(float.class, Float.class);
        PRIMITIVE_MAP.put(double.class, Double.class);
        PRIMITIVE_MAP.put(char.class, Character.class);
        PRIMITIVE_MAP.put(boolean.class, Boolean.class);
    }

    /**
     * 原始类型和包装类型互转
     *
     * @param clazz 类
     * @return 如果原始类型返回对应封装类, 否则返回原类型
     */
    private static Class<?> boxing(Class<?> clazz) {
        Class<?> mapClass = PRIMITIVE_MAP.get(clazz);
        return mapClass != null ? mapClass : clazz;
    }
}
