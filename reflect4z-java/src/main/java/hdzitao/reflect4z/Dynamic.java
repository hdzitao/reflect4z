package hdzitao.reflect4z;

/**
 * 可以动态获取值和执行方法的元素————Class(静态field和method)和Object
 */
public interface Dynamic {
    /**
     * 获取field值
     *
     * @param name field名字
     */
    <R> R attr(String name);

    /**
     * 设置field值
     *
     * @param name  field名字
     * @param value field值
     */
    void attr(String name, Object value);

    /**
     * 动态调用方法
     *
     * @param method 方法名
     * @param args   方法参数
     * @param <R>    返回值类型
     * @return method执行返回值
     */
    <R> R send(String method, Object... args);
}
