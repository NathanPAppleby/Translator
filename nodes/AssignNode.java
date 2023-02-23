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

    public AssignNode(TypeNode typeNode, IdNode idNode, Token assignToken, ExprNode exprNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.assignToken = assignToken;
        this.exprNode = exprNode;
    }

    static AssignNode parseAssignNode(ArrayList<Token> tokens) throws Exception {
        TypeNode typeNode = null;
        try {
            typeNode = TypeNode.parseTypeNode(tokens);
        }
        catch (Exception ignored) { }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            throw new Exception();
        }
        Token assignToken = tokens.remove(0);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        if(tokens.remove(0).equals(";")) {
            return new AssignNode(typeNode, idNode, assignToken, exprNode);
        }
        else{
            throw new Exception();
        }

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
        sb.append(";");
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
