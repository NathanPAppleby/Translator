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

    @Override
    public String convertToJott() {
        return exprNode.convertToJott() + (paramTNode == null ? "" : paramTNode.convertToJott());
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

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        return this.exprNode.validateTree(functionSymbolTable, localVariableSymbolTable) &&
                (this.paramTNode == null || this.paramTNode.validateTree(functionSymbolTable, localVariableSymbolTable));
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

    public String getType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable);
    }
}
