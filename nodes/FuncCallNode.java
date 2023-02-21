package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FuncCallNode extends ExprNode implements JottTree {

    private final Token token;

    public FuncCallNode(Token token){
        this.token = token;
    }
    static FuncCallNode parseFuncCallNode(ArrayList<Token> tokens) { return null; }

    @Override
    public String convertToJott() {
        return null;
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
