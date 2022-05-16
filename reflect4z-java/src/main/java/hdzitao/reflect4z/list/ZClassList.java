package hdzitao.reflect4z.list;

import hdzitao.reflect4z.ZClass;

/**
 * ZClass类集合
 */
public class ZClassList extends ReflectList<Class<?>, ZClass> {
    public ZClassList(Class<?>[] java) {
        super(java);
    }

    @Override
    protected ZClass warpElement(Class<?> clazz) {
        return ZClass.forClass(clazz);
    }
}
