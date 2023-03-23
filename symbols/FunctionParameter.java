package symbols;

import nodes.FunctionDefParamNode;

public class FunctionParameter {
    public String parameterName;
    public String parameterReturnType;

    public FunctionParameter(String parameterName, String parameterReturnType) {
        this.parameterName = parameterName;
        this.parameterReturnType = parameterReturnType;
    }

    public static FunctionParameter buildFunctionParameter(FunctionDefParamNode paramNode) {
        return new FunctionParameter(paramNode.getParamName(), paramNode.getReturnType());
    }

}