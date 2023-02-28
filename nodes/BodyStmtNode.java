package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class BodyStmtNode implements JottTree {

    static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getToken().equals("if")) {
            return IfStmtNode.parseIfStmtNode(tokens);
        }
        else if (tokens.get(0).getToken().equals("while")) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        }
        else {
            return StmtNode.parseStmtNode(tokens);
        }
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
