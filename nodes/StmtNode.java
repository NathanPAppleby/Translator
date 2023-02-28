package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class StmtNode extends BodyStmtNode implements JottTree {


    static StmtNode parseStmtNode(ArrayList<Token> tokens) throws Exception {
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
            if(tokens.get(1).getToken().equals("=")){
                return AssignNode.parseAssignNode(tokens);
            }
            else{
                return FuncCallNode.parseFuncCallNode(tokens);
            }
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
