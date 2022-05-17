package hdzitao.reflect4z;

import hdzitao.reflect4z.proxy.CGLibZObject;
import hdzitao.reflect4z.proxy.RewrittenMethod;
import hdzitao.reflect4z.reflect.MethodResolver;
import hdzitao.reflect4z.test.bean.HasAge;
import hdzitao.reflect4z.test.bean.HasName;
import hdzitao.reflect4z.test.bean.Person;
import hdzitao.reflect4z.test.bean.Show;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CGLibZObjectTest {
    private Person person;
    private CGLibZObject<Person> zObject;

    @Before
    public void before() {
        person = new Person();
        // 代理
        zObject = CGLibZObject.forObject(person);
    }

    /**
     * 基础功能
     */
    @Test
    public void base() {
        Object proxy = zObject.method(new RewrittenMethod() {
            @Override
            public Object call(Object p, Object java, Method method, Object... args) throws Throwable {
                return method.invoke(java, args);
            }
        });

        // 子类检验
        assertTrue(proxy instanceof Person);
        // ZObject实现了ZObject接口
        Set<Class<?>> interfaces1 = new LinkedHashSet<>();

        Class<?> current = proxy.getClass(); // 当前遍历的类
        do {
            interfaces1.addAll(Arrays.asList(current.getInterfaces()));
        } while ((current = current.getSuperclass()) != null);

        Set<Class<?>> interfaces = new HashSet<>(Arrays.asList(interfaces1.toArray(new Class<?>[0])));
        assertTrue(interfaces.containsAll(Arrays.asList(HasAge.class, HasName.class, Show.class)));
        // 校验原值
        assertEquals("taojinhou", person.getName());
        assertEquals(20, person.getAge());
        // 生成的ZObject代理了 person
        assertEquals("taojinhou", ((HasName) proxy).getName());
        assertEquals(20, ((HasAge) proxy).getAge());
        // 原值改变，代理类同时改变
        person.setAge(21);
        assertEquals(21, ((HasAge) proxy).getAge());
        // 用代理改变原值
        ((HasAge) proxy).setAge(22);
        assertEquals(22, person.getAge());
    }

    /**
     * 通过ZObject反射方法
     */
    @Test
    public void reflectMethod() {
        // 反射方法
        final ZMethod show = zObject.getZClass().getZMethod("show", String.class, int.class);
        assertNotNull(show);
        // 新生成一个重写了show方法的实例
        final Show newShow_1st = zObject.method(show.java(), new RewrittenMethod() {
            @Override
            public Object call(Object proxy, Object java, Method method, Object... args) throws Throwable {
                assertSame(person, java);
                assertTrue(MethodResolver.isOverridden(show.java(), method));

                String school = MethodResolver.getArgs(args, 0);
                Integer gradle = MethodResolver.getArgs(args, 1);

                return "newShow/school:" + school + "/gradle:" + gradle;
            }
        });
        assertEquals("newShow/school:春晖/gradle:3", newShow_1st.show("春晖", 3));
        assertEquals("name:taojinhou,age:20,school:春晖,grade:3", person.show("春晖", 3));
        // 新对象的其他方法转发给原对象
        assertEquals(20, person.getAge());
        ((Person) newShow_1st).setAge(21);
        assertEquals(21, person.getAge());

        // 新生成一个用InvocationHandler代理所有方法的实例
        CGLibZObject<Show> showZObject = CGLibZObject.forObject(newShow_1st);
        Show newShow_2nd = showZObject.method(new RewrittenMethod() {
            @Override
            public Object call(Object proxy, Object java, Method method, Object... args) throws Throwable {
                assertSame(newShow_1st, java);
                if ("show".equals(method.getName())) {
                    return "InvocationHandler:" + method.invoke(java, args);
                } else {
                    return method.invoke(java, args);
                }
            }
        });
        String showResult = newShow_2nd.show("春晖", 3);
        assertEquals("InvocationHandler:" + newShow_1st.show("春晖", 3), showResult);
        // 原来的实例没有重置方法
        assertEquals("name:taojinhou,age:21,school:春晖,grade:3", person.show("春晖", 3));
        // 新对象的其他方法转发给原对象
        assertEquals(21, person.getAge());
        ((Person) newShow_2nd).setAge(20);
        assertEquals(20, person.getAge());
    }
}