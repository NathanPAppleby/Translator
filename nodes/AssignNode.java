package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssignNode implements StmtNode {
    // < asmt > -> < type > <id > = < expr > < end_statement > | <id > = < expr > < end_statement >

    private final TypeNode typeNode;
    private final IdNode idNode;
    private final ExprNode exprNode;

    public AssignNode(TypeNode typeNode, IdNode idNode, ExprNode exprNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.exprNode = exprNode;
    }

    static AssignNode parseAssignNode(ArrayList<Token> tokens) throws Exception {

        TypeNode typeNode = null;

        switch (tokens.get(0).getToken()) {
            case "Double", "Integer", "String", "Boolean" -> typeNode = TypeNode.parseTypeNode(tokens);
            default -> {}
        }

        IdNode idNode = IdNode.parseIdNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            Token errToken = tokens.get(0);
            System.err.printf("\nAssign Error:\n\tExpected Assign Token, found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            Token errToken = tokens.get(0);
            System.err.printf("Function Definition Error:\n\tExpected \";\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        return new AssignNode(typeNode, idNode, exprNode);
    }

    @Override
    public String convertToJott() {
        String output = (typeNode == null ? "" : typeNode.convertToJott()) + " " + idNode.convertToJott() + " = " +
                exprNode.convertToJott();

        return output;
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
