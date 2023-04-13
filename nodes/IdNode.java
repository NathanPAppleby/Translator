package nodes;

import org.w3c.dom.ls.LSOutput;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;


public class IdNode implements ExprNode {

    private final Token token;
    private final boolean isOperation;

    public IdNode(Token token){
        this.token = token;
        this.isOperation = false;
    }

    static IdNode parseIdNode(ArrayList<Token> tokens) throws Exception {
        Token t = tokens.get(0);
        if (t.getTokenType() != TokenType.ID_KEYWORD) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("ID/Keyword Error:\n\tExpected id/keyword token, found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        return new IdNode(t);
    }

    public String getIdName() {
        return token.getToken();
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
        return token.getToken();
    }

    @Override
    public String convertToPython(int depth) {
        return token.getToken();
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        ArrayList<String> blacklist = new ArrayList<>() {
            {
                add("while");
                add("if");
                add("else");
                add("elseif");
                add("Integer");
                add("Boolean");
                add("Double");
                add("String");
                add("Void");
                add("def");
                add("return");
                add("print");
                add("concat");
                add("length");
            }
        };
        if(blacklist.contains(this.getIdName())){
            String file = this.getTokenObj().getFilename() + ":" + this.getTokenObj().getLineNum();
            throw new Exception(String.format("Semantic Error:\n\tIllegal ID name \"%s\" used\n\t%s",this.getIdName(), file));
        }
        return true;
    }

    @Override
    public boolean isInitialized(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) {
        //make sure declated first
        if (!localVariableSymbolTable.containsKey(this.getIdName())) {
            try {
                throw new Exception(String.format("Semantic Error:\n\tVariable \"%s\" is undefined " +
                                "\n\t%s:%d", this.token.getToken(), this.token.getFilename(),
                        this.token.getLineNum()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } //if declared make sure initalized
        return localVariableSymbolTable.get(getIdName()).get(1).equals("True");
    }


    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) {
        return localVariableSymbolTable.get(this.getIdName()).get(0).contains("Boolean");
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) {
        return localVariableSymbolTable.get(this.getIdName()).get(0);
    }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    @Override
    public Token getTokenObj() { return this.token; }

}
