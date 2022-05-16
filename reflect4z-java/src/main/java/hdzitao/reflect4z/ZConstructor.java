package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZClassList;
import hdzitao.reflect4z.list.ZGenericList;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * 构造函数封装类
 */
public class ZConstructor extends ZExecutable<Constructor<?>> {
    private ZConstructor(Constructor<?> constructor) {
        super(constructor);
        this.java.setAccessible(true);
    }

    /**
     * 构造函数 new 一个实例
     *
     * @param args 构造函数参数
     * @param <R>  new的类型
     * @return 一个新new的实例
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <R> R newInstance(Object... args) {
        return (R) this.java.newInstance(args);
    }

    /**
     * 创建ZConstructor
     *
     * @param constructor Constructor
     * @return ZConstructor
     */
    public static ZConstructor forConstructor(Constructor<?> constructor) {
        return new ZConstructor(constructor);
    }

    @Override
    public ZGenericList getGenericParameterZTypes() {
        return new ZGenericList(this.java.getGenericParameterTypes());
    }

    @Override
    public <R> R invoke(Object obj, Object... args) {
        return newInstance(args);
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
}
