package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionListNode implements JottTree {
    private final ArrayList<FunctionDefNode> functionDefNodes;
    public FunctionListNode(ArrayList<FunctionDefNode> functionDefNodes) {
        this.functionDefNodes = functionDefNodes;
    }

    static FunctionListNode parseFunctionListNode(ArrayList<Token> tokens, HashMap<String, FunctionDef> functionSymbolTable) throws Exception {
        ArrayList<FunctionDefNode> functionDefNodes = new ArrayList<>();
        getFunctionDefNodes(tokens, functionDefNodes, functionSymbolTable);
        return new FunctionListNode(functionDefNodes);
    }

    static void getFunctionDefNodes(ArrayList<Token> tokens, ArrayList<FunctionDefNode> functionDefNodes, HashMap<String, FunctionDef> functionSymbolTable) throws Exception {
        if(!tokens.isEmpty()) {
            FunctionDefNode fn = FunctionDefNode.parseFunctionDefNode(tokens, functionSymbolTable);
            functionSymbolTable.put(fn.getFunctionName(), FunctionDef.buildFunctionDef(fn)); // TODO: Need to check to make sure function does not already exist
            functionDefNodes.add(fn);
            getFunctionDefNodes(tokens, functionDefNodes, functionSymbolTable);
        }
    }
    @Override
    public String convertToJott() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToJott()).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToJava(className)).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToC()).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToPython()).append("\n");
        }
        return output.toString();
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return false;
    }
}
