package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class TypeNode implements JottTree {

    private final Token token;

    public TypeNode(Token token) {
        this.token = token;
    }

    static TypeNode parseTypeNode(ArrayList<Token> tokens) throws Exception {
        return switch (tokens.get(0).getToken()) {
            case "Double", "Integer", "String", "Boolean" -> new TypeNode(tokens.remove(0));
            default -> throw new Exception(String.format("Type Error:\n\tInvalid type \"%s\"\n\t%s:%d\n",
                    tokens.get(0).getToken(), tokens.get(0).getFilename(), tokens.get(0).getLineNum()));
        };
    }

    @Override
    public String convertToJott() {
        return token.getToken();
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
