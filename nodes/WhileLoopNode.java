package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class WhileLoopNode implements JottTree {

    private final ExprNode bool_expr;
    private final BodyNode body_node;

    WhileLoopNode(ExprNode b_expr, BodyNode bodyNode) {
        this.bool_expr = b_expr;
        this.body_node = bodyNode;
    }

    static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception {
        if (!tokens.get(0).getToken().equals("while")) {
            throw new Exception();
        }
        tokens.remove(0); //remove "while"
        if (!tokens.get(0).getTokenType().equals(TokenType.L_BRACKET)) {
            throw new Exception();
        }
        tokens.remove(0); //remove '['

        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        if (!tokens.get(0).getTokenType().equals(TokenType.R_BRACKET)) {
            throw new Exception();
        }
        tokens.remove(0); //remove ']'
        if (!tokens.get(0).getTokenType().equals(TokenType.L_BRACE)) {
            throw new Exception();
        }
        tokens.remove(0); //remove '{'

        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

        if (!tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
            throw new Exception();
        }
        tokens.remove(0); //remove '}'

        return new WhileLoopNode(exprNode, bodyNode);
    }

    @Override
    public String convertToJott() {
        return "while[" + this.bool_expr.convertToJott() + "] " +
                "{" + this.body_node.convertToJott() + "}";
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
