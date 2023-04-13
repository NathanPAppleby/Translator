package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Handles <op> & <rel_op>
 */
public class OperatorNode implements JottTree {

    private final Token token;

    public OperatorNode(Token token){
        this.token = token;
    }

    static OperatorNode parseOperatorNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        switch (t.getToken()) { //first four <op>
            case "+", "*", "/", "-", ">", "<", ">=", "<=", "==", "!=" -> {
                tokens.remove(0);
                return new OperatorNode(t);
            }
            default -> {
                throw new Exception(String.format("Operator Error:\n\tExpected Operator Token, found \"%s\"\n\t%s:%d\n", t.getToken(), t.getFilename(), t.getLineNum()));
            }
        }
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        switch (this.token.getToken()) {
            case "+", "*", "/", "-", ">", "<", ">=", "<=", "==", "!=" -> {
                return true;
            }
            default -> {
                String filename = this.token.getFilename() + ":" + this.token.getLineNum();
                throw new Exception("Semantic Error\n\tInvalid operator\n\t"+ filename);
            }
        }
    }

    public Token getToken() {
        return token;
    }
    @Override
    public String convertToJott() {
        return token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.token.getToken();
    }

    @Override
    public String convertToC() {
        return this.token.getToken();
    }

    @Override
    public String convertToPython(int depth) {
        return this.token.getToken();
    }
}
