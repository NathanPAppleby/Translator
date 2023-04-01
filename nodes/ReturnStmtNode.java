package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ReturnStmtNode implements JottTree {
    private final ExprNode exprNode;

    public ReturnStmtNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws Exception {
        if (!tokens.get(0).getToken().equals("return")) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Return Statement Error:\n\tReceived token \"%s\" expected \"return\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        if(tokens.get(0).getToken().equals(";")) {
            tokens.remove(0);
            return new ReturnStmtNode(exprNode);
        }
        else{
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Return Statement Error:\n\tExpected \";\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        return this.exprNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable,
                                  HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception{

        //Checks if value is initialized
        this.exprNode.isInitialized(functionSymbolTable, localVariableSymbolTable);
        if(returnType.equals("Void")){
            throw new Exception("Semantic Error\n\tUnexpected return in Void function\n\t"+ this.getLocation());
        }
        else if (this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable).equals(returnType)) {
            return true;
        }
        else {
            throw new Exception(String.format("Semantic Error\n\tInvalid return type \"%s\" in \"%s\" function\n\t%s",this.exprNode.getJottType(functionSymbolTable,
                    localVariableSymbolTable),returnType, this.getLocation()));
        }
    }

    public String getLocation() {
        return String.format("%s:%s",this.exprNode.getTokenObj().getFilename(), this.exprNode.getTokenObj().getLineNum());
    }

    @Override
    public String convertToJott() {
        return "return " + exprNode.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        return "return " + exprNode.convertToJava(className) + ";";
    }

    @Override
    public String convertToC() {
        return "return " + exprNode.convertToC() + ";";
    }

    @Override
    public String convertToPython() {
        return "return " + exprNode.convertToPython();
    }
}
