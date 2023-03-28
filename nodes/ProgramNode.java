package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;
    private HashMap<String, FunctionDef> functionSymbolTable;

    public ProgramNode(FunctionListNode functionListNode, HashMap<String, FunctionDef> functionSymbolTable){
        this.functionListNode = functionListNode;
        this.functionSymbolTable = functionSymbolTable;
    }

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        Token lastToken = tokens.get(tokens.size()-1);
        try {
            HashMap<String, FunctionDef> functionSymbolTable = new HashMap<>();
            FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens, functionSymbolTable);
            return new ProgramNode(functionListNode, functionSymbolTable);
        }
        catch (Exception e) {
            if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                System.err.println(String.format("General Tokens Error: \n\tPrematurely ran out of tokens in parser.\n\t%s:%d\n", lastToken.getFilename(), lastToken.getLineNum()));
            }
            else {
                System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    public String convertToJott() {
        return this.functionListNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return this.functionListNode.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.functionListNode.convertToC();
    }

    @Override
    public String convertToPython() {
        return this.functionListNode.convertToPython();
    }

    @Override
    public boolean validateTree(HashMap<String, String> localVariableSymbolTable) {
        return false;
    }
}
