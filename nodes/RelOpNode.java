package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class RelOpNode implements JottTree {
    private final Token token;
    public RelOpNode(Token token) {
        this.token = token;
    }

    static RelOpNode parseRelOpNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.remove(0);
        return switch (t.getToken()) {
            case ">", "<", ">=", "<=", "==", "!=" -> new RelOpNode(t);
            default -> throw new Exception();
        };
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
