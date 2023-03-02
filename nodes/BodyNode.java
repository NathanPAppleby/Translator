package nodes;

import provided.JottTree;
import provided.Token;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BodyNode implements JottTree {
    private final ArrayList<BodyStmtNode> bodyStmtNodes;
    private final ReturnStmtNode returnStmtNode;
    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
    }
    static BodyNode parseBodyNode(ArrayList<Token> tokens) {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        ReturnStmtNode returnStmtNode = null;
        bodyStmtNodes = getBodyStmtNodes(tokens, bodyStmtNodes);
        try {
            returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);
        }
        catch (Exception ignored) {}
        return new BodyNode(bodyStmtNodes, returnStmtNode);
    }

    static ArrayList<BodyStmtNode> getBodyStmtNodes(ArrayList<Token> tokens, ArrayList<BodyStmtNode> bodyStmtNodes) {
        try {
            bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            getBodyStmtNodes(tokens, bodyStmtNodes);
        }
        catch (Exception ignored) {}
        return bodyStmtNodes;
    }

    @Override
    public String convertToJott() {
        StringBuilder output = new StringBuilder();
        for(BodyStmtNode bodyStmtNode: this.bodyStmtNodes){
            output.append(bodyStmtNode.convertToJott()).append("\n");
        };
        if(this.returnStmtNode != null){
            output.append(this.returnStmtNode.convertToJott()).append("\n");
        }
        return output.toString();
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
    public boolean validateTree() {
        return false;
    }
}
