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
            if (tokens.get(0).getTokenType().equals(TokenType.L_BRACKET)) {
                tokens.remove(0);
                ExprNode exprNode = ExprNode.parseExprNode(tokens);
                if (tokens.get(0).getTokenType().equals(TokenType.R_BRACKET)) {
                    tokens.remove(0);
                    if (tokens.get(0).getTokenType().equals(TokenType.L_BRACE)) {
                        tokens.remove(0);
                        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                        if (tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                            tokens.remove(0);
                            ElseIfLstNode elseIfLstNode = ElseIfLstNode.parseElseIfLstNode(tokens);
                            return new ElseIfLstNode(exprNode, bodyNode, elseIfLstNode);
                        } else {
                            Token errToken = tokens.get(0);
                            throw new Exception(String.format("Else If Error:\n\tReceived token \"%s\" expected \"}\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                        }
                    } else {
                        Token errToken = tokens.get(0);
                        throw new Exception(String.format("Else If Error:\n\tReceived token \"%s\" expected \"{\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                    }
                } else {
                    Token errToken = tokens.get(0);
                    throw new Exception(String.format("Else If Error:\n\tReceived token \"%s\" expected \"]\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                }
            } else {
                Token errToken = tokens.get(0);
                throw new Exception(String.format("Else If Error:\n\tReceived token \"%s\" expected \"[\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
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
                "\n}";
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
        return bexprNode.validateTree() && bodyNode.validateTree() && elseIfLstNode.validateTree();
    }
}
