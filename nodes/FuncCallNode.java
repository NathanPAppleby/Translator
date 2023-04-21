package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;
import symbols.FunctionParameter;

import java.util.ArrayList;
import java.util.HashMap;

public class FuncCallNode implements StmtNode, ExprNode {
    // < func_call > -> <id >[ params ]
    private final IdNode idNode;
    private final ParamNode paramNode; // Can be nothing
    private final boolean isOperation;

    public FuncCallNode(IdNode idNode, ParamNode paramNode){
        this.idNode = idNode;
        this.paramNode = paramNode;
        this.isOperation = false;
    }

    static FuncCallNode parseFuncCallNode(ArrayList<Token> tokens) throws Exception {
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Call Error:\n\tExpected \"[\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        ParamNode paramNode = ParamNode.parseParamNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Call Error:\n\tExpected \"]\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        return new FuncCallNode(idNode, paramNode);
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        String functionName = this.idNode.getIdName();
        if (!functionSymbolTable.containsKey(functionName)) {
            throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), this.getLocation()));
        }
        FunctionDef fd = functionSymbolTable.get(functionName);
        if(this.paramNode != null) {
            ArrayList<ParamNode> parameters = this.paramNode.getAllParamNodes();
            ArrayList<FunctionParameter> funcParameters = fd.parameters;
            // Incorrect number of parameters
            if (parameters.size() != funcParameters.size()) {
                throw new Exception("Semantic Error:\n\tIncorrect number of parameters in function call.\n\t" + this.getLocation());
            }
            for (int i = 0; i < parameters.size(); i++) {
                try {
                    if (!parameters.get(i).getType(functionSymbolTable, localVariableSymbolTable).equals(funcParameters.get(i).parameterReturnType)
                            && !funcParameters.get(i).parameterReturnType.equals("Any")){
                        // Parameter does not match the type of function parameter defined in the function definition
                        throw new Exception("Semantic Error:\n\tParameter types do not match provided value.\n\t" + this.getLocation());
                    }
                }
                catch (Exception e) {
                    throw new Exception(String.format("Semantic Error:\n\tReference to an uninitialized variable \n\t%s", this.getLocation()));
                }
//                if (!parameters.get(i).getType(functionSymbolTable, localVariableSymbolTable).equals(funcParameters.get(i).parameterReturnType)
//                && !funcParameters.get(i).parameterReturnType.equals("Any")){
//                    // Parameter does not match the type of function parameter defined in the function definition
//                    throw new Exception("Semantic Error:\n\tParameter types do not match provided value.\n\t" + this.getLocation());
//                }
            }
            // Function is defined, same number of parameters coming in with the call as there are defined in the function,
            // and all passed parameters match the expected type
            return true;
        }
        else{
            ArrayList<FunctionParameter> funcParameters = fd.parameters;
            // Incorrect number of parameters
            if (funcParameters.size() != 0) {
                throw new Exception("Semantic Error:\n\tIncorrect number of parameters in function call.\n\t" + this.getLocation());
            }
            // Function is defined, same number of parameters coming in with the call as there are defined in the function,
            // and all passed parameters match the expected type
            return true;
        }
    }

    @Override
    public boolean isInitialized(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) {
        //check for function declaration
        if (!functionSymbolTable.containsKey(this.idNode.getIdName())) {
            try {
                throw new Exception(String.format("Semantic Error:\n\tFunction \"%s\" is undefined " +
                                "\n\t%s", this.idNode.getIdName(), this.getLocation()));
            } catch (Exception e) {
                System.out.println("Error here~");
                throw new RuntimeException(e);
            }
        }
        //make sure provided number of params is correct
        int numParamsNeeded = functionSymbolTable.get(this.idNode.getIdName()).parameters.size();
        int numberProvidedParams;
        if(this.paramNode == null) {
            numberProvidedParams = 0;
            if (numParamsNeeded != numberProvidedParams) {
                try {
                    throw new Exception(String.format("Semantic Error:\n\tIncorrect number of parameters provided\n\t"+this.getLocation()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ArrayList<ParamNode> params = new ArrayList<>();
        if (this.paramNode != null) {
            params = this.paramNode.getAllParamNodes();
        }
        if (params.size() != numParamsNeeded) {
            try {
                throw new Exception(String.format("Semantic Error:\n\tIncorrect number of parameters provided\n\t"+this.getLocation()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //make sure all params are initalized
        for (ParamNode curParam : params) {
            if (!curParam.getExprNode().isInitialized(functionSymbolTable, localVariableSymbolTable)) {
                try {
                    throw new Exception(String.format("Semantic Error:\n\tParameter has not been initialized\n\t"+this.getLocation()));
                } catch (Exception e) {
                    System.out.println("error 2");
                    throw new RuntimeException(e);

                }
            }
        }
        return true;
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        if(functionSymbolTable.get(this.idNode.getIdName()) != null) {
            return functionSymbolTable.get(this.idNode.getIdName()).returnType.equals("Boolean");
        }
        throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), this.getLocation()));
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        if(functionSymbolTable.get(this.idNode.getIdName()) != null) {
            return functionSymbolTable.get(this.idNode.getIdName()).returnType;
        }
        throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), this.getLocation()));
    }

    @Override
    public Token getTokenObj() { return this.idNode.getTokenObj(); }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    @Override
    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        return false;
    }

    @Override
    public String getLocation() {
        return String.format("%s:%s",this.idNode.getTokenObj().getFilename(), this.idNode.getTokenObj().getLineNum());
    }
    @Override
    public String convertToJott() {

        String output = idNode.convertToJott() + "[";
        if(paramNode != null){
            output += paramNode.convertToJott();
        }
        output += "]";
        return output;
    }

    @Override
    public String convertToJava(String className) {
        String id = idNode.convertToJava(className);
        if (id.equals("print")) {
            id = "System.out.println";
        }
        String output = id + "(";
        if(paramNode != null){
            output += paramNode.convertToJava(className);
        }
        output += ")";
        return output;
    }

    @Override
    public String convertToC() {
        String output = idNode.convertToC() + "(";
        if(paramNode != null){
            output += paramNode.convertToC();
        }
        output += ")";
        return output;
    }

    @Override
    public String convertToPython(int depth) {
        String output = "\t".repeat(Math.max(0, depth)) + idNode.convertToPython(0) + "(";
        if(paramNode != null){
            output += paramNode.convertToPython(0);
        }
        output += ")";
        return output;
    }
}
