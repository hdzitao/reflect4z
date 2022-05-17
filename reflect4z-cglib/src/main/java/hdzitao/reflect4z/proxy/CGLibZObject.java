package hdzitao.reflect4z.proxy;

import hdzitao.reflect4z.ZObject;
import hdzitao.reflect4z.reflect.MethodResolver;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 用 cglib 实现方法重新
 * <p>
 * 因为 cglib 基于子类实现代理,所以不能用于final class
 */
public class CGLibZObject<O> extends ZObject<O> implements ZProxy {
    private CGLibZObject(O java) {
        super(java);
    }

    /**
     * CGLibZObject
     *
     * @param object object
     * @param <O>    object类型
     * @return CGLibZObject
     */
    public static <O> CGLibZObject<O> forObject(O object) {
        return new CGLibZObject<>(object);
    }

    @Override
    public <R> R method(final Method theMethod, final RewrittenMethod rewrittenMethod) {
        return enhancer(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy p) throws Throwable {
                return MethodResolver.isOverridden(theMethod, method) // method 是父类方法(原对象), m是子类方法(cglib生成的)
                        ? rewrittenMethod.call(obj, CGLibZObject.this.java, method, args)
                        : method.invoke(java, args);
            }
        });
    }

    @Override
    public <R> R method(final RewrittenMethod rewrittenMethod) {
        return enhancer(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy p) throws Throwable {
                return rewrittenMethod.call(obj, CGLibZObject.this.java, method, args);
            }
        });
    }

    /**
     * 用cglib生成代理类
     *
     * @param interceptor 代理逻辑
     * @param <R>         代理类型
     * @return 一个新的代理实例
     */
    @SuppressWarnings("unchecked")
    private <R> R enhancer(MethodInterceptor interceptor) {
        if (this.java instanceof Factory) { // 二次增强
            Factory factory = (Factory) this.java;
            return (R) factory.newInstance(interceptor);
        } else {
            Enhancer enhancer = new Enhancer();
            Class<?> clazz = this.java.getClass();
            enhancer.setSuperclass(clazz);
            enhancer.setClassLoader(clazz.getClassLoader());
            enhancer.setCallback(interceptor);
            return (R) enhancer.create();
        }
    }
}

