package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {
    // < function_def > -> def <id >[ func_def_params ]: < function_return >{ < body >}

    private final IdNode idNode;
    private final FunctionDefParamNode fDefParamNode;
    private final FuncReturnNode funcReturnNode;
    private final BodyNode bodyNode;

    public FunctionDefNode(IdNode idNode, FunctionDefParamNode fDefParamNode, FuncReturnNode funcReturnNode,
                           BodyNode bodyNode) {
        this.idNode = idNode;
        this.fDefParamNode = fDefParamNode;
        this.funcReturnNode = funcReturnNode;
        this.bodyNode = bodyNode;
    }

    static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getToken().equals("def")) {
            tokens.remove(0);
        }
        else {
            throw new Exception();
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.L_BRACKET) {
            tokens.remove(0);
        }
        else {
            throw new Exception();
        }
        FunctionDefParamNode fDefParamNode = FunctionDefParamNode.parseFunctionDefParamNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET
            && tokens.get(1).getTokenType() == TokenType.COLON) {
            tokens.remove(0);
            tokens.remove(0);
        }
        else {
            throw new Exception();
        }
        FuncReturnNode funcReturnNode = FuncReturnNode.parseFuncReturnNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.L_BRACE) {
            tokens.remove(0);
        }
        else {
            throw new Exception();
        }
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.R_BRACE) {
            tokens.remove(0);
        }
        else {
            throw new Exception();
        }

        return new FunctionDefNode(idNode, fDefParamNode, funcReturnNode, bodyNode);
    }

    @Override
    public String convertToJott() {
        return "def " +
                this.idNode.convertToJott() +
                "[" +
                (this.fDefParamNode == null ? "" : this.fDefParamNode.convertToJott())  +
                "]: " +
                this.funcReturnNode.convertToJott() +
                "{" +
                this.bodyNode.convertToJott() +
                "}";
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
