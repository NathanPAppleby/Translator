package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionDefParamNode implements JottTree {
    // < func_def_params > -> <id >: < type > < function_def_params_t > | nothing

    private final IdNode idNode;
    private final TypeNode typeNode;
    private final FunctionDefParamTNode fDefParamTNode;

    public FunctionDefParamNode(IdNode idNode, TypeNode typeNode, FunctionDefParamTNode fDefParamTNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.fDefParamTNode = fDefParamTNode;
    }

    static FunctionDefParamNode parseFunctionDefParamNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return null;
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.COLON) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Parameter Error:\n\tExpected \":\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        FunctionDefParamTNode fDefParamTNode = FunctionDefParamTNode.parseFunctionDefParamTNode(tokens);
        return new FunctionDefParamNode(idNode, typeNode, fDefParamTNode);
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        addParamToLocalVarTable(localVariableSymbolTable);
        return this.fDefParamTNode == null ||
                this.fDefParamTNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    public String getParamName(){
        return this.idNode.getIdName();
    }

    public String getReturnType(){
        return this.typeNode.getType();
    }

    public FunctionDefParamNode getTail(){
        return this.fDefParamTNode;
    }

    @Override
    public String convertToJott() {
        return idNode.convertToJott()+ ":" + typeNode.convertToJott() +
                (fDefParamTNode == null ? "" : ", " + fDefParamTNode.convertToJott());
    }

    @Override
    public String convertToJava(String className) {
        return this.typeNode.convertToJava(className) + " " + this.idNode.convertToJava(className) +
                (this.fDefParamTNode == null ? "" : ", "+this.fDefParamTNode.convertToJava(className));
    }

    @Override
    public String convertToC() {
        return this.typeNode.convertToC() + " " + this.idNode.convertToC() +
                (this.fDefParamTNode == null ? "" : ", "+this.fDefParamTNode.convertToC());
    }

    @Override
    public String convertToPython(int depth) {
        return this.idNode.convertToPython(depth) +
                (this.fDefParamTNode == null ? "" : ", " + this.fDefParamTNode.convertToPython(depth));
    }

    public void addParamToLocalVarTable( HashMap<String, ArrayList<String>> localVariableSymbolTable ) {
        localVariableSymbolTable.put(this.idNode.getIdName(), new ArrayList<>());
        localVariableSymbolTable.get(this.idNode.getIdName()).add(this.typeNode.getType());
        localVariableSymbolTable.get(this.idNode.getIdName()).add("True");
    }
}
