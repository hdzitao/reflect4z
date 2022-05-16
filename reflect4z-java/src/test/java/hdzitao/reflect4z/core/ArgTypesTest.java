package hdzitao.reflect4z.core;

import hdzitao.reflect4z.ZClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgTypesTest {
    public static class A {
        private String m(B a) {
            return "B";
        }

    }

    public static class B extends A {

        private String m(B a) {
            return "A";
        }

        public String m2(A a) {
            return "A2";
        }
    }

    @Test
    public void some() {
        assertEquals("A", ZClass.forClass(B.class).newZObject().send("m", new B()));
    }

    @Test
    public void inherited() {
        assertEquals("A2", ZClass.forClass(B.class).newZObject().send("m2", new B()));
    }
}
