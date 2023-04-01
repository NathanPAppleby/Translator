package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class FuncReturnNode implements JottTree {
    // < function_return > -> < type > | Void

    private final TypeNode typeNode;

    public FuncReturnNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws Exception {
         if (tokens.get(0).getToken().equals("Void")) {
             tokens.remove(0);
             return new FuncReturnNode(null);
         }
         return new FuncReturnNode(TypeNode.parseTypeNode(tokens));
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, IdNode> localVariableSymbolTable) throws Exception {
        return this.typeNode == null || this.typeNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    public String getReturnType() {
        return typeNode == null ? "Void" : typeNode.convertToJott();
    }

    @Override
    public String convertToJott() {
        return (typeNode == null ? "Void" : typeNode.convertToJott());
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
}
