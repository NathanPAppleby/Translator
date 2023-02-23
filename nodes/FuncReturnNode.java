package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FuncReturnNode implements JottTree {
    // < function_return > -> < type > | Void

    private final TypeNode typeNode;

    public FuncReturnNode(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws Exception {
         if (tokens.get(0).getToken().equals("Void")) {
             return new FuncReturnNode(null);
         }
         return new FuncReturnNode(TypeNode.parseTypeNode(tokens));
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
