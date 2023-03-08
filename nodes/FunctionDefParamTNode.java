package nodes;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefParamTNode extends FunctionDefParamNode {
    public FunctionDefParamTNode(IdNode idNode, TypeNode typeNode, FunctionDefParamTNode fDefParamTNode) {
        super(idNode, typeNode, fDefParamTNode);
    }

    // < func_def_params_t > -> ,<id >: < type > < func_def_params_t > | nothing

    static FunctionDefParamTNode parseFunctionDefParamTNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() != TokenType.COMMA) {
            return null;
        }
        tokens.remove(0);

        IdNode idNode = IdNode.parseIdNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.COLON) {
            Token errToken = tokens.get(0);
            System.err.printf("\nFunction Definition Parameter Error:\n\tExpected \":\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum());
            throw new Exception();
        }
        tokens.remove(0);

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        FunctionDefParamTNode fDefParamTNode = FunctionDefParamTNode.parseFunctionDefParamTNode(tokens);

        return new FunctionDefParamTNode(idNode, typeNode, fDefParamTNode);
    }

    @Override
    public String convertToJott() {
        return "," + super.convertToJott();
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
