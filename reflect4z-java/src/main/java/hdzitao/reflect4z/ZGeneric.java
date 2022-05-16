package hdzitao.reflect4z;

import hdzitao.reflect4z.generic.ZGenericArrayType;
import hdzitao.reflect4z.generic.ZParameterizedType;
import hdzitao.reflect4z.generic.ZTypeVariable;
import hdzitao.reflect4z.generic.ZWildcardType;

import java.lang.reflect.*;

/**
 * 泛型封装类
 * 类型具体有 GenericArrayType/ParameterizedType/WildcardType/Class,
 * 先用ZGeneric统一保存泛型,再调用相应方法转化成具体类型
 */
public class ZGeneric extends ReflectElement<Type> {
    private ZGeneric(Type java) {
        super(java);
    }

    /**
     * 创建ZGeneric
     *
     * @param type Generic
     * @return ZGeneric
     */
    public static ZGeneric forType(Type type) {
        return new ZGeneric(type);
    }

    /**
     * 转化成ZGenericArrayType
     *
     * @return ZGenericArrayType
     */
    public ZGenericArrayType genericArray() {
        return ZGenericArrayType.forGenericArrayType((GenericArrayType) this.java);
    }

    /**
     * 转化成ParameterizedType
     *
     * @return ZParameterizedType
     */
    public ZParameterizedType parameterized() {
        return ZParameterizedType.forParameterizedType((ParameterizedType) this.java);
    }

    /**
     * 转化成WildcardType
     *
     * @return ZWildcardType
     */
    public ZWildcardType wildcard() {
        return ZWildcardType.forWildcardType((WildcardType) this.java);
    }

    /**
     * 转化成TypeVariable
     *
     * @param <D> GenericDeclaration
     * @return ZTypeVariable
     */
    @SuppressWarnings("unchecked")
    public <D extends GenericDeclaration> ZTypeVariable<D> typeVariable() {
        return ZTypeVariable.forTypeVariable((TypeVariable<D>) this.java);
    }

    /**
     * 转化成Class
     *
     * @return ZClass
     */
    public ZClass clazz() {
        return ZClass.forClass((Class<?>) this.java);
    }
}
