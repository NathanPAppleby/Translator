package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssignNode implements JottTree {
    // < asmt > -> < type > <id > = < expr > < end_statement > | <id > = < expr > < end_statement >

    private final TypeNode typeNode;
    private final IdNode idNode;
    private final Token assignToken;
    private final ExprNode exprNode;
    private final EndStmtNode endStmtNode;

    public AssignNode(TypeNode typeNode, IdNode idNode, Token assignToken, ExprNode exprNode, EndStmtNode endStmtNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.assignToken = assignToken;
        this.exprNode = exprNode;
        this.endStmtNode = endStmtNode;
    }

    static AssignNode parseAssignNode(ArrayList<Token> tokens) throws Exception {
        Token token1 = tokens.get(1);
        Token token2 = tokens.get(2);
        TypeNode typeNode = null;
        if (token2.getTokenType() == TokenType.ASSIGN) {
            typeNode = TypeNode.parseTypeNode(tokens);
        }
        else if (token1.getTokenType() != TokenType.ASSIGN) {
            throw new Exception();
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        Token assignToken = tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        EndStmtNode endStmtNode = EndStmtNode.parseEndStmtNode(tokens);
        return new AssignNode(typeNode, idNode, assignToken, exprNode, endStmtNode);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        if (this.typeNode != null) {
            sb.append(this.typeNode.convertToJott()).append(" ");
        }
        sb.append(this.idNode.convertToJott()).append(" ");
        sb.append(this.assignToken.getToken()).append(" ");
        sb.append(this.exprNode.convertToJott());
        sb.append(this.endStmtNode.convertToJott());
        return sb.toString();
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
