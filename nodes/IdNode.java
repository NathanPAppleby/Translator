package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;


public class IdNode extends ExprNode implements JottTree {

    private final Token token;

    public IdNode(Token token){
        this.token = token;
    }

    static IdNode parseIdNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        if (t.getTokenType() != TokenType.ID_KEYWORD) {
            throw new Exception();
        }
        return new IdNode(t);
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
