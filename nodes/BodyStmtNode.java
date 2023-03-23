package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.HashMap;

public interface BodyStmtNode extends JottTree {

    static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens, HashMap<String, String> localVarSymbolTable) throws Exception {
        if (tokens.get(0).getToken().equals("if")) {
            return IfStmtNode.parseIfStmtNode(tokens, localVarSymbolTable);
        }
        else if (tokens.get(0).getToken().equals("while")) {
            return WhileLoopNode.parseWhileLoopNode(tokens, localVarSymbolTable);
        }
        else {
            return StmtNode.parseStmtNode(tokens, localVarSymbolTable);
        }
    }
}
