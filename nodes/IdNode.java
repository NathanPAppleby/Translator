package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;


public class IdNode implements ExprNode {

    private final Token token;
    private final boolean isOperation;
    private boolean isInitalized; //in terms of variable id's

    public IdNode(Token token){
        this.token = token;
        this.isOperation = false;
        this.isInitalized = false; //assume false on Default NEED TO SET TO TRUE EVERYWHERE ID INIT
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
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
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
            }
        };
        if(blacklist.contains(this.getIdName())){
            String file = this.getTokenObj().getFilename() + ":" + this.getTokenObj().getLineNum();
            throw new Exception(String.format("Semantic Error:\n\tIllegal ID name \"%s\" used\n\t%s",this.getIdName(), file));
        }
        return true;
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return localVariableSymbolTable.get(this.getIdName()).equals("Boolean");
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return localVariableSymbolTable.get(this.getIdName());
    }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    @Override
    public Token getTokenObj() { return this.token; }

    public void setInitalizedAsTrue() {
        this.isInitalized = true;
    }

    @Override
    public boolean isInitalized() {
        return this.isInitalized;
    }
}
