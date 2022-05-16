package hdzitao.reflect4z;

import hdzitao.reflect4z.list.ZAnnotationList;
import hdzitao.reflect4z.parameter.Parameter;
import hdzitao.reflect4z.reflect.AnnotationResolver;

import java.lang.annotation.Annotation;

/**
 * 参数封装类
 */
public class ZParameter extends ReflectElement<Parameter> implements ZAnnotationElement<Parameter> {

    private ZParameter(Parameter java) {
        super(java);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return AnnotationResolver.getJavaAnnotation(this.java, annotationClass);
    }

    @Override
    public ZAnnotation getZAnnotation(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.getAnnotation(this.java, annotationClass);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return AnnotationResolver.isAnnotationPresent(this.java, annotationClass);
    }

    @Override
    public ZAnnotationList getZAnnotations() {
        return AnnotationResolver.getAnnotations(this.java);
    }

    @Override
    public ZAnnotationList getDeclaredZAnnotations() {
        return AnnotationResolver.getDeclaredAnnotations(this.java);
    }

    /**
     * 创建ZParameter
     *
     * @param java Parameter
     * @return ZParameter
     */
    public static ZParameter forParameter(Parameter java) {
        return new ZParameter(java);
    }

    /**
     * 获取参数索引
     *
     * @return 参数索引
     */
    public int getIndex() {
        return this.java.getIndex();
    }

    /**
     * 有没有名字
     *
     * @return true 保留了参数名
     */
    public boolean isNamePresent() {
        return this.java.isNamePresent();
    }

    /**
     * 获取参数名
     *
     * @return 参数名
     */
    public String getName() {
        return this.java.getName();
    }

    /**
     * 获取泛型
     *
     * @return 泛型
     */
    public ZGeneric getParameterizedZType() {
        return ZGeneric.forType(this.java.getParameterizedType());
    }

    /**
     * 获取参数类型
     *
     * @return 参数类型
     */
    public ZClass getZType() {
        return ZClass.forClass(this.java.getType());
    }

    /**
     * 是否隐式参数
     *
     * @return true 如果是隐式参数
     */
    public boolean isImplicit() {
        return this.java.isImplicit();
    }

    /**
     * 是否Java编译器引入
     * 主要用于内部类和外部类中间的私有成员的访问
     *
     * @return true 如果Java编译器引入
     */
    public boolean isSynthetic() {
        return this.java.isSynthetic();
    }

    /**
     * 是否是可变参数
     *
     * @return true 如果是可变参数
     */
    public boolean isVarArgs() {
        return this.java.isVarArgs();
    }
}
