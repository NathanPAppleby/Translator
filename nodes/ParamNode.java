package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamNode implements JottTree {
    // < params > -> < expr > < params_t > | nothing

    private final ExprNode exprNode;
    private final ParamTNode paramTNode;

    public ParamNode(ExprNode exprNode, ParamTNode paramTNode) {
        this.exprNode = exprNode;
        this.paramTNode = paramTNode;
    }

    static ParamNode parseParamNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return null;
        }
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        ParamTNode paramTNode = ParamTNode.parseParamTNode(tokens); // can be nothing (null)
        return new ParamNode(exprNode, paramTNode);
    }

    @Override
    public String convertToJott() {
        return exprNode.convertToJott() + (paramTNode == null ? "" : paramTNode.convertToJott());
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
