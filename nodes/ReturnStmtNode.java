package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ReturnStmtNode implements JottTree {
    private final ExprNode exprNode;

    public ReturnStmtNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws Exception {
        Token returnToken = tokens.remove(0);
        if(returnToken.getToken().equals("return")){
            ExprNode exprNode = ExprNode.parseExprNode(tokens);
            EndStmtNode.parseEndStmtNode(tokens);
            return new ReturnStmtNode(exprNode);
        }
        else{
            throw new Exception();
        }
    }

    @Override
    public String convertToJott() {
        return "return " + exprNode.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        return "return " + exprNode.convertToJava(className) + ";";
    }

    @Override
    public String convertToC() {
        return "return " + exprNode.convertToC() + ";";
    }

    @Override
    public String convertToPython() {
        return "return " + exprNode.convertToPython();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
