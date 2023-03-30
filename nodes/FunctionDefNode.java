package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionDefNode implements JottTree {
    // < function_def > -> def <id >[ func_def_params ]: < function_return >{ < body >}

    private final IdNode idNode;
    private final FunctionDefParamNode fDefParamNode;
    private final FuncReturnNode funcReturnNode;
    private final BodyNode bodyNode;
    private final HashMap<String, String> localVarSymbolTable;

    public FunctionDefNode(IdNode idNode, FunctionDefParamNode fDefParamNode, FuncReturnNode funcReturnNode,
                           BodyNode bodyNode, HashMap<String, String> localVarSymbolTable) {
        this.idNode = idNode;
        this.fDefParamNode = fDefParamNode;
        this.funcReturnNode = funcReturnNode;
        this.bodyNode = bodyNode;
        this.localVarSymbolTable = localVarSymbolTable;
        System.out.println(idNode.getIdName() + localVarSymbolTable.toString());
    }

    static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens, HashMap<String, FunctionDef> functionSymbolTable) throws Exception {
        if (tokens.get(0).getToken().equals("def")) {
            tokens.remove(0);
        }
        else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"def\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.L_BRACKET) {
            tokens.remove(0);
        }
        else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"[\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        FunctionDefParamNode fDefParamNode = FunctionDefParamNode.parseFunctionDefParamNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET
            && tokens.get(1).getTokenType() == TokenType.COLON) {
            tokens.remove(0);
            tokens.remove(0);
        }
        else {
            Token errToken;
            if (tokens.get(0).getTokenType().equals(TokenType.R_BRACKET)) {
                errToken = tokens.get(1);
            } else {
                errToken = tokens.get(0);
            }
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"]\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        FuncReturnNode funcReturnNode = FuncReturnNode.parseFuncReturnNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.L_BRACE) {
            tokens.remove(0);
        }
        else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"{\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        HashMap<String, String> localVariableSymbolTable = new HashMap<>();
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens, localVariableSymbolTable);
        if (tokens.get(0).getTokenType() == TokenType.R_BRACE) {
            tokens.remove(0);
        }
        else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"}\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }

        return new FunctionDefNode(idNode, fDefParamNode, funcReturnNode, bodyNode, localVariableSymbolTable);
    }

    public String getFunctionName() {
        return this.idNode.getIdName();
    }

    public ArrayList<FunctionDefParamNode> getFunctionParameters() {
        ArrayList<FunctionDefParamNode> paramNodes = new ArrayList<>();
        FunctionDefParamNode paramNode = this.fDefParamNode;
        while (paramNode != null) {
            paramNodes.add(paramNode);
            paramNode = paramNode.getTail();
        }
        return paramNodes;
    }

    public String getFunctionReturnType() {
        return this.funcReturnNode.getReturnType();
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
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        HashMap<String, String> newLocalVariableSymbolTable = new HashMap<>();
        return false;
    }
}
