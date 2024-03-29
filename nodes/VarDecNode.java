package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class VarDecNode implements StmtNode {
    // < var_dec > -> < type > <id > < end_statement >

    private final TypeNode typeNode;
    private final IdNode idNode;

    public VarDecNode(TypeNode typeNode, IdNode idNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
    }

    static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws Exception {
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Variable Declaration Error:\n\tReceived token \"%s\" expected \";\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        return new VarDecNode(typeNode, idNode);
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String,
            ArrayList<String>> localVariableSymbolTable) throws Exception{
        // Validation
        boolean isValidated = this.idNode.validateTree(functionSymbolTable,localVariableSymbolTable) &&
                this.typeNode.validateTree(functionSymbolTable, localVariableSymbolTable);
        // After Validation, add to local variable table
        localVariableSymbolTable.put(this.idNode.getIdName(), new ArrayList<>());
        localVariableSymbolTable.get(this.idNode.getIdName()).add(this.typeNode.getType());
        localVariableSymbolTable.get(this.idNode.getIdName()).add("False");
        return true;
    }

    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return this.typeNode.convertToJava(className) + " " + this.idNode.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.typeNode.convertToC() + " " + this.idNode.convertToC();
    }

    @Override
    public String convertToPython(int depth) {
        // not used in python
        return "";
    }


    @Override
    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        return false;
    }

    @Override
    public String getLocation() {
        return String.format("%s:%s",this.idNode.getTokenObj().getFilename(), this.idNode.getTokenObj().getLineNum());
    }
}
