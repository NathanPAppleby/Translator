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
            String> localVariableSymbolTable) throws Exception{
        // Validation
        boolean isValidated = this.idNode.validateTree(functionSymbolTable,localVariableSymbolTable) &&
                this.typeNode.validateTree(functionSymbolTable, localVariableSymbolTable);
        // After Validation, add to local variable table
        localVariableSymbolTable.put(this.idNode.getIdName(), this.typeNode.getType());
        return true;
    }

    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott() + ";";
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
    public boolean containsReturn() {
        return false;
    }
}
