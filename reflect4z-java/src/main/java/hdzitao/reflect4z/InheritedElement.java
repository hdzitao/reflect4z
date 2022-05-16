package hdzitao.reflect4z;

/**
 * 可以被子类继承的元素
 * <p>
 * 子类继承field和method的时候可以指明具体的泛型，
 * 所以判断这些泛型时需要子类信息
 */
public interface InheritedElement {
    Class<?> getInheritedType();

    InheritedElement withInheritedType(Class<?> inheritedType);
}
