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

    //private final ExprNode theNode;
    /*
    public ExprNode(ExprNode node) {
        this.theNode = node;
    }

     */

    //each token in arrayList of Tokens knows its Type from tokenizing logic
    static ExprNode parseExprNode(ArrayList<Token> tokens) throws Exception {
        //if next token is constant Node- need to cut this down with a simple ask if constant node somehow
        if (tokens.get(0).getTokenType().equals(TokenType.STRING) ||
                tokens.get(0).getTokenType().equals(TokenType.NUMBER) ||
                tokens.get(0).getToken().equals("true") || tokens.get(0).getToken().equals("false"))
        {
            return ConstantNode.parseConstantNode(tokens);
            //return new ExprNode(ConstantNode.parseConstantNode(tokens));
        }
        else if (tokens.size() > 1) { //this might be pointless, tokens passed in will always be greater
            // if Function Call Node. MUST Check for func call before id because func call starts with id
            if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) &&
                    tokens.get(1).getTokenType().equals(TokenType.L_BRACKET)) {
                return FuncCallNode.parseFuncCallNode(tokens);
            }
            //if Operation Node MUST also check for func call before id because Operation can begin with id
            if (tokens.get(1).getTokenType().equals(TokenType.MATH_OP) ||
                    tokens.get(1).getTokenType().equals(TokenType.REL_OP)) {
                return OperationNode.parseOperationNode(tokens);
            }
        } else if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) { // if next token is id
            return IdNode.parseIdNode(tokens);
        } else {
            throw new Exception();
        }
        return null;
    }

}
