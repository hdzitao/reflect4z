package hdzitao.reflect4z.generic;

import hdzitao.reflect4z.ReflectElement;
import hdzitao.reflect4z.ZGeneric;
import hdzitao.reflect4z.ZType;
import hdzitao.reflect4z.list.ZGenericList;

import java.lang.reflect.ParameterizedType;

/**
 * ParameterizedType泛型封装类
 */
public class ZParameterizedType extends ReflectElement<ParameterizedType> implements ZType<ParameterizedType> {
    private ZParameterizedType(ParameterizedType java) {
        super(java);
    }

    /**
     * 创建 ZParameterizedType
     *
     * @param type ParameterizedType
     * @return ZParameterizedType
     */
    public static ZParameterizedType forParameterizedType(ParameterizedType type) {
        return new ZParameterizedType(type);
    }

    /**
     * 获取泛型参数列表,如:
     * Map<K, V> => [K, V]
     *
     * @return 泛型参数列表
     */
    public ZGenericList getActualTypeArguments() {
        return new ZGenericList(this.java.getActualTypeArguments());
    }

    /**
     * 获取泛型参数的所有者的类型,如:
     * Map.Entry<K, V> => Map
     *
     * @return 泛型参数所有者的类型
     */
    public ZGeneric getOwnerType() {
        return ZGeneric.forType(this.java.getOwnerType());
    }

    /**
     * 获取使用泛型的类型, 如:
     * Map<K, V> => Map
     *
     * @return 使用泛型的类型
     */
    public ZGeneric getRawType() {
        return ZGeneric.forType(this.java.getRawType());
    }
}
