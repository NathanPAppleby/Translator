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
public class ExprNode implements JottTree {

    //each token in arrayList of Tokens knows its Type from tokenizing logic
    static ExprNode parseExprNode(ArrayList<Token> tokens) throws Exception {
        //if next token is constant Node- need to cut this down with a simple ask if constant node somehow
        if (tokens.get(0).getTokenType().equals(TokenType.STRING) ||
                tokens.get(0).getTokenType().equals(TokenType.NUMBER) ||
                tokens.get(0).getToken().equals("true") || tokens.get(0).getToken().equals("false")
        ) {
            ArrayList<Token> constantToken = new ArrayList<>();
            constantToken.add(tokens.get(0));
            return ConstantNode.parseConstantNode(constantToken);
        } // if next token is function call
        else if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) &&
                tokens.get(1).getTokenType().equals(TokenType.L_BRACKET)) {
            ArrayList<Token> funcCallToken = new ArrayList<>();
            funcCallToken.add(tokens.get(0));
            return FuncCallNode.parseFuncCallNode(funcCallToken);
        } //if operation Node
        else if (tokens.get(0).getTokenType().equals(TokenType.MATH_OP)) {
            ArrayList<Token> operationToken = new ArrayList<>();
            operationToken.add(tokens.get(0));
            return OperationNode.parseOperationNode(operationToken);
        } // if next token is id
        else if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            ArrayList<Token> idToken = new ArrayList<>();
            idToken.add(tokens.get(0));
            return IdNode.parseIdNode(idToken);
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
