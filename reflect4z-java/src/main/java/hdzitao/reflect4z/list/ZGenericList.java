package hdzitao.reflect4z.list;

import hdzitao.reflect4z.ZGeneric;

import java.lang.reflect.Type;

/**
 * ZGeneric泛型集合
 */
public class ZGenericList extends ReflectList<Type, ZGeneric> {
    public ZGenericList(Type[] java) {
        super(java);
    }

    @Override
    protected ZGeneric warpElement(Type type) {
        return ZGeneric.forType(type);
    }
}
