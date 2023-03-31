package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;

    public ProgramNode(FunctionListNode functionListNode){
        this.functionListNode = functionListNode;
    }

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        Token lastToken = tokens.get(tokens.size()-1);
        try {
            FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
            return new ProgramNode(functionListNode);
        }
        catch (Exception e) {
            if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                System.err.printf("General Tokens Error: \n\tPrematurely ran out of tokens in parser.\n\t%s:%d\n%n", lastToken.getFilename(), lastToken.getLineNum());
            }
            else {
                System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            }
            return null;
        }
    }

    public boolean validateTree() throws Exception {
        HashMap<String, FunctionDef> newFunctionSymbolTable = new HashMap<>();
        try {
            functionListNode.validateTree(newFunctionSymbolTable, null);
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            return false;
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
}
