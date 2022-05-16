package hdzitao.reflect4z;


import hdzitao.reflect4z.reflect.ClassResolver;
import hdzitao.reflect4z.reflect.ObjectResolver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * object封装类
 */
public class ZObject<O> extends ReflectElement<O> implements Dynamic {
    protected ZObject(O java) {
        super(java);
    }

    /**
     * 创建ZObject
     *
     * @param object 实例
     * @param <O>    实例类型
     * @return ZObject封装的object
     */
    public static <O> ZObject<O> forObject(O object) {
        return new ZObject<>(object);
    }

    /**
     * 获取ZClass
     *
     * @return object的类型
     */
    public ZClass getZClass() {
        return ZClass.forClass(this.java.getClass());
    }

    /**
     * 如果原始对象和to接口有相同签名的方法但没有继承关系，可以用此方法生成一个to接口的实例，把方法调用转发给原始对象
     *
     * @param to
     * @param <I>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <I> I asInterface(Class<I> to) {
        return (I) Proxy.newProxyInstance(to.getClassLoader(), new Class[]{to}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return ClassResolver.getMethod(ZObject.this.java.getClass(), method.getName(), method.getParameterTypes())
                        .invoke(ZObject.this.java, args);
            }
        });
    }

    @Override
    public <R> R attr(String name) {
        return ObjectResolver.get(this.java, name);
    }

    @Override
    public void attr(String name, Object value) {
        ObjectResolver.set(this.java, name, value);
    }

    @Override
    public <R> R send(String method, Object... args) {
        return ObjectResolver.invoke(this.java, method, args);
    }
}