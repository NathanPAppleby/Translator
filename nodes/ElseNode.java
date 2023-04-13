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

    static ElseNode parseElseNode(ArrayList<Token> tokens) throws Exception{
        if (!tokens.get(0).getToken().equals("else")) {
            return null;
        }
        tokens.remove(0);

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            Token errToken = tokens.get(0);
            throw new Exception(
                    String.format("Else Error:\n\tReceived token \"%s\" expected \"{\".\n\t%s:%d\n",
                            errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            Token errToken = tokens.get(0);
            throw new Exception(
                    String.format("Else Error:\n\tReceived token \"%s\" expected \"}\".\n\t%s:%d\n",
                            errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        return new ElseNode(bodyNode);
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        return bodyNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable,
                                  HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        return this.bodyNode.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);
    }

    @Override
    public String convertToJott() {
        return "else {" + bodyNode.convertToJott() + "}";
    }

    @Override
    public String convertToJava(String className) {
        return "else {\n\t" +
                this.bodyNode.convertToJava(className) +
                "\n}\n";
    }

    @Override
    public String convertToC() {
        return "else {\n\t" +
                this.bodyNode.convertToC() +
                "\n}\n";
    }

    @Override
    public String convertToPython(int depth) {
        return "\t".repeat(depth) +
                "else:\n" +
                this.bodyNode.convertToPython(depth + 1);
    }
}
