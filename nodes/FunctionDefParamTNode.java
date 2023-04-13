package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

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
            throw new Exception(
                    String.format("Function Definition Parameter Error:\n\tExpected \":\", found \"%s\"\n\t%s:%d\n",
                            errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        FunctionDefParamTNode fDefParamTNode = FunctionDefParamTNode.parseFunctionDefParamTNode(tokens);

        return new FunctionDefParamTNode(idNode, typeNode, fDefParamTNode);
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        return super.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    @Override
    public String convertToJott() {
        return super.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return super.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return super.convertToC();
    }

    @Override
    public String convertToPython(int depth) {
        return super.convertToPython(depth);
    }
}
