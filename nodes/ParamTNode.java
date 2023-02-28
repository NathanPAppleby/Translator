package nodes;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamTNode extends ParamNode {
    // < params_t > -> ,< expr > < params_t > | nothing
    public ParamTNode(ExprNode exprNode, ParamTNode paramTNode) {
        super(exprNode, paramTNode);
    }

    static ParamTNode parseParamTNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() != TokenType.COMMA) {
            return null;
        }
        tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        ParamTNode paramTNode = ParamTNode.parseParamTNode(tokens);
        return new ParamTNode(exprNode, paramTNode);
    }

    @Override
    public String convertToJott() {
        return "," + super.convertToJott();
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
