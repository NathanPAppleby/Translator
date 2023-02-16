package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BoolNode implements JottTree {
    private final boolean booleanValue;

    public BoolNode(boolean booleanValue) { this.booleanValue = booleanValue; }

    static BoolNode parseBoolNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        if (t.getTokenType() == TokenType.ID_KEYWORD) {
            if (t.getToken().equals("True")) {
                tokens.remove(0);
                return new BoolNode(true);
            }
            else if (t.getToken().equals("False")) {
                tokens.remove(0);
                return new BoolNode(false);
            }
        }
        throw new Exception("Invalid boolean value: " + t.getToken());
    }

    @Override
    public String convertToJott() { return this.booleanValue ? "True" : "False"; }

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
