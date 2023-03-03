package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

/**
 * An Expression Node <expr>, representing an <n_expr>, <b_expr>, or an <s_expr>,
 * Can Break Down to 1 of 4 things
 *     ConstantNode (see ConstantNode)
 *     FuncCallNode <func_call>
 *     OperationNode
 *         - <expr> operator <expr> (currently: <expr> OpNode <expr>)
 *     IdNode <id/keyword>
 */
public interface ExprNode extends JottTree {

    //LOOK AT FIRST TOKEN AND DETERMINE WHAT     I COULD BE,

    static ExprNode parseExprNode(ArrayList<Token> tokens) throws Exception {
        //if we have a <bool> or a <str_literal> (both of TokenType String)
        //we can return that right away bc it will not
        //directly exist as one of our 4 potential operation nodes
        if (tokens.get(0).getTokenType().equals(TokenType.STRING)) {
            return ConstantNode.parseConstantNode(tokens);
        }
        ExprNode expressionNode = null; //will be <id> or <num>
        if ( tokens.get(0).getTokenType().equals(TokenType.NUMBER) ) {
            expressionNode = ConstantNode.parseConstantNode(tokens);
        } else if ( tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) ) {
            //MUST Check for func call before id because func call starts with id
            if ( tokens.get(1).getTokenType().equals(TokenType.L_BRACKET) ) {
                expressionNode = FuncCallNode.parseFuncCallNode(tokens);
            } else {
                expressionNode = IdNode.parseIdNode(tokens);
            }
        }
        if (tokens.get(0).getTokenType().equals(TokenType.MATH_OP) ||
                tokens.get(0).getTokenType().equals(TokenType.REL_OP)) {
            OperatorNode op = OperatorNode.parseOperatorNode(tokens);
            ExprNode right = ExprNode.parseExprNode(tokens);
            return new OperationNode(expressionNode, op, right);
        } else { // we have a number, but no op or rel op follows, so our expression is simply a number
            return expressionNode;
        }
    }

}
