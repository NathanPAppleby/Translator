package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public interface StmtNode extends BodyStmtNode, JottTree {

    static StmtNode parseStmtNode(ArrayList<Token> tokens) throws Exception {
        // firsts for both varDec and Assign are from <type>
        if (tokens.get(0).getToken().equals("Double") ||
                tokens.get(0).getToken().equals("Integer") ||
                tokens.get(0).getToken().equals("String") ||
                tokens.get(0).getToken().equals("Boolean")){
            if(tokens.get(2).getToken().equals(";")){
                return VarDecNode.parseVarDecNode(tokens);
            }
            else{
                return AssignNode.parseAssignNode(tokens);
            }
        }
        else{
            //if Assigns first isn't from type, it should begin w id followed by '='
            if(tokens.get(1).getToken().equals("=")){
                return AssignNode.parseAssignNode(tokens);
            }
            else{
                return FuncCallNode.parseFuncCallNode(tokens);
            }
        }
    }
}
