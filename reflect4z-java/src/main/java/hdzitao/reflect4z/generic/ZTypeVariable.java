package hdzitao.reflect4z.generic;

import hdzitao.reflect4z.ReflectElement;
import hdzitao.reflect4z.ZType;
import hdzitao.reflect4z.list.ZGenericList;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;

/**
 * TypeVariable泛型变量封装类
 */
public class ZTypeVariable<D extends GenericDeclaration> extends ReflectElement<TypeVariable<D>> implements ZType<TypeVariable<D>> {
    private ZTypeVariable(TypeVariable<D> java) {
        super(java);
    }

    /**
     * 创建ZTypeVariable
     *
     * @param type TypeVariable
     * @param <D>  GenericDeclaration
     * @return ZTypeVariable
     */
    public static <D extends GenericDeclaration> ZTypeVariable<D> forTypeVariable(TypeVariable<D> type) {
        return new ZTypeVariable<>(type);
    }

    /**
     * 获取泛型上边界
     *
     * @return 泛型上边界
     */
    public ZGenericList getBounds() {
        return new ZGenericList(this.java.getBounds());
    }

    /**
     * 获取声明该类型变量实体(即获得类、方法或构造器名)
     *
     * @return 声明该类型变量实体
     */
    public D getGenericDeclaration() {
        return this.java.getGenericDeclaration();
    }

    /**
     * 获得名称
     *
     * @return 名字
     */
    public String getName() {
        return this.java.getName();
    }
}
