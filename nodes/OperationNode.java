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
    ExprNode left;
    OpNode middle;
    ExprNode right;

    OperationNode(ExprNode leftNode, OpNode middleNode, ExprNode rightNode) {
        this.left = leftNode;
        this.middle = middleNode;
        this.right = rightNode;
    }

    /**
     * @param tokens .
     * @return .
     * @throws Exception .
     */
    static OperationNode parseOperationNode(ArrayList<Token> tokens) throws Exception {
        ExprNode leftExpr = ExprNode.parseExprNode(tokens);
        OpNode operation = OpNode.parseOpNode(tokens);
        ExprNode rightExpr = ExprNode.parseExprNode(tokens);
        return new OperationNode(leftExpr, operation, rightExpr);
    }

    @Override
    public String convertToJott() {

        return this.left.convertToJott() + this.middle.convertToJott() + this.right.convertToJott();
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
