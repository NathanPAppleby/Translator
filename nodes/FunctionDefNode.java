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
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
        if (tokens.get(0).getTokenType() == TokenType.R_BRACE) {
            tokens.remove(0);
        }
        else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Error:\n\tExpected \"}\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }

        return new FunctionDefNode(idNode, fDefParamNode, funcReturnNode, bodyNode);
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, String> localVariableSymbolTable) throws Exception {
        HashMap<String, String> newLocalVarTable = new HashMap<>();
        boolean isValidated = this.idNode.validateTree(functionSymbolTable, newLocalVarTable) &&
                (this.fDefParamNode == null || this.fDefParamNode.validateTree(functionSymbolTable, newLocalVarTable)) &&
                this.funcReturnNode.validateTree(functionSymbolTable, newLocalVarTable) &&
                this.bodyNode.validateTree(functionSymbolTable, newLocalVarTable);
        // Check to make sure if return type is not "Void"
        // then there is a return statement within the body or every return statement
        //todo Validate whatever is returned is same type as return type
        //todo Validate if void that there is no return, if not void then there is return
        String returnType = this.bodyNode.getReturn(functionSymbolTable, newLocalVarTable);
        // If it is not void but there is no return found, error
        if (!this.funcReturnNode.getReturnType().equals("Void") && returnType == null) {
            throw new Exception("Semantic Error: no return found (need to clean up exception, in FunctionDefNode line 91");
        }
        // If it is void but a return is found, error
        if (this.funcReturnNode.getReturnType().equals("Void") && returnType != null) {
            throw new Exception("Semantic Error: Return found in void function");
        }
        // If the return types don't match, error
        if (!this.funcReturnNode.getReturnType().equals(returnType)) {
            throw new Exception("Semantic Error: Return does not match function return type");
        }

//        if (!this.funcReturnNode.getReturnType().equals("Void")) {
//            this.bodyNode.alwaysReturns();
//        }
        return isValidated;
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

    public String getFilenameAndLine(){
        return this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
    }
}
