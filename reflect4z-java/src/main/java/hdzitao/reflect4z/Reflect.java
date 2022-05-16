package hdzitao.reflect4z;

/**
 * Reflect包含一个T类型的对象。
 *
 * @param <T>
 */
public interface Reflect<T> {
    /**
     * 返回原对象
     *
     * @return java原版反射对象
     */
    T java();
}
