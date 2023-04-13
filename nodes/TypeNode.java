package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeNode implements JottTree {

    private final Token token;

    public TypeNode(Token token) {
        this.token = token;
    }

    static TypeNode parseTypeNode(ArrayList<Token> tokens) throws Exception {
        switch (tokens.get(0).getToken()) {
            case "Double", "Integer", "String", "Boolean" -> {return new TypeNode(tokens.remove(0));}
            default -> throw new Exception(String.format("Type Error:\n\tInvalid type \"%s\"\n\t%s:%d\n",
                    tokens.get(0).getToken(), tokens.get(0).getFilename(), tokens.get(0).getLineNum()));
        }
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        return true;
    }

    public String getType(){
        return this.token.getToken();
    }

    @Override
    public String convertToJott() {
        return token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return switch (token.getToken()) {
            case "Double" -> "double";
            case "Integer" -> "int";
            case "Boolean" -> "boolean";
            default -> token.getToken();
        };
    }

    @Override
    public String convertToC() {
        return switch (token.getToken()) {
            case "Double" -> "double";
            case "Integer" -> "int";
            case "String" -> "char*";
            case "Boolean" -> "bool";
            default -> token.getToken();
        };
    }

    @Override
    public String convertToPython(int depth) {
        return switch (token.getToken()) {
            case "Double" -> "float";
            case "Integer" -> "int";
            case "String" -> "str";
            case "Boolean" -> "bool";
            default -> token.getToken();
        };
    }
}
