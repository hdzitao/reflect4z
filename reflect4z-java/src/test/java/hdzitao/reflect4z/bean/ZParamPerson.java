package hdzitao.reflect4z.bean;


import hdzitao.reflect4z.parameter.ZParam;
import hdzitao.reflect4z.test.annotation.ParamAge;
import hdzitao.reflect4z.test.annotation.ParamName;
import hdzitao.reflect4z.test.bean.FPerson;

public class ZParamPerson extends FPerson {
    public void paramAnnotation(@ParamName(name = "taojinhou") @ZParam("name") String name,
                                @ParamAge(age = 20) @ZParam("age") int age) {

    }
}
