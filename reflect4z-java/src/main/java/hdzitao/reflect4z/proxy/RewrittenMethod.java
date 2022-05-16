package hdzitao.reflect4z.proxy;

import java.lang.reflect.Method;

/**
 * 重写方法
 *
 * @author taojinhou
 * @since 0.1.0
 */
@FunctionalInterface
public interface RewrittenMethod {
    /**
     * @param proxy  新生成的代理对象
     * @param java   原对象
     * @param method 原方法
     * @param args   参数
     * @return
     */
    Object call(Object proxy, Object java, Method method, Object... args) throws Throwable;
}
