package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.HashMap;

public interface BodyStmtNode extends JottTree {

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

    public boolean containsReturn();
}
