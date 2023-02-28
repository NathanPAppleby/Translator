package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;

    public ProgramNode(FunctionListNode functionListNode){this.functionListNode = functionListNode;}
    static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        try {
            FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
            return new ProgramNode(functionListNode);
        }
        catch (IndexOutOfBoundsException e){
            throw new Exception();
        }
    }

    @Override
    public String convertToJott() {
        return this.functionListNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return this.functionListNode.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.functionListNode.convertToC();
    }

    @Override
    public String convertToPython() {
        return this.functionListNode.convertToPython();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
