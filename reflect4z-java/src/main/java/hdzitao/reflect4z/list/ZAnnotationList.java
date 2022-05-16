package hdzitao.reflect4z.list;

import hdzitao.reflect4z.ZAnnotation;

import java.lang.annotation.Annotation;

/**
 * ZAnnotation注解集合
 */
public class ZAnnotationList extends ReflectList<Annotation, ZAnnotation> {
    public ZAnnotationList(Annotation[] java) {
        super(java);
    }

    @Override
    protected ZAnnotation warpElement(Annotation annotation) {
        return ZAnnotation.forAnnotation(annotation);
    }
}
