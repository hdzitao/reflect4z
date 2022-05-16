package hdzitao.reflect4z.list;

import hdzitao.reflect4z.ZParameter;
import hdzitao.reflect4z.parameter.Parameter;

/**
 * ZParameter参数集合
 */
public class ZParameterList extends ReflectList<Parameter, ZParameter> {
    public ZParameterList(Parameter[] java) {
        super(java);
    }

    @Override
    protected ZParameter warpElement(Parameter parameter) {
        return ZParameter.forParameter(parameter);
    }
}
