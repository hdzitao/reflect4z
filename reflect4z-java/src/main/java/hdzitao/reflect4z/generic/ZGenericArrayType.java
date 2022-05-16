package hdzitao.reflect4z.generic;

import hdzitao.reflect4z.ReflectElement;
import hdzitao.reflect4z.ZGeneric;
import hdzitao.reflect4z.ZType;

import java.lang.reflect.GenericArrayType;

/**
 * GenericArrayType泛型数组封装类
 */
public class ZGenericArrayType extends ReflectElement<GenericArrayType> implements ZType<GenericArrayType> {
    private ZGenericArrayType(GenericArrayType java) {
        super(java);
    }

    /**
     * 创建ZGenericArrayType
     *
     * @param type GenericArrayType
     * @return ZGenericArrayType
     */
    public static ZGenericArrayType forGenericArrayType(GenericArrayType type) {
        return new ZGenericArrayType(type);
    }

    /**
     * 获取泛型数组的泛型信息
     *
     * @return ZGeneric泛型信息
     */
    public ZGeneric getGenericComponentType() {
        return ZGeneric.forType(this.java.getGenericComponentType());
    }
}
