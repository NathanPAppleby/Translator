package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionListNode implements JottTree {
    private final ArrayList<FunctionDefNode> functionDefNodes;
    public FunctionListNode(ArrayList<FunctionDefNode> functionDefNodes) {
        this.functionDefNodes = functionDefNodes;
    }

    static FunctionListNode parseFunctionListNode(ArrayList<Token> tokens) throws Exception {
        ArrayList<FunctionDefNode> functionDefNodes = new ArrayList<>();
        getFunctionDefNodes(tokens, functionDefNodes);
        return new FunctionListNode(functionDefNodes);
    }

    static void getFunctionDefNodes(ArrayList<Token> tokens, ArrayList<FunctionDefNode> functionDefNodes) throws Exception {
        if(!tokens.isEmpty()) {
            functionDefNodes.add(FunctionDefNode.parseFunctionDefNode(tokens));
            getFunctionDefNodes(tokens, functionDefNodes);
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
    public boolean validateTree() {
        return false;
    }
}
