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
        if (!tokens.get(0).getToken().equals("return")) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Return Error:\nReceived token \"%s\" expected \"return\".\n%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        if(tokens.get(0).getToken().equals(";")) {
            tokens.remove(0);
            return new ReturnStmtNode(exprNode);
        }
        else{
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Return Error:\n\tExpected \";\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
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
