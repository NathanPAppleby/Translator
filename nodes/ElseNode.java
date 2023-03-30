package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ElseNode implements JottTree {

    // < else > -> else { < body } | nothing

    private final BodyNode bodyNode;

    public ElseNode(BodyNode bodyNode) {
        this.bodyNode = bodyNode;
    }

    static ElseNode parseElseNode(ArrayList<Token> tokens, HashMap<String, String> localVarSymbolTable) throws Exception{
        if (!tokens.get(0).getToken().equals("else")) {
            return null;
        }
        tokens.remove(0);

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Else Error:\n\tReceived token \"%s\" expected \"{\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        BodyNode bodyNode = BodyNode.parseBodyNode(tokens, localVarSymbolTable);

        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Else Error:\n\tReceived token \"%s\" expected \"}\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        return new ElseNode(bodyNode);
    }

    @Override
    public String convertToJott() {
        return "else {" + bodyNode.convertToJott() + "}";
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
        return bodyNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }
}
