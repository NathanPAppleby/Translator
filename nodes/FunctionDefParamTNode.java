package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefParamTNode implements JottTree {

    private final FunctionDefParamNode fDefParamNode;

    public FunctionDefParamTNode(FunctionDefParamNode fDefParamTNode) {
        this.fDefParamNode = fDefParamTNode;
    }
    // < func_def_params_t > -> ,<id >: < type > < func_def_params_t > | nothing

    static FunctionDefParamTNode parseFunctionDefParamTNode(ArrayList<Token> tokens) {
        try {
            if (tokens.get(0).getTokenType() != TokenType.COMMA) {
                throw new Exception();
            }
            tokens.remove(0);
            FunctionDefParamNode fDefParamNode = FunctionDefParamNode.parseFunctionDefParamNode(tokens);
            if (fDefParamNode == null) {
                throw new Exception();
            }
            return new FunctionDefParamTNode(fDefParamNode);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String convertToJott() {
        return "," + fDefParamNode.convertToJott();
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
