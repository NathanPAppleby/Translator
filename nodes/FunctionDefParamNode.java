package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefParamNode implements JottTree {
    // < func_def_params > -> <id >: < type > < function_def_params_t > | nothing

    private final IdNode idNode;
    private final TypeNode typeNode;
    private final FunctionDefParamTNode fDefParamTNode;

    public FunctionDefParamNode(IdNode idNode, TypeNode typeNode, FunctionDefParamTNode fDefParamTNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.fDefParamTNode = fDefParamTNode;
    }

    static FunctionDefParamNode parseFunctionDefParamNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return null;
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (tokens.get(0).getTokenType() != TokenType.COLON) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Function Definition Parameter Error:\n\tExpected \":\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        FunctionDefParamTNode fDefParamTNode = FunctionDefParamTNode.parseFunctionDefParamTNode(tokens);
        return new FunctionDefParamNode(idNode, typeNode, fDefParamTNode);
    }

    @Override
    public String convertToJott() {
        return idNode.convertToJott()+ ":" + typeNode.convertToJott() +
                (fDefParamTNode == null ? "" : fDefParamTNode.convertToJott());
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
