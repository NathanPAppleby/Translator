package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements StmtNode {
    // < var_dec > -> < type > <id > < end_statement >

    private final TypeNode typeNode;
    private final IdNode idNode;

    public VarDecNode(TypeNode typeNode, IdNode idNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
    }

    static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws Exception {
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new Exception();
        }
        tokens.remove(0);
        return new VarDecNode(typeNode, idNode);
    }

    @Override
    public String convertToJott() {
        return typeNode.convertToJott() + " " + idNode.convertToJott() + ";";
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
