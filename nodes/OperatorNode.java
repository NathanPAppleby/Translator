package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


/**
 * Handles <op> & <rel_op>
 */
public class OperatorNode implements JottTree {

    private final Token token;

    public OperatorNode(Token token){
        this.token = token;
    }

    static OperatorNode parseOperatorNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        switch (t.getToken()) { //first four <op>
            case "+", "*", "/", "-", ">", "<", ">=", "<=", "==", "!=" -> {
                tokens.remove(0);
                return new OperatorNode(t);
            }
            default -> {
                throw new Exception(String.format("Operator Error:\n\tExpected Operator Token, found \"%s\"\n\t%s:%d\n", t.getToken(), t.getFilename(), t.getLineNum()));
            }
        }
    }

    public Token getToken() {
        return token;
    }
    @Override
    public String convertToJott() {
        return token.getToken();
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
