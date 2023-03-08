package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncCallNode implements StmtNode, ExprNode {
    // < func_call > -> <id >[ params ]
    private final IdNode idNode;
    private final ParamNode paramNode; // Can be nothing

    public FuncCallNode(IdNode idNode, ParamNode paramNode){
        this.idNode = idNode;
        this.paramNode = paramNode;
    }

    static FuncCallNode parseFuncCallNode(ArrayList<Token> tokens) throws Exception {
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new Exception();
        }
        tokens.remove(0);
        ParamNode paramNode = ParamNode.parseParamNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new Exception();
        }
        tokens.remove(0);
        return new FuncCallNode(idNode, paramNode);
    }

    @Override
    public String convertToJott() {
        return idNode.convertToJott() + "[" + paramNode.convertToJott() + "]";
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
