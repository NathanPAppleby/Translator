package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.HashMap;

/**
 * A Type of ExprNode, OperationNode follows the format <expr> operator <expr>
 * which can break down into one of the following:
 *     <id> <op> <n_expr>          (ConstantNode OpNode ExprNode)
 *     <num> <op> <n_expr>         (ConstantNode OpNode ExprNode)
 *     <func_call> <op> <n_expr>   (ConstantNode OpNode ExprNode)
 *     <n_expr> <rel_op> <n_expr>  (ExprNode RelOpNode ExprNode)
 */
public class OperationNode implements ExprNode {
    private final ExprNode left;
    private final OperatorNode middle;
    private final ExprNode right;
    private final boolean isBoolean;

    OperationNode(ExprNode leftNode, OperatorNode middleNode, ExprNode rightNode) {
        this.left = leftNode;
        this.middle = middleNode;
        this.isBoolean = (middleNode.getToken().getTokenType().equals(TokenType.REL_OP));
        this.right = rightNode;
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        return isBoolean;
    }

    @Override
    public String convertToJott() {
        return this.left.convertToJott() +
                this.middle.convertToJott() +
                this.right.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return this.left.convertToJava(className) +
                this.middle.convertToJava(className) +
                this.right.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.left.convertToC() +
                this.middle.convertToC() +
                this.right.convertToC();
    }

    @Override
    public String convertToPython() {
        return this.left.convertToPython() +
                this.middle.convertToPython() +
                this.right.convertToPython();
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        return this.left.validateTree(functionSymbolTable, localVariableSymbolTable)
                && this.middle.validateTree(functionSymbolTable, localVariableSymbolTable)
                && this.right.validateTree(functionSymbolTable, localVariableSymbolTable)
                && !this.getJottType(functionSymbolTable, localVariableSymbolTable).equals("Invalid");
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        String ltype = this.left.getJottType(functionSymbolTable, localVariableSymbolTable);
        String rtype = this.right.getJottType(functionSymbolTable, localVariableSymbolTable);
        // Compare the left and right types
        if(ltype.equals(rtype) && !ltype.equals("Invalid")){
            // Make sure they are the right types
            if(ltype.equals("Integer") || ltype.equals("Double")){
                // Figure out if operation is boolean or not
                if(this.middle.getToken().getTokenType().equals(TokenType.REL_OP)){
                    return "Boolean";
                }
                else{
                    return ltype;
                }
            }
        }
        return "Invalid";
    }

    @Override
    public Token getTokenObj() { return null; }


}
