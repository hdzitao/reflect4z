package hdzitao.reflect4z.proxy;

import hdzitao.reflect4z.ZObject;
import hdzitao.reflect4z.reflect.ClassResolver;
import hdzitao.reflect4z.reflect.MethodResolver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用 java.lang.reflect.Proxy 实现方法重新
 * <p>
 * 因为 Proxy 针对的是接口,所以只能重写接口方法,生成接口实例
 *
 * @author taojinhou
 * @since 0.1.0
 */
public class JavaZObject<O> extends ZProxy<O> {
    private final Class<?>[] newInterfaces;
    private final ClassLoader loader;


    private JavaZObject(O java) {
        super(java);

        Class<?> clazz = this.java.getClass();
        this.loader = clazz.getClassLoader();
        this.newInterfaces = ClassResolver.getInterfaces(clazz);
    }

    /**
     * 创建JavaZObject
     *
     * @param object object
     * @param <O>    object类型
     * @return JavaZObject
     */
    public static <O> JavaZObject<O> forObject(O object) {
        return new JavaZObject<>(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R method(final RewrittenMethod rewrittenMethod) {
        return (R) Proxy.newProxyInstance(this.loader, this.newInterfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return rewrittenMethod.call(proxy, JavaZObject.this.java, method, args);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R method(final Method theMethod, final RewrittenMethod rewrittenMethod) {
        return (R) Proxy.newProxyInstance(this.loader, this.newInterfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return MethodResolver.isOverridden(method, theMethod)   // m是父类方法（接口），method是子类方法（原对象）
                        ? rewrittenMethod.call(proxy, JavaZObject.this.java, method, args)
                        : method.invoke(java, args);
            }
        });
    }
}
