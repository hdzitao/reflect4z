package hdzitao.reflect4z.test.annotation;

import java.lang.annotation.*;

/**
 * Created by taojinhou on 2019/10/18.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAge {
    int age();
}
