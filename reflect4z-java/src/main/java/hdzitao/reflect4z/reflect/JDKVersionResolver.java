package hdzitao.reflect4z.reflect;

import lombok.Getter;

public abstract class JDKVersionResolver {
    private JDKVersionResolver() {
    }

    @Getter
    private static boolean sinceJDK8 = true;


    static {
        try {
            Class.forName("java.lang.reflect.Parameter");
        } catch (ClassNotFoundException ignore) {
            // jdk7
            sinceJDK8 = false;
        }
    }
}
