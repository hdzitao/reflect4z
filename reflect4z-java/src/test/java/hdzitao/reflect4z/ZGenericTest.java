/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hdzitao.reflect4z;


import hdzitao.reflect4z.generic.ZGenericArrayType;
import hdzitao.reflect4z.generic.ZParameterizedType;
import hdzitao.reflect4z.generic.ZWildcardType;
import hdzitao.reflect4z.list.ZGenericList;
import hdzitao.reflect4z.reflect.GenericResolver;
import hdzitao.reflect4z.test.generic.Calculator;
import hdzitao.reflect4z.test.generic.Level0Mapper;
import hdzitao.reflect4z.test.generic.Level1Mapper;
import hdzitao.reflect4z.test.generic.Level2Mapper;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class ZGenericTest {
    @Test
    public void testReturn_Lv0SimpleClass() throws Exception {
        ZClass clazz = ZClass.forClass(Level0Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelect");
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(Double.class, result.clazz().java());
    }

    @Test
    public void testReturn_SimpleVoid() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectVoid", Integer.class);
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(void.class, result.clazz().java());
    }

    @Test
    public void testReturn_SimplePrimitive() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectPrimitive", int.class);
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(double.class, result.clazz().java());
    }

    @Test
    public void testReturn_SimpleClass() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelect");
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(Double.class, result.clazz().java());
    }

    @Test
    public void testReturn_SimpleList() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectList");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(List.class, paramType.getRawType().clazz().java());
        assertEquals(1, paramType.getActualTypeArguments().size());
        assertEquals(Double.class, paramType.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testReturn_SimpleMap() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectMap");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(Map.class, paramType.getRawType().clazz().java());
        assertEquals(2, paramType.getActualTypeArguments().size());
        assertEquals(Integer.class, paramType.getActualTypeArguments().get(0).clazz().java());
        assertEquals(Double.class, paramType.getActualTypeArguments().get(1).clazz().java());
    }

    @Test
    public void testReturn_SimpleWildcard() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectWildcard");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(List.class, paramType.getRawType().clazz().java());
        assertEquals(1, paramType.getActualTypeArguments().size());
        ZWildcardType wildcard = paramType.getActualTypeArguments().get(0).wildcard();
        assertEquals(String.class, wildcard.getUpperBounds().get(0).clazz().java());
    }

    @Test
    public void testReturn_SimpleArray() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectArray");
        ZGeneric result = method.getGenericReturnZType();
        ZClass resultClass = result.clazz();
        assertTrue(resultClass.isArray());
        assertEquals(String.class, resultClass.getComponentZType().java());
    }

    @Test
    public void testReturn_SimpleArrayOfArray() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectArrayOfArray");
        ZGeneric result = method.getGenericReturnZType();
        ZClass resultClass = result.clazz();
        assertTrue(resultClass.isArray());
        assertTrue(resultClass.getComponentZType().isArray());
        assertEquals(String.class, resultClass.getComponentZType().getComponentZType().java());
    }

    @Test
    public void testReturn_SimpleTypeVar() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectTypeVar");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(Calculator.class, paramType.getRawType().clazz().java());
        assertEquals(1, paramType.getActualTypeArguments().size());
        assertNotNull(paramType.getActualTypeArguments().get(0).wildcard());
    }

    @Test
    public void testReturn_Lv1Class() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("select", Object.class);
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(String.class, result.clazz().java());
    }

    @Test
    public void testReturn_Lv2CustomClass() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectCalculator", Calculator.class);
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(Calculator.class, paramType.getRawType().clazz().java());
        assertEquals(1, paramType.getActualTypeArguments().size());
        assertEquals(String.class, paramType.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testReturn_Lv2CustomClassList() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectCalculatorList");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramTypeOuter = result.parameterized();
        assertEquals(List.class, paramTypeOuter.getRawType().clazz().java());
        assertEquals(1, paramTypeOuter.getActualTypeArguments().size());
        ZParameterizedType paramTypeInner = paramTypeOuter.getActualTypeArguments().get(0).parameterized();
        assertEquals(Calculator.class, paramTypeInner.getRawType().clazz().java());
        assertEquals(Date.class, paramTypeInner.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testReturn_Lv0InnerClass() throws Exception {
        ZClass clazz = ZClass.forClass(Level0Mapper.Level0InnerMapper.class);
        ZMethod method = clazz.getZMethod("select", Object.class);
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(Float.class, result.clazz().java());
    }

    @Test
    public void testReturn_Lv2Class() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("select", Object.class);
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(String.class, result.clazz().java());
    }

    @Test
    public void testReturn_Lv1List() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("selectList", Object.class, Object.class);
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType type = result.parameterized();
        assertEquals(List.class, type.getRawType().clazz().java());
        assertEquals(1, type.getActualTypeArguments().size());
        assertEquals(String.class, type.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testReturn_Lv1Array() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("selectArray", List[].class);
        ZGeneric result = method.getGenericReturnZType();

        ZClass resultClass = result.clazz();
        assertTrue(resultClass.isArray());
        assertEquals(String.class, resultClass.getComponentZType().java());
    }

    @Test
    public void testReturn_Lv2ArrayOfArray() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectArrayOfArray");
        ZGeneric result = method.getGenericReturnZType();
        ZClass resultClass = result.clazz();
        assertTrue(resultClass.isArray());
        assertTrue(resultClass.getComponentZType().isArray());
        assertEquals(String.class, resultClass.getComponentZType().getComponentZType().java());
    }

    @Test
    public void testReturn_Lv2ArrayOfList() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectArrayOfList");
        ZGeneric result = method.getGenericReturnZType();

        ZGenericArrayType genericArrayType = result.genericArray();
        ZParameterizedType paramType = genericArrayType.getGenericComponentType().parameterized();
        assertEquals(List.class, paramType.getRawType().clazz().java());
        assertEquals(String.class, paramType.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testReturn_Lv2WildcardList() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectWildcardList");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType type = result.parameterized();
        assertEquals(List.class, type.getRawType().java());
        assertEquals(1, type.getActualTypeArguments().size());
        ZWildcardType wildcard = type.getActualTypeArguments().get(0).wildcard();
        assertEquals(0, wildcard.getLowerBounds().size());
        assertEquals(1, wildcard.getUpperBounds().size());
        assertEquals(String.class, wildcard.getUpperBounds().get(0).clazz().java());
    }

    @Test
    public void testReturn_LV2Map() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectMap");
        ZGeneric result = method.getGenericReturnZType();
        ZParameterizedType paramType = result.parameterized();
        assertEquals(Map.class, paramType.getRawType().clazz().java());
        assertEquals(2, paramType.getActualTypeArguments().size());
        assertEquals(String.class, paramType.getActualTypeArguments().get(0).clazz().java());
        assertEquals(Integer.class, paramType.getActualTypeArguments().get(1).clazz().java());
    }

    @Test
    public void testReturn_Subclass() throws Exception {
        ZClass clazz = ZClass.forClass(Calculator.SubCalculator.class);
        ZMethod method = clazz.getZMethod("getId");
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(String.class, result.clazz().java());
    }

    @Test
    public void testParam_Primitive() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectPrimitive", int.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(1, result.size());
        assertEquals(int.class, result.get(0).clazz().java());
    }

    @Test
    public void testParam_Simple() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("simpleSelectVoid", Integer.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(1, result.size());
        assertEquals(Integer.class, result.get(0).clazz().java());
    }

    @Test
    public void testParam_Lv1Single() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("select", Object.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(1, result.size());
        assertEquals(String.class, result.get(0).clazz().java());
    }

    @Test
    public void testParam_Lv2Single() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("select", Object.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(1, result.size());
        assertEquals(String.class, result.get(0).clazz().java());
    }

    @Test
    public void testParam_Lv2Multiple() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectList", Object.class, Object.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(2, result.size());
        assertEquals(Integer.class, result.get(0).clazz().java());
        assertEquals(String.class, result.get(1).clazz().java());
    }

    @Test
    public void testParam_Lv2CustomClass() throws Exception {
        ZClass clazz = ZClass.forClass(Level2Mapper.class);
        ZMethod method = clazz.getZMethod("selectCalculator", Calculator.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(1, result.size());
        ZParameterizedType paramType = result.get(0).parameterized();
        assertEquals(Calculator.class, paramType.getRawType().clazz().java());
        assertEquals(1, paramType.getActualTypeArguments().size());
        assertEquals(String.class, paramType.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testParam_Lv1Array() throws Exception {
        ZClass clazz = ZClass.forClass(Level1Mapper.class);
        ZMethod method = clazz.getZMethod("selectArray", List[].class);
        ZGenericList result = method.getGenericParameterZTypes();
        ZGenericArrayType genericArrayType = result.get(0).genericArray();
        ZParameterizedType paramType = genericArrayType.getGenericComponentType().parameterized();
        assertEquals(List.class, paramType.getRawType().clazz().java());
        assertEquals(String.class, paramType.getActualTypeArguments().get(0).clazz().java());
    }

    @Test
    public void testParam_Subclass() throws Exception {
        ZClass clazz = ZClass.forClass(Calculator.SubCalculator.class);
        ZMethod method = clazz.getZMethod("setId", Object.class);
        ZGenericList result = method.getGenericParameterZTypes();
        assertEquals(String.class, result.get(0).clazz().java());
    }

    @Test
    public void testReturn_Anonymous() throws Exception {
        Calculator<?> instance = new Calculator<Integer>();
        ZClass clazz = ZClass.forClass(instance.getClass());
        ZMethod method = clazz.getZMethod("getId");
        ZGeneric result = method.getGenericReturnZType();
        assertEquals(Object.class, result.clazz().java());
    }

    @Test
    public void testField_GenericField() throws Exception {
        ZClass clazz = ZClass.forClass(Calculator.SubCalculator.class);
        ZClass declaredClass = ZClass.forClass(Calculator.class);
        ZField field = declaredClass.getDeclaredZField("fld");
        field.withInheritedType(clazz.java());
        ZGeneric result = field.getGenericZType();
        assertEquals(String.class, result.clazz().java());
    }

    @Test
    public void testReturnParam_WildcardWithUpperBounds() throws Exception {
        class Key {
        }
        @SuppressWarnings("unused")
        class KeyBean<S extends Key & Cloneable, T extends Key> {
            private S key1;
            private T key2;

            public S getKey1() {
                return key1;
            }

            public void setKey1(S key1) {
                this.key1 = key1;
            }

            public T getKey2() {
                return key2;
            }

            public void setKey2(T key2) {
                this.key2 = key2;
            }
        }
        ZClass clazz = ZClass.forClass(KeyBean.class);
        ZMethod getter1 = clazz.getZMethod("getKey1");
        assertEquals(Key.class, getter1.getGenericReturnZType().clazz().java());
        ZMethod setter1 = clazz.getZMethod("setKey1", Key.class);
        assertEquals(Key.class, setter1.getGenericParameterZTypes().get(0).clazz().java());
        ZMethod getter2 = clazz.getZMethod("getKey2");
        assertEquals(Key.class, getter2.getGenericReturnZType().clazz().java());
        ZMethod setter2 = clazz.getZMethod("setKey2", Key.class);
        assertEquals(Key.class, setter2.getGenericParameterZTypes().get(0).clazz().java());
    }

    @Test
    public void testDeepHierarchy() throws Exception {
        @SuppressWarnings("unused")
        abstract class A<S> {
            protected S id;

            public S getId() {
                return this.id;
            }

            public void setId(S id) {
                this.id = id;
            }
        }
        abstract class B<T> extends A<T> {
        }
        abstract class C<U> extends B<U> {
        }
        class D extends C<Integer> {
        }
        ZClass clazz = ZClass.forClass(D.class);
        ZMethod method = clazz.getZMethod("getId");
        assertEquals(Integer.class, method.getGenericReturnZType().clazz().java());
        ZField field = ZClass.forClass(A.class).getDeclaredZField("id");
        field.withInheritedType(clazz.java());
        assertEquals(Integer.class, field.getGenericZType().clazz().java());
    }

    @Test
    public void shouldTypeVariablesBeComparedWithEquals() throws Exception {
        // #1794
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Type> futureA = executor.submit(new Callable<Type>() {
            @Override
            public Type call() throws Exception {
                Type retType = GenericResolver.resolveReturnType(IfaceA.class.getMethods()[0], IfaceA.class);
                return ((ParameterizedType) retType).getActualTypeArguments()[0];
            }
        });
        Future<Type> futureB = executor.submit(new Callable<Type>() {
            @Override
            public Type call() throws Exception {
                Type retType = GenericResolver.resolveReturnType(IfaceB.class.getMethods()[0], IfaceB.class);
                return ((ParameterizedType) retType).getActualTypeArguments()[0];
            }
        });
        assertEquals(AA.class, futureA.get());
        assertEquals(BB.class, futureB.get());
        executor.shutdown();
    }

    // @formatter:off
    class AA {
    }

    class BB {
    }

    interface IfaceA extends ParentIface<AA> {
    }

    interface IfaceB extends ParentIface<BB> {
    }

    interface ParentIface<T> {
        List<T> m();
    }
    // @formatter:on
}