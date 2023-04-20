package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class IfStmtNode implements BodyStmtNode {

    private ExprNode b_expr;
    private BodyNode body;
    private ElseIfLstNode elseif_lst;
    private ElseNode else_node;

    public IfStmtNode(ExprNode exprNode, BodyNode bodyNode, ElseIfLstNode elseIfLstNode, ElseNode elseNode) {
        b_expr = exprNode;
        body = bodyNode;
        elseif_lst = elseIfLstNode;
        else_node = elseNode;
    }

    static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws Exception {
        tokens.remove( 0 );
        if ( tokens.get( 0 ).getTokenType().equals( TokenType.L_BRACKET ) ) {
            tokens.remove(0);
            ExprNode exprNode = ExprNode.parseExprNode( tokens );
            if ( tokens.get( 0 ).getTokenType().equals( TokenType.R_BRACKET ) ) {
                tokens.remove(0);
                if ( tokens.get( 0 ).getTokenType().equals( TokenType.L_BRACE) ) {
                    tokens.remove(0);
                    BodyNode bodyNode = BodyNode.parseBodyNode( tokens );
                    if ( tokens.get( 0 ).getTokenType().equals( TokenType.R_BRACE ) ) {
                        tokens.remove(0);
                        ElseIfLstNode elseIfLstNode = ElseIfLstNode.parseElseIfLstNode( tokens );
                        ElseNode elseNode = ElseNode.parseElseNode( tokens );
                        return new IfStmtNode(exprNode, bodyNode, elseIfLstNode, elseNode);
                    } else {
                        Token errToken = tokens.get(0);
                        throw new Exception(String.format("If Statement Error:\n\tExpected \"}\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                    }
                } else {
                    Token errToken = tokens.get(0);
                    throw new Exception(String.format("If Statement Error:\n\tExpected \"{\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                }
            } else {
                Token errToken = tokens.get(0);
                throw new Exception(String.format("If Statement Error:\n\tExpected \"]\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
            }
        } else {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("If Statement Error:\n\tExpected \"[\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
    }

    @Override
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {
        if (!this.b_expr.isBoolean(functionSymbolTable, localVariableSymbolTable)){
            throw new Exception(String.format("Semantic Error:\n\tIf statement conditional requires boolean value\n\t%s", this.getLocation()));
        }

        return b_expr.validateTree(functionSymbolTable, localVariableSymbolTable) &&
                body.validateTree(functionSymbolTable, localVariableSymbolTable) &&
                (elseif_lst == null || elseif_lst.validateTree(functionSymbolTable, localVariableSymbolTable)) &&
                (else_node == null || else_node.validateTree(functionSymbolTable, localVariableSymbolTable)) &&
                b_expr.isBoolean(functionSymbolTable, localVariableSymbolTable);
    }

    @Override
    public String getLocation() {
        return String.format("%s:%s",this.b_expr.getTokenObj().getFilename(), this.b_expr.getTokenObj().getLineNum());
    }

    @Override
    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        boolean bodyReturn = this.body.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);
        boolean elseifReturn = this.elseif_lst == null ||
                this.elseif_lst.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);
        boolean elseReturn = else_node != null &&
                this.else_node.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);

        return bodyReturn && elseifReturn && elseReturn;
    }

    @Override
    public String convertToJott() {
        return "if[" +
                this.b_expr.convertToJott() +
                "] {\n" +
                this.body.convertToJott() +
                "\n}\n" +
                (this.elseif_lst == null ? "" : this.elseif_lst.convertToJott()) +
                (this.else_node == null ? "" : this.else_node.convertToJott());
    }

    @Override
    public String convertToJava(String className) {
        return "if ( " +
                this.b_expr.convertToJava(className) +
                " ) {\n\t" +
                this.body.convertToJava(className) +
                "\n}" +
                (this.elseif_lst == null ? "" : this.elseif_lst.convertToJava(className)) +
                (this.else_node == null ? "" : this.else_node.convertToJava(className));
    }

    @Override
    public String convertToC() {
        return "if ( " +
                this.b_expr.convertToC() +
                " ) {\n\t" +
                this.body.convertToC() +
                "\n}" +
                (this.elseif_lst == null ? "" : this.elseif_lst.convertToC()) +
                (this.else_node == null ? "" : this.else_node.convertToC());
    }

    @Override
    public String convertToPython(int depth) {
        return "\t".repeat(depth) +
                "if " + this.b_expr.convertToPython(0) + ":\n" + this.body.convertToPython(depth + 1) + (this.elseif_lst == null ? "" : this.elseif_lst.convertToPython(depth)) + (this.else_node == null ? "" : this.else_node.convertToPython(depth));
    }


}
