package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AssignNode implements StmtNode {
    // < asmt > -> < type > <id > = < expr > < end_statement > | <id > = < expr > < end_statement >

    private final TypeNode typeNode;
    private final IdNode idNode;
    private final ExprNode exprNode;

    public AssignNode(TypeNode typeNode, IdNode idNode, ExprNode exprNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.exprNode = exprNode;
    }

    static AssignNode parseAssignNode(ArrayList<Token> tokens) throws Exception {

        TypeNode typeNode = null;

        switch (tokens.get(0).getToken()) {
            case "Double", "Integer", "String", "Boolean" -> typeNode = TypeNode.parseTypeNode(tokens);
            default -> {}
        }

        IdNode idNode = IdNode.parseIdNode(tokens);

        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Assign Error:\n\tExpected Assign Token, found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);

        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            Token errToken = tokens.get(0);
            throw new Exception(String.format("Assign Error:\n\tExpected \";\", found \"%s\"\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
        }
        tokens.remove(0);
        return new AssignNode(typeNode, idNode, exprNode);
    }

    @Override
    public String convertToJott() {
        String output = (typeNode == null ? "" : typeNode.convertToJott()) + " " + idNode.convertToJott() + " = " +
                exprNode.convertToJott();

        return output;
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
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) throws Exception{
        Token exprToken = exprNode.getTokenObj();
        //todo if exprNode is constant node- only constant node has a getJottType() actually implemented atm!
        //body of this if commented out below
            /*
            //if type of idNode is not equal to type of a constant
            if ( !Objects.equals(localVariableSymbolTable.get(idNode.getIdName()), this.exprNode.getJottType()) ) {
                try {
                    throw new Exception(String.format("Semantic Error:\n constant \"%s\" has invalid type\n%s:%d\n", exprToken.getToken(), exprToken.getFilename(), exprToken.getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            */

        //somehow this method can be written much better avoiding redundancy
        //if exprNode is also an idNode, so in case of "Integer a = b" for example
            /* //in second portion of if condition, getToken should return id Name?
            if ( !Objects.equals(localVariableSymbolTable.get(idNode.getIdName()), localVariableSymbolTable.get(exprToken.getToken())) ) {
                try {
                    throw new Exception(String.format("Semantic Error:\n variable \"%s\" has invalid type\n%s:%d\n", exprToken.getToken(), exprToken.getFilename(), exprToken.getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            */
        localVariableSymbolTable.put(this.idNode.getIdName(), this.typeNode.getType());
        return false;
    }
}
