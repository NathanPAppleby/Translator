package nodes;

import provided.JottTree;
import provided.Token;


import java.util.ArrayList;

import static nodes.BodyStmtNode.parseBodyStmtNode;

public class BodyNode implements JottTree {
    public static BodyNode parseBodyNode(ArrayList<Token> tokens) { return null; }

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
