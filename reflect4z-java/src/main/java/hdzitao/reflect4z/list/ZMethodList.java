package hdzitao.reflect4z.list;

import hdzitao.reflect4z.InheritedElement;
import hdzitao.reflect4z.ZMethod;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * ZMethod方法集合
 */
public class ZMethodList extends ReflectList<Method, ZMethod> implements InheritedElement {
    @Getter
    private Class<?> inheritedType;

    public ZMethodList(Method[] java) {
        super(java);
    }

    @Override
    public ZMethodList withInheritedType(Class<?> inheritedType) {
        this.inheritedType = inheritedType;
        return this;
    }

    @Override
    protected ZMethod warpElement(Method method) {
        return ZMethod.forMethod(method).withInheritedType(this.inheritedType);
    }
}
