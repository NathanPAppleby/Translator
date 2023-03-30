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

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        HashMap<String, FunctionDef> newFunctionSymbolTable = new HashMap<>();
        boolean isValidated = true;
        try {
            functionListNode.validateTree(newFunctionSymbolTable, null);

            // Every file must have a "main" function.
            if (!newFunctionSymbolTable.containsKey("main")) {
                throw new Exception("Semantic Error:\nMissing main function definition\nfilename.jott\n");
            } else {
                // main function must return "Void" or "Integer" types
                FunctionDef fd = newFunctionSymbolTable.get("main");
                if (!fd.returnType.equals("Void") && !fd.returnType.equals("Integer")) {
                    return false;
                }
                //TODO: params in main function?
            }
            return true;
        }
        catch (Exception e) {
            isValidated = false;
            System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            return false;
        }
    }
}
