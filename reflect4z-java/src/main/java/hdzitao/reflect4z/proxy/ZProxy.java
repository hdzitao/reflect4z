package hdzitao.reflect4z.proxy;

import hdzitao.reflect4z.ZMethod;
import hdzitao.reflect4z.ZObject;

import java.lang.reflect.Method;

/**
 * java实例代理类,实现运行期重写方法
 *
 * @author taojinhou
 * @since 0.1.0
 */
public abstract class ZProxy<O> extends ZObject<O> {
    protected ZProxy(O java) {
        super(java);
    }

    /**
     * 代理所有方法
     *
     * @param rewrittenMethod 重写方法逻辑
     * @return 一个新的代理对象
     */
    public abstract <R> R method(RewrittenMethod rewrittenMethod);

    /**
     * 重写method方法
     * 返回重写了方法的实例，原对象不变
     *
     * @param theMethod       相应方法
     * @param rewrittenMethod 重写方法逻辑
     * @return 一个新的代理对象
     */
    public abstract <R> R method(Method theMethod, RewrittenMethod rewrittenMethod);

    /**
     * 重写method方法
     * 返回重写了方法的实例，原对象不变
     *
     * @param theMethod       相应方法
     * @param rewrittenMethod 重写方法逻辑
     * @return 一个新的代理对象
     */
    public final <R> R method(ZMethod theMethod, RewrittenMethod rewrittenMethod) {
        return method(theMethod.java(), rewrittenMethod);
    }
}
