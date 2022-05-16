package hdzitao.reflect4z.parameter;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

/**
 * jdk7版本向上兼容jdk8 Parameter的部分功能
 */
public interface Parameter extends AnnotatedElement {

    /**
     * 参数索引
     *
     * @return 参数索引
     */
    int getIndex();


    /**
     * Returns true if the parameter has a name according to the class
     * file; returns false otherwise. Whether a parameter has a name
     * is determined by the {@literal MethodParameters} attribute of
     * the method which declares the parameter.
     *
     * @return true if and only if the parameter has a name according
     * to the class file.
     */
    boolean isNamePresent();

    /**
     * Returns the name of the parameter.  If the parameter's name is
     * {@linkplain #isNamePresent() present}, then this method returns
     * the name provided by the class file. Otherwise, this method
     * synthesizes a name of the form argN, where N is the index of
     * the parameter in the descriptor of the method which declares
     * the parameter.
     *
     * @return The name of the parameter, either provided by the class
     * file or synthesized if the class file does not provide
     * a name.
     */
    String getName();

    /**
     * Returns a {@code Type} object that identifies the parameterized
     * type for the parameter represented by this {@code Parameter}
     * object.
     *
     * @return a {@code Type} object identifying the parameterized
     * type of the parameter represented by this object
     */
    Type getParameterizedType();

    /**
     * Returns a {@code Class} object that identifies the
     * declared type for the parameter represented by this
     * {@code Parameter} object.
     *
     * @return a {@code Class} object identifying the declared
     * type of the parameter represented by this object
     */
    Class<?> getType();

    /**
     * Returns {@code true} if this parameter is implicitly declared
     * in source code; returns {@code false} otherwise.
     *
     * @return true if and only if this parameter is implicitly
     * declared as defined by <cite>The Java&trade; Language
     * Specification</cite>.
     */
    boolean isImplicit();

    /**
     * Returns {@code true} if this parameter is neither implicitly
     * nor explicitly declared in source code; returns {@code false}
     * otherwise.
     *
     * @return true if and only if this parameter is a synthetic
     * construct as defined by
     * <cite>The Java&trade; Language Specification</cite>.
     * @jls 13.1 The Form of a Binary
     */
    boolean isSynthetic();

    /**
     * Returns {@code true} if this parameter represents a variable
     * argument list; returns {@code false} otherwise.
     *
     * @return {@code true} if an only if this parameter represents a
     * variable argument list.
     */
    boolean isVarArgs();

    int getModifiers();
}
