package hdzitao.reflect4z.test.bean;


import hdzitao.reflect4z.parameter.ZParam;
import hdzitao.reflect4z.test.annotation.ParamAge;
import hdzitao.reflect4z.test.annotation.ParamName;

public class Person extends Man implements HasName, Show {
    private static final int number = 2;
    private int age = 20;

    @Override
    public int setAge(int age) {
        return this.age = age;
    }

    @Override
    public int packageAge(Integer age) {
        return age;
    }

    @Override
    public int primitiveAge(int age) {
        return age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getName() {
        return "taojinhou";
    }

    @Override
    public String show(String school, int grade) {
        return String.format("name:%s,age:%d,school:%s,grade:%d", getName(), age, school, grade);
    }

    private String secret(Integer i) {
        return "Secret:" + i;
    }

    public String getWork(int day) {
        return "has work:" + day;
    }

    public String work() {
        return "can work";
    }

    public void paramAnnotation(@ParamName(name = "taojinhou") @ZParam("name") String name,
                                @ParamAge(age = 20) @ZParam("age") int age) {

    }

    void testPackage() {
    }
}
