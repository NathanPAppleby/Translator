package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

/**
 * A Type of ExprNode, OperationNode follows the format <expr> operator <expr>
 * which can break down into one of the following:
 *     <id> <op> <n_expr>          (ConstantNode OpNode ExprNode)
 *     <num> <op> <n_expr>         (ConstantNode OpNode ExprNode)
 *     <func_call> <op> <n_expr>   (ConstantNode OpNode ExprNode)
 *     <n_expr> <rel_op> <n_expr>  (ExprNode RelOpNode ExprNode)
 */
public class OperationNode extends ExprNode implements JottTree {

    /**
     * OperationNode does not need a constructor will simply return
     * @param tokens .
     * @return .
     * @throws Exception .
     */
    static OperationNode parseOperationNode(ArrayList<Token> tokens) throws Exception {
        //ArrayList<Token> firstExprToken = new ArrayList<>();
        //firstExprToken.add(tokens.get(0));
        //return ExprNode.parseExprNode(firstExprToken);
        return null;
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
