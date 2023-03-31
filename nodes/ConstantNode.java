package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A Constant Node will handle any of the following
 *     Integer or Double (<num>)
 *     String as defined in DFA (<str_literal>)
 *     Boolean (<bool>)
 */
public class ConstantNode implements ExprNode {

    private final Token token;
    private final boolean isBoolean;

    private final boolean isOperation;

    // "Double" | "Integer" | "String" | "Boolean"
    private final String jottType;

    /**
     * Constructor initalize's the actual token rather than the string representation of the token
     * because when we get to later phases and need to report an error, our token knows which
     * line it belongs too
     * @param token
     */
    public ConstantNode(Token token, String jottTypeStr){
        this.token = token;
        this.jottType = jottTypeStr;
        isBoolean = token.getToken().equals("True") || token.getToken().equals("False");
        this.isOperation = false;
    }

    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return this.jottType;
    }

    public Token getTokenObj() {
        return this.token;
    }

    static ConstantNode parseConstantNode(ArrayList<Token> tokens) throws Exception {

        if (tokens.get(0).getTokenType().equals(TokenType.STRING)) {
            return new ConstantNode(tokens.remove(0), "String");
        } else if (tokens.get(0).getTokenType().equals(TokenType.NUMBER)) {
            if (tokens.get(0).getToken().contains(".")) { //we have a double
                return new ConstantNode(tokens.remove(0), "Double");
            } else {
                return new ConstantNode(tokens.remove(0), "Integer");
            }
        } else if (tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False")) {
            return new ConstantNode(tokens.remove(0), "Boolean");
        } else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Invalid Constant Error:\n\t\"%s\" is an invalid constant\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }

        /*
        if (tokens.get(0).getTokenType().equals(TokenType.STRING) ||
                tokens.get(0).getTokenType().equals(TokenType.NUMBER) ||
                tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False")) {
            return new ConstantNode(tokens.remove(0));
        } else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Invalid Constant Error:\n\t\"%s\" is an invalid constant\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
         */
    }

    @Override
    public String convertToJott() {
        return token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return token.getToken();
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        if (token.getToken().equals("true")) {
            return "True";
        } else if (token.getToken().equals("false")) {
            return "False";
        } else {
           return token.getToken();
        }
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return isBoolean;
    }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        //already validated in parsing
        return true;
    }

    @Override
    public boolean isInitalized() {return true;}
}
