package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode {

    private ExprNode b_expr;
    private BodyNode body;
    private ElseIfLstNode elseif_lst;
    private ElseNode else_node;

    public IfStmtNode(ExprNode exprNode, BodyNode bodyNode, ElseIfLstNode elseIfLstNode, ElseNode elseNode) {
        b_expr = exprNode;
        body = bodyNode;
        elseif_lst = elseIfLstNode;
        else_node = elseNode;
    }

    static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws Exception {
        tokens.remove( 0 );
        if ( tokens.get( 0 ).getTokenType().equals( TokenType.L_BRACKET ) ) {
            tokens.remove(0);
            ExprNode exprNode = ExprNode.parseExprNode( tokens );
            if ( tokens.get( 0 ).getTokenType().equals( TokenType.R_BRACKET ) ) {
                tokens.remove(0);
                if ( tokens.get( 0 ).getTokenType().equals( TokenType.L_BRACE) ) {
                    tokens.remove(0);
                    BodyNode bodyNode = BodyNode.parseBodyNode( tokens );
                    if ( tokens.get( 0 ).getTokenType().equals( TokenType.R_BRACE ) ) {
                        tokens.remove(0);
                        ElseIfLstNode elseIfLstNode = ElseIfLstNode.parseElseIfLstNode( tokens );
                        ElseNode elseNode = ElseNode.parseElseNode( tokens );
                        return new IfStmtNode(exprNode, bodyNode, elseIfLstNode, elseNode);
                    } else {
                        Token errToken = tokens.get(0);
                        System.err.printf("\nMissing Token Error:\n\tExpected \"}\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
                        throw new Exception();
                    }
                } else {
                    Token errToken = tokens.get(0);
                    System.err.printf("\nMissing Token Error:\n\tExpected \"{\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
                    throw new Exception();
                }
            } else {
                Token errToken = tokens.get(0);
                System.err.printf("\nMissing Token Error:\n\tExpected \"]\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
                throw new Exception();
            }
        } else {
            Token errToken = tokens.get(0);
            System.err.printf("\nMissing Token Error:\n\tExpected \"[\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
            throw new Exception();
        }
    }

    @Override
    public String convertToJott() {
        return "if[" +
                this.b_expr.convertToJott() +
                "] {\n" +
                this.body.convertToJott() +
                "\n}\n" +
                this.elseif_lst.convertToJott() +
                this.else_node.convertToJott();
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
