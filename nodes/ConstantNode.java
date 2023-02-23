package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


/**
 * A Constant Node will handle any of the following
 *     Integer (<num>)
 *     String (<str_literal>)
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
        //handle Boolean's
        if (tokens.get(0).getToken().equals("true")) {
            tokens.remove(0);
            return new ConstantNode(tokens.get(0));
        }
        else if (tokens.get(0).getToken().equals("false")) {
            tokens.remove(0);
            return new ConstantNode(tokens.get(0));
        }
        //handle Integer's
        else if (true) {
            return new ConstantNode(tokens.get(0));
        }
        //handle string literals
        else if (true) {
            return new ConstantNode(tokens.get(0));
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
