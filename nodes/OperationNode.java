package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

/**
 * TODO THIS CLASSES FUNCTIONALITY is treating both rel_op and op as an OpNode-
 *  can we combine OpNode and RelOpNode into a single operator node class
 * A Type of ExprNode, OperationNode follows the format <expr> operator <expr>
 * which can break down into one of the following:
 *     <id> <op> <n_expr>          (ConstantNode OpNode ExprNode)
 *     <num> <op> <n_expr>         (ConstantNode OpNode ExprNode)
 *     <func_call> <op> <n_expr>   (ConstantNode OpNode ExprNode)
 *     <n_expr> <rel_op> <n_expr>  (ExprNode RelOpNode ExprNode)
 */
public class OperationNode extends ExprNode implements JottTree {
    private final ExprNode left;
    private final OpNode middle;
    private final ExprNode right;

    OperationNode(ExprNode leftNode, OpNode middleNode, ExprNode rightNode) {
        this.left = leftNode;
        this.middle = middleNode;
        this.right = rightNode;
    }

    /**
     * @param tokens the mutable array of all remaining tokens
     * @return instance of Operation node which has 3 children
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
        return this.left.convertToJava(className) + this.middle.convertToJava(className) +
                this.right.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.left.convertToC() + this.middle.convertToC() + this.right.convertToC();
    }

    @Override
    public String convertToPython() {
        return this.left.convertToPython() + this.middle.convertToPython() + this.right.convertToPython();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
