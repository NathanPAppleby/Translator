package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

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
    //DO NOT HANDLE CASE WHERE 4 - + 3 which is technically valid

    static ExprNode parseExprNode(ArrayList<Token> tokens) throws Exception {
        //if we have a <bool> or a <str_literal> (both of TokenType String)
        //we can return that right away bc it will not
        //directly exist as one of our 4 potential operation nodes
        if (tokens.get(0).getTokenType().equals(TokenType.STRING) || tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False")) {
            return ConstantNode.parseConstantNode(tokens);
        }
        ExprNode expressionNode = null; //will be <id> or <num>
        // Negative Number check
        if (tokens.get(0).getToken().equals("-") && tokens.get(1).getTokenType().equals(TokenType.NUMBER)) {
            tokens.remove(0); //remove negative token
            Token num = tokens.remove(0); //remove num
            Token negativeNum = new Token("-" + num.getToken(), num.getFilename(), num.getLineNum(), num.getTokenType());
            tokens.add(0, negativeNum); //Add negative num back in
            expressionNode = ConstantNode.parseConstantNode(tokens);
        }
        // Positive Number check
        else if ( tokens.get(0).getTokenType().equals(TokenType.NUMBER) ) {
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
                tokens.get(0).getTokenType().equals(TokenType.REL_OP)) { //Need to check if negative sign or math op.
            OperatorNode op = OperatorNode.parseOperatorNode(tokens);
            ExprNode right = ExprNode.parseExprNode(tokens);
            //this is for handling case where directly after '=' we have '-''-', the lone minus case.
            //we should not be returning an operation node if we don't have a complete operation node,
            if(expressionNode == null){
               Token errToken = tokens.get(0);
                throw new Exception(String.format("Expression Error:\n\tEmpty Expression\n\t%s:%d\n", errToken.getFilename(), errToken.getLineNum()));
            } else {
                return new OperationNode(expressionNode, op, right);
            }
        } else { // we have a number, but no op or rel op follows, so our expression is simply a number
            if(expressionNode == null){
                Token errToken = tokens.get(0);
                throw new Exception(String.format("Expression Error:\n\tEmpty Expression\n\t%s:%d\n", errToken.getFilename(), errToken.getLineNum()));
            }
            return expressionNode;
        }
    }
    public boolean isBoolean();

    //Idea here is to be able to get type expression when doing validation in
    //AssignNode, so the type of constant node and idNode too if needed?
    public String getJottType();

    //to be able to get token info like Line Number
    public Token getTokenObj();
}
