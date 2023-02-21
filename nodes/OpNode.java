package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


public class OpNode implements JottTree {

    private final Token token;

    public OpNode(Token token){
        this.token = token;
    }

    static OpNode parseOpNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        switch (t.getToken()) {
            case "+", "*", "/", "-" -> {
                tokens.remove(0);
                return new OpNode(t);
            }
            default -> throw new Exception();
        }
    }

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
