package hdzitao.reflect4z;

import java.util.Objects;

/**
 * 封装一个java原版反射对象
 *
 * @see Reflect
 */
public abstract class ReflectElement<T> implements Reflect<T> {
    /**
     * 原版反射对象
     */
    protected final T java;

    public ReflectElement(T java) {
        this.java = Objects.requireNonNull(java, "Reflect element must not be null.");
    }


    @Override
    public T java() {
        return this.java;
    }

    /**
     * 判断来源
     *
     * @param other 另一个ReflectElement
     * @return true 如果java和other相等
     */
    public boolean from(T other) {
        return Objects.equals(this.java, other);
    }

    /**
     * java相等，则ReflectElement相等
     *
     * @param o 计较对象非
     * @return true 如果两个ReflectElement的java相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReflectElement<?> that = (ReflectElement<?>) o;
        return Objects.equals(java, that.java);
    }

    /**
     * @return java#hashcode
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        return this.java.hashCode();
    }

    /**
     * toString
     *
     * @return java#toString
     */
    @Override
    public String toString() {
        return "Reflect4z[" + java + "]";
    }
}
