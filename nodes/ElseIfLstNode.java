package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ElseIfLstNode implements JottTree {
    private ExprNode bexprNode;
    private BodyNode bodyNode;
    private ElseIfLstNode elseIfLstNode;

    public ElseIfLstNode(ExprNode exprNode, BodyNode bodyNode, ElseIfLstNode elseIfLstNode) {
        this.bexprNode = exprNode;
        this.bodyNode = bodyNode;
        this.elseIfLstNode = elseIfLstNode;
    }

    static ElseIfLstNode parseElseIfLstNode(ArrayList<Token> tokens) throws Exception {
        if(tokens.get( 0 ).getToken().equals("elseif")) {
            tokens.remove( 0 );
            if (tokens.remove(0).getTokenType().equals(TokenType.L_BRACKET)) {
                ExprNode exprNode = ExprNode.parseExprNode(tokens);
                if (tokens.remove(0).getTokenType().equals(TokenType.R_BRACKET)) {
                    if (tokens.remove(0).getTokenType().equals(TokenType.L_BRACE)) {
                        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                        if (tokens.remove(0).getTokenType().equals(TokenType.R_BRACE)) {
                            ElseIfLstNode elseIfLstNode = ElseIfLstNode.parseElseIfLstNode(tokens);
                            return new ElseIfLstNode(exprNode, bodyNode, elseIfLstNode);
                        } else {
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
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
        String out =    "elseif[ " +
                this.bexprNode.convertToJott() +
                "] {\n" +
                this.bodyNode.convertToJott() +
                "\n}\n";
        if (this.elseIfLstNode != null) {
            out += this.elseIfLstNode.convertToJott();
        }
        return out;
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
