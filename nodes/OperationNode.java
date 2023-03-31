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
    private final boolean isOperation;

    OperationNode(ExprNode leftNode, OperatorNode middleNode, ExprNode rightNode) {
        this.left = leftNode;
        this.middle = middleNode;
        this.right = rightNode;
        this.isOperation = true;
    }

    @Override
    public boolean isBoolean(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        return this.getJottType(functionSymbolTable, localVariableSymbolTable).contains("Boolean");
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
                && this.right.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    @Override
    public String getJottType(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception {
        String ltype = this.left.getJottType(functionSymbolTable, localVariableSymbolTable);
        String rtype = this.right.getJottType(functionSymbolTable, localVariableSymbolTable);
        // Compare the left and right types
        if(((ltype.contains("Integer") && (rtype.contains("Integer")))
                || ltype.contains("Double") && (rtype.contains("Double")))){
            // Make sure they are the right types
            if((ltype.equals("Integer") && rtype.equals("Integer"))
                    || (ltype.equals("Double") && rtype.equals("Double"))){
                // Figure out if operation is boolean or not
                if(this.middle.getToken().getTokenType().equals(TokenType.REL_OP)){
                    return ltype + " Boolean";
                }
                else{
                    return ltype;
                }
            }
            else if (ltype.contains("Boolean") && !rtype.contains("Boolean")) {
                return ltype;
            }
            else if (!ltype.contains("Boolean") && rtype.contains("Boolean")){
                return rtype;
            }
        }
        String file = this.getTokenObj().getFilename() + ":" + this.getTokenObj().getLineNum();
        throw new Exception(String.format("Semantic Error:\n\tEquation mixes incompatible types\n\t%s", file));
    }

    @Override
    public boolean isOperation() {
        return this.isOperation;
    }

    /**
     * For this Override in Operation Node, because OperationNode doesn't
     * have a distinct token, we will return the left Node's token
     * @return Left Node of Operation's Token Object
     */
    @Override
    public Token getTokenObj() { return this.left.getTokenObj(); }

}
