package hdzitao.reflect4z.generic;

import hdzitao.reflect4z.ReflectElement;
import hdzitao.reflect4z.list.ZGenericList;

import java.lang.reflect.WildcardType;

/**
 * WildcardType泛型通配符表达式封装类
 */
public class ZWildcardType extends ReflectElement<WildcardType> {
    private ZWildcardType(WildcardType java) {
        super(java);
    }

    /**
     * 创建ZWildcardType
     *
     * @param type WildcardType
     * @return ZWildcardType
     */
    public static ZWildcardType forWildcardType(WildcardType type) {
        return new ZWildcardType(type);
    }

    /**
     * 获得泛型表达式下边界
     *
     * @return 泛型表达式下边界
     */
    public ZGenericList getLowerBounds() {
        return new ZGenericList(this.java.getLowerBounds());
    }

    /**
     * 获取泛型表达式上边界
     *
     * @return 泛型表达式上边界
     */
    public ZGenericList getUpperBounds() {
        return new ZGenericList(this.java.getUpperBounds());
    }
}
