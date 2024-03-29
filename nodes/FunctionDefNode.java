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
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        HashMap<String, ArrayList<String>> newLocalVarTable = new HashMap<>();
        boolean isValidated = this.idNode.validateTree(functionSymbolTable, newLocalVarTable) &&
                (this.fDefParamNode == null || this.fDefParamNode.validateTree(functionSymbolTable, newLocalVarTable)) &&
                this.funcReturnNode.validateTree(functionSymbolTable, newLocalVarTable) &&
                this.bodyNode.validateTree(functionSymbolTable, newLocalVarTable);

        // Return Checking
        boolean returnPresent = this.bodyNode.validateReturn(functionSymbolTable, newLocalVarTable, this.funcReturnNode.getReturnType());
        if (this.funcReturnNode.getReturnType().equals("Void") && returnPresent) {
            String filename = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
            throw new Exception("Semantic Error\n\tUnexpected return in Void function " + this.idNode.getIdName() + "\n\t"+ filename);
        }
        else if (!this.funcReturnNode.getReturnType().equals("Void") && !returnPresent) {
            String filename = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
            throw new Exception("Semantic Error\n\tNo return found in requiring function " + this.idNode.getIdName() + "\n\t"+ filename);
        }


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
        String id = this.idNode.convertToJava(className);
        String defParams = (this.fDefParamNode == null ? "" : this.fDefParamNode.convertToJava(className));
        if (id.equals("main") && defParams.equals("")) {
            defParams = "String[] args";
        }
        return "public static " + this.funcReturnNode.convertToJava(className) + " " +
                id + "(" + defParams + ") {\n" +
                this.bodyNode.convertToJava(className) +
                "}\n";
    }

    @Override
    public String convertToC() {
        String id = this.idNode.convertToC();
        if (id.equals("main")) {
            return "int" + " " + this.idNode.convertToC() + "(void) {\n" +
                    this.bodyNode.convertToC() + "return 1;\n}\n";
        }
        else if (this.fDefParamNode == null) {
            return this.funcReturnNode.convertToC() + " " + this.idNode.convertToC() + "() {\n" +
                    this.bodyNode.convertToC() + "}\n";
        }
        else {
            return this.funcReturnNode.convertToC() + " " + this.idNode.convertToC() + "( " +
                    this.fDefParamNode.convertToC() + " ) {\n" + this.bodyNode.convertToC() + "}\n";
        }
    }

    @Override
    public String convertToPython(int depth) {
        String output = "\t".repeat(depth) +
                "def " +
                this.idNode.convertToPython(depth) +
                "(";
        if(this.fDefParamNode != null) {
            output += this.fDefParamNode.convertToPython(depth);
        }
        output += "):\n" + this.bodyNode.convertToPython(depth + 1);
        return output;
    }

    public String getFilenameAndLine(){
        return this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
    }
}
