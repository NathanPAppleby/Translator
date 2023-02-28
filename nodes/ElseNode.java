package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseNode implements JottTree {
    private BodyNode bodyNode;

    private ElseNode(BodyNode bodyNode){
        this.bodyNode = bodyNode;
    }

    static ElseNode parseElseNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getToken().equals("else")) {
            tokens.remove(0);
            if(tokens.remove(0).getTokenType().equals(TokenType.L_BRACE)) {
                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                if (tokens.remove(0).getTokenType().equals(TokenType.R_BRACE)) {
                    return new ElseNode(bodyNode);
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        } else {
            return null;
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
