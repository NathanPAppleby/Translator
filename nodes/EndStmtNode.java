package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class EndStmtNode implements JottTree {

    private final Token token;

    public EndStmtNode(Token token){
        this.token = token;
    }
    static EndStmtNode parseEndStmtNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        if (t.getToken().equals(";")) {
            tokens.remove(0);
            return new EndStmtNode(t);
        }
        else {
            throw new Exception();
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
