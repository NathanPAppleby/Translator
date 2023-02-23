package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseNode implements JottTree {
    // < else > -> else { < body } | nothing

    private final BodyNode bodyNode;

    public ElseNode(BodyNode bodyNode) {
        this.bodyNode = bodyNode;
    }

    static ElseNode parseElseNode(ArrayList<Token> tokens) throws Exception{
        if (!tokens.get(0).getToken().equals("else")) {
            return null;
        }
        tokens.remove(0);

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new Exception();
        }
        tokens.remove(0);

        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new Exception();
        }
        tokens.remove(0);

        return new ElseNode(bodyNode);
    }

    @Override
    public String convertToJott() {
        return "else {" + bodyNode.convertToJott() + "}";
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
