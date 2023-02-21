package nodes;

import provided.JottTree;
import provided.Token;

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
public class ExprNode extends ParamNode implements JottTree {
    static ExprNode parseExprNode(ArrayList<Token> tokens) {
        return null;
    }

    /*
        //each token in arrayList of Tokens knows its Type from tokenizing logic
    static SExprNode parseSExprNode(ArrayList<Token> tokens) throws Exception {
        //if next token is str literal
        if (tokens.get(0).getTokenType().equals(TokenType.STRING)) {
            //return new SExprNode(); param to SExprNode result of parseStrLiteralNode
        } //if next token is id
        else if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) &&
                tokens.get(1).getTokenType().equals(TokenType.L_BRACKET)) {
            ArrayList<Token> funcCallTokens = new ArrayList<>();
            funcCallTokens.add(tokens.get(0));
            FuncCallNode.parseFuncCallNode(funcCallTokens);
            //return new SExprNode();
        }
        else if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            //return new SExprNode(); param to SExprNode result of parseIdNode
        } // if next token is function call
        else {
            throw new Exception();
        }
        return null;
    }
     */

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
