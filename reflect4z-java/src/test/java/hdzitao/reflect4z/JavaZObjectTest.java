package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZAnnotationList;
import hdzitao.reflect4z.list.ZParameterList;
import hdzitao.reflect4z.proxy.JavaZObject;
import hdzitao.reflect4z.proxy.RewrittenMethod;
import hdzitao.reflect4z.reflect.MethodResolver;
import hdzitao.reflect4z.test.annotation.ClassAge;
import hdzitao.reflect4z.test.annotation.ClassName;
import hdzitao.reflect4z.test.annotation.ParamAge;
import hdzitao.reflect4z.test.annotation.ParamName;
import hdzitao.reflect4z.test.bean.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class JavaZObjectTest {
    private FPerson person;
    private JavaZObject<FPerson> zObject;

    @Before
    public void before() {
        person = new FPerson();
        // 代理
        zObject = JavaZObject.forObject(person);
    }

    /**
     * 基础功能
     */
    @Test
    public void base() {
        // 生成一个默认的代理
        Object proxy = zObject.method(new RewrittenMethod() {
            @Override
            public Object call(Object p, Object java, Method method, Object... args) throws Throwable {
                return method.invoke(java, args);
            }
        });

        Set<Class<?>> interfaces = new HashSet<>(Arrays.asList(proxy.getClass().getInterfaces()));
        assertTrue(interfaces.containsAll(Arrays.asList(HasAge.class, HasName.class, Show.class)));
        assertTrue(!interfaces.contains(SetAge.class) && proxy instanceof SetAge);
        // 校验原值
        assertEquals("taojinhou", person.getName());
        assertEquals(20, person.getAge());
        // 生成的proxy代理了 person
        assertEquals("taojinhou", ((HasName) proxy).getName());
        assertEquals(20, ((HasAge) proxy).getAge());
        // 原值改变，代理类同时改变
        person.setAge(21);
        assertEquals(21, ((HasAge) proxy).getAge());
    }

    /**
     * 通过ZObject反射属性
     */
    @Test
    public void reflectField() {
        // 反射字段
        int age = zObject.attr("age");
        assertEquals(20, age);
        zObject.attr("age", 21);
        assertEquals(21, person.getAge());

    }

    /**
     * 通过ZObject反射方法
     */
    @Test
    public void reflectMethod() {
        // 反射方法
        final ZMethod show = zObject.getZClass().getZMethod("show", String.class, int.class);
        // 新生成一个重写了show方法的实例
        final Show newShow_1st = zObject.method(show.java(), new RewrittenMethod() {
            @Override
            public Object call(Object proxy, Object java, Method method, Object... args) throws Throwable {
                assertSame(person, java);
                assertTrue(MethodResolver.isOverridden(method, show.java()));

                String school = MethodResolver.getArgs(args, 0);
                Integer gradle = MethodResolver.getArgs(args, 1);
                return "newShow/school:" + school + "/gradle:" + gradle;
            }
        });
        assertEquals("newShow/school:春晖/gradle:3", newShow_1st.show("春晖", 3));
        assertEquals("name:taojinhou,age:20,school:春晖,grade:3", person.show("春晖", 3));
        // 新对象的其他方法转发给原对象
        assertEquals(20, person.getAge());
        ((HasAge) newShow_1st).setAge(21);
        assertEquals(21, person.getAge());

        // 新生成一个用InvocationHandler代理所有方法的实例
        JavaZObject<Show> showZObject = JavaZObject.forObject(newShow_1st);
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
        ((HasAge) newShow_2nd).setAge(20);
        assertEquals(20, person.getAge());
    }

    /**
     * 测试获取 ZMethod
     */
    @Test
    public void method() throws NoSuchMethodException {
        ZMethod setAge = ZClass.forClass(SetAge.class).getZMethod("setAge", int.class);
        Method setAgeOriginal = SetAge.class.getDeclaredMethod("setAge", int.class);

        assertEquals(setAgeOriginal, setAge.java());

        assertEquals(int.class, setAgeOriginal.getReturnType());
        assertTrue(setAge.getReturnZType().from(int.class));

        assertArrayEquals(new Class[]{int.class}, setAgeOriginal.getParameterTypes());
        assertArrayEquals(setAge.getParameterZTypes().java(), new Class[]{int.class});

        setAge.invoke(this.person, 1);
        int age = this.zObject.getZClass().getZMethod("getAge").invoke(this.person);
        assertEquals(1, age);
    }

    /**
     * 测试私有方法
     */
    @Test
    public void reflectMethodPrivate() {
        // 私有方法
        assertEquals("Secret:1", zObject.send("secret", 1));
        assertEquals("secret", zObject.getZClass().getZMethod("secret", Integer.class).getName());
    }

    /**
     * 测试 强转
     */
    @Test
    public void asInterface() {
        HasWork hasWork = this.zObject.asInterface(HasWork.class);
        assertEquals("has work:1", hasWork.getWork(1));
        assertEquals("can work", hasWork.work());
    }

    /**
     * 测试获取 ZField
     */
    @Test
    public void field() throws NoSuchFieldException {
        ZField gender = this.zObject.getZClass().getZField("gender");
        assertEquals(Man.class.getDeclaredField("gender"), gender.java());
        // 隐藏原始类型
        assertTrue(gender.getZType().from(int.class));
        // 设置
        gender.set(this.person, 21);
        assertEquals(21, (int) gender.get(this.person));
    }

    @Test
    public void primitive() {
        assertEquals(1, (int) this.zObject.send("packageAge", 1));
        assertEquals(1, (int) this.zObject.send("primitiveAge", 1));
    }

    /**
     * 测试获取原始annotation
     */
    @Test
    public void annotation() {
        ZClass zclass = this.zObject.getZClass();

        ClassName className = zclass.getAnnotation(ClassName.class);
        assertNotNull(className);
        assertEquals("taojinhou", className.name());

        ClassAge classAge = zclass.getAnnotation(ClassAge.class);
        assertNotNull(classAge);
        assertEquals(20, classAge.age());
    }

    /**
     * 测试 ZAnnotation
     */
    @Test
    public void annotations() {
        ZClass zclass = this.zObject.getZClass();

        ZAnnotationList annotations = zclass.getZAnnotations();

        assertFalse(annotations.isEmpty());

        ZAnnotation search = zclass.getZAnnotation(ClassName.class);
        assertTrue(search.is(ClassName.class));
        ClassName className = search.as(ClassName.class);
        assertNotNull(className);
        assertEquals("taojinhou", className.name());

        search = zclass.getZAnnotation(ClassAge.class);
        assertTrue(search.is(ClassAge.class));
        ClassAge classAge = search.as(ClassAge.class);
        assertNotNull(classAge);
        assertEquals(20, classAge.age());
    }

    /**
     * 测试 ZParameter
     */
    @Test
    public void parameter() {
        ZClass zclass = this.zObject.getZClass();
        ZMethod paramAnnotation = zclass.getZMethod("paramAnnotation", String.class, int.class);
        ZParameterList parameter = paramAnnotation.getZParameters();
        assertFalse(parameter.isEmpty());

        ZParameter nameParam = parameter.get(0);

        assertEquals(0, nameParam.getIndex());
        assertEquals("name", nameParam.getName());

        ParamName nameParamAnnotation = nameParam.getAnnotation(ParamName.class);
        assertNotNull(nameParamAnnotation);
        assertEquals("taojinhou", nameParamAnnotation.name());
        assertTrue(nameParam.getZType().from(String.class));

        ZParameter ageParam = parameter.get(1);

        assertEquals(1, ageParam.getIndex());
        assertEquals("age", ageParam.getName());

        ParamAge ageParamAnnotation = ageParam.getAnnotation(ParamAge.class);
        assertNotNull(ageParamAnnotation);
        assertEquals(20, ageParamAnnotation.age());
        assertTrue(ageParam.getZType().from(int.class));

    }

    @Test
    public void packageModifier() {
//        ZMethod testPackage = this.zObject.getZClass().getMethod("testPackage");
//        assertTrue(testPackage.isPackage());
    }
}