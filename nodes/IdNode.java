package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


public class IdNode extends ExprNode implements JottTree {

    private final Token token;

    public IdNode(Token token){
        this.token = token;
    }

    static IdNode IdNode(ArrayList<Token> tokens) throws Exception { return null; }

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
