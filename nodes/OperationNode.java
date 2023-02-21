package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

/**
 * A Type of ExprNode, OperationNode follows the format:
 *     <expr> operator <expr>
 */
public class OperationNode extends ExprNode implements JottTree {

    /**
     * OperationNode does not need a constructor will simply return
     * @param tokens .
     * @return .
     * @throws Exception .
     */
    static OperationNode OperationNode(ArrayList<Token> tokens) throws Exception { return null; }

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
