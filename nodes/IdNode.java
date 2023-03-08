package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;


public class IdNode implements ExprNode {

    private final Token token;

    public IdNode(Token token){
        this.token = token;
    }

    static IdNode parseIdNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        if (t.getTokenType() != TokenType.ID_KEYWORD) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("\nID/Keyword Error:\n\tExpected id/keyword token, found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        return new IdNode(t);
    }

    public String getIdName() {
        return token.getToken();
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
