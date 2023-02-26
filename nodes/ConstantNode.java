package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;


/**
 * A Constant Node will handle any of the following
 *     Integer or Double (<num>)
 *     String as defined in DFA (<str_literal>)
 *     Boolean (<bool>)
 */
public class ConstantNode extends ExprNode implements JottTree {

    private final Token token;

    /**
     * Constructor initalize's the actual token rather than the string representation of the token
     * because when we get to later phases and need to report an error, our token knows which
     * line it belongs too
     * @param token
     */
    public ConstantNode(Token token){
        this.token = token;
    }

    static ConstantNode parseConstantNode(ArrayList<Token> tokens) throws Exception {

        if (tokens.get(0).getTokenType().equals(TokenType.STRING) ||
                tokens.get(0).getTokenType().equals(TokenType.NUMBER) ||
                tokens.get(0).getToken().equals("true") || tokens.get(0).getToken().equals("false")) {
            return new ConstantNode(tokens.remove(0));
        } else {
            throw new Exception();
        }
    }

    @Override
    public String convertToJott() {
        return token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return token.getToken();
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        if (token.getToken().equals("true")) {
            return "True";
        } else if (token.getToken().equals("false")) {
            return "False";
        } else {
           return token.getToken();
        }
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
