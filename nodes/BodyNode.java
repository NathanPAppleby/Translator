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
        bodyStmtNodes = getbodystmtnodes(tokens, bodyStmtNodes);
        try {
            returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);
        }
        catch (Exception ignored) {}
        return new BodyNode(bodyStmtNodes, returnStmtNode);
    }

    static ArrayList<BodyStmtNode> getbodystmtnodes(ArrayList<Token> tokens, ArrayList<BodyStmtNode> bodyStmtNodes) {
        try {
            bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            getbodystmtnodes(tokens, bodyStmtNodes);
        }
        catch (Exception ignored) {}
        return bodyStmtNodes;
    }

    @Override
    public String convertToJott() {
        return null;
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
