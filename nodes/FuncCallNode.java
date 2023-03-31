package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;
import symbols.FunctionParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

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
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        String functionName = this.idNode.getIdName();
        if (!functionSymbolTable.containsKey(functionName)) {
            // referencing a yet undefined function
            if(functionName.equals("print") && this.paramNode.getAllParamNodes().size() == 1){
                return true;
            }
            String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
            throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), file));
        }
        FunctionDef fd = functionSymbolTable.get(functionName);
        if(this.paramNode != null) {
            ArrayList<ParamNode> parameters = this.paramNode.getAllParamNodes();
            ArrayList<FunctionParameter> funcParameters = fd.parameters;
            // Incorrect number of parameters
            if (parameters.size() != funcParameters.size()) {
                String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
                throw new Exception("Semantic Error:\n\tIncorrect number of parameters in function call.\n\t" + file);
            }
            for (int i = 0; i < parameters.size(); i++) {
                if (!parameters.get(i).getType(functionSymbolTable, localVariableSymbolTable).equals(funcParameters.get(i).parameterReturnType)) {
                    // Parameter does not match the type of function parameter defined in the function definition
                    String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
                    throw new Exception("Semantic Error:\n\tParameter types does not match provided value.\n\t" + file);
                }
            }
            // Function is defined, same number of parameters coming in with the call as there are defined in the function,
            // and all passed parameters match the expected type
            return true;
        }
        else{
            ArrayList<FunctionParameter> funcParameters = fd.parameters;
            // Incorrect number of parameters
            if (funcParameters.size() != 0) {
                String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
                throw new Exception("Semantic Error:\n\tIncorrect number of parameters in function call.\n\t" + file);
            }
            // Function is defined, same number of parameters coming in with the call as there are defined in the function,
            // and all passed parameters match the expected type
            return true;
        }
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        if(functionSymbolTable.get(this.idNode.getIdName()) != null) {
            return functionSymbolTable.get(this.idNode.getIdName()).returnType.equals("Boolean");
        }
        String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
        throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), file));
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        if(functionSymbolTable.get(this.idNode.getIdName()) != null) {
            return functionSymbolTable.get(this.idNode.getIdName()).returnType;
        }
        String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
        throw new Exception(String.format("Semantic Error:\n\tReference to an undefined function \"%s\"\n\t%s",this.idNode.getIdName(), file));
    }

    @Override
    public Token getTokenObj() { return this.idNode.getTokenObj(); }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    @Override
    public boolean containsReturn() {
        return false;
    }

    @Override
    public boolean isInitalized() {return true;}
}
