package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ParamNode implements JottTree {
    // < params > -> < expr > < params_t > | nothing

    private final ExprNode exprNode;
    private final ParamTNode paramTNode;

    public ParamNode(ExprNode exprNode, ParamTNode paramTNode) {
        this.exprNode = exprNode;
        this.paramTNode = paramTNode;
    }

    static ParamNode parseParamNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return null;
        }
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        ParamTNode paramTNode = ParamTNode.parseParamTNode(tokens); // can be nothing (null)
        return new ParamNode(exprNode, paramTNode);
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        return this.exprNode.validateTree(functionSymbolTable, localVariableSymbolTable) &&
                (this.paramTNode == null || this.paramTNode.validateTree(functionSymbolTable, localVariableSymbolTable));
    }

    @Override
    public String convertToJott() {
        return exprNode.convertToJott() + (paramTNode == null ? "" : paramTNode.convertToJott());
    }

    @Override
    public String convertToJava(String className) {
        return exprNode.convertToJava(className) + (paramTNode == null ? "" : paramTNode.convertToJava(className));
    }

    @Override
    public String convertToC() {
        return exprNode.convertToC() + (paramTNode == null ? "" : paramTNode.convertToC());
    }

    @Override
    public String convertToPython(int depth) {
        return exprNode.convertToPython(0) + (paramTNode == null ? "" : paramTNode.convertToPython(0));
    }

    // Retrieve all linked Param and ParamTNodes into a single, easily accessible list
    public ArrayList<ParamNode> getAllParamNodes() {
        ArrayList<ParamNode> pn;
        if (this.paramTNode == null) {
            pn = new ArrayList<>();
        }
        else {
            pn = this.paramTNode.getAllParamNodes();
        }
        pn.add(0, this);
        return pn;
    }

    //Due to the need of more specific operation types, this function either
    //returns the given type, or it returns a more generalized version of a specific type
    public String getType(HashMap<String, FunctionDef> functionSymbolTable,
                          HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        String type = this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable);
        if(type.contains("Boolean")){
            return "Boolean";
        }
        else{
            return type;
        }
    }

    public ExprNode getExprNode() {
        return exprNode;
    }
}
