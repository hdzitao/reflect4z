package hdzitao.reflect4z.list;

import hdzitao.reflect4z.InheritedElement;
import hdzitao.reflect4z.ZField;
import lombok.Getter;

import java.lang.reflect.Field;

/**
 * ZField属性集合
 */
public class ZFieldList extends ReflectList<Field, ZField> implements InheritedElement {
    @Getter
    private Class<?> inheritedType;

    public ZFieldList(Field[] java) {
        super(java);
    }

    @Override
    public ZFieldList withInheritedType(Class<?> inheritedType) {
        this.inheritedType = inheritedType;
        return this;
    }

    @Override
    protected ZField warpElement(Field field) {
        return ZField.forField(field).withInheritedType(this.inheritedType);
    }
}
