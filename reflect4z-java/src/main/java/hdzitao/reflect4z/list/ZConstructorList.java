package hdzitao.reflect4z.list;

import hdzitao.reflect4z.ZConstructor;

import java.lang.reflect.Constructor;

/**
 * ZConstructor构造函数集合
 */
public class ZConstructorList extends ReflectList<Constructor<?>, ZConstructor> {
    public ZConstructorList(Constructor<?>[] java) {
        super(java);
    }

    @Override
    protected ZConstructor warpElement(Constructor<?> constructor) {
        return ZConstructor.forConstructor(constructor);
    }
}
