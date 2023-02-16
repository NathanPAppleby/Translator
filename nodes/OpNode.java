package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;


public class OpNode implements JottTree {

    private final String token;

    public OpNode(String token){
        this.token = token;
    }

    static OpNode parseOpNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getToken().equals("+")) {
            tokens.remove(0);
            return new OpNode("+");
        }
        else if (tokens.get(0).getToken().equals("*")) {
            tokens.remove(0);
            return new OpNode("*");
        }
        else if (tokens.get(0).getToken().equals("/")) {
            tokens.remove(0);
            return new OpNode("/");
        }
        else if (tokens.get(0).getToken().equals("-")) {
            tokens.remove(0);
            return new OpNode("-");
        }
        else {
            throw new Exception();
        }
    }

    @Override
    public String convertToJott() {
        return null;
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
