package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FunctionListNode implements JottTree {
    private final FunctionDefNode functionDefNode;
    private final FunctionListNode next;

    public FunctionListNode(FunctionDefNode functionDefNode, FunctionListNode next) {
        this.functionDefNode = functionDefNode;
        this.next = next;
    }

    static FunctionListNode parseFunctionListNode(ArrayList<Token> tokens) throws Exception {
        if(!tokens.isEmpty()){
            FunctionDefNode functionDefNode = FunctionDefNode.parseFunctionDefNode(tokens);
            FunctionListNode next = FunctionListNode.parseFunctionListNode(tokens);
            return new FunctionListNode(functionDefNode, next);
        }
        else {
            return null;
        }
    }

    @Override
    public String convertToJott() {
        String output = this.functionDefNode.convertToJott();
        if(this.next != null){
            output += "\n" + this.next.convertToJott();
        }
        return output;
    }

    @Override
    public String convertToJava(String className) {
        String output = this.functionDefNode.convertToJava(className);
        if(this.next != null){
            output += "\n" + this.next.convertToJava(className);
        }
        return output;
    }

    @Override
    public String convertToC() {
        String output = this.functionDefNode.convertToC();
        if(this.next != null){
            output += "\n" + this.next.convertToC();
        }
        return output;
    }

    @Override
    public String convertToPython() {
        String output = this.functionDefNode.convertToPython();
        if(this.next != null){
            output += "\n" + this.next.convertToPython();
        }
        return output;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
