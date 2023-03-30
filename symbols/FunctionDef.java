package symbols;

import nodes.FunctionDefNode;
import nodes.FunctionDefParamNode;

import java.util.ArrayList;

public class FunctionDef {
    public String functionName;
    public ArrayList<FunctionParameter> parameters;
    public String returnType;

    public FunctionDef(String functionName, ArrayList<FunctionParameter> parameters, String returnType) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public static FunctionDef buildFunctionDef(FunctionDefNode fd) {
        ArrayList<FunctionParameter> parameters = new ArrayList<>();
        for (FunctionDefParamNode param : fd.getFunctionParameters()) {
            parameters.add(FunctionParameter.buildFunctionParameter(param));
        }
        return new FunctionDef(fd.getFunctionName(), parameters, fd.getFunctionReturnType());
    }

    public String getReturnType() {
        return this.returnType;
    }
}

