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
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, String> localVariableSymbolTable) {
        Token exprToken = this.exprNode.getTokenObj();
        boolean addToLocalVarSymbolTable = false; //default assumes already exists in table
        String idTokenJottType;
        if (this.typeNode == null) { //variable has already been declared and should exist in localVarSymTable: <id> = <expr>
            idTokenJottType = localVariableSymbolTable.get(this.idNode.getIdName());
        } else { //variable is being declared in same statement as assignment: <type> <id> = <expr>
            addToLocalVarSymbolTable = true;
            idTokenJottType = this.typeNode.getType();
        }
        //if exprNode is A Constant Node - only constant node has a getJottType() actually implemented atm!
        if (exprToken.getTokenType() == TokenType.NUMBER || exprToken.getTokenType() == TokenType.STRING ||
                Objects.equals(exprToken.getToken(), "True") || Objects.equals(exprToken.getToken(), "False")) {
            String exprTokenJottType = this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable);
            //if type of idNode is not equal to type of constant
            if ( !Objects.equals(idTokenJottType, exprTokenJottType) ) {
                try {
                    throw new Exception(String.format("Semantic Error:\n constant \"%s\" is of type '%s', and does not " +
                                    "match type '%s' \n%s:%d\n", exprToken.getToken(), exprTokenJottType,
                            idTokenJottType, exprToken.getFilename(), exprToken.getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //Somehow this method can be written much better avoiding redundancy
        //if exprNode is also an idNode (b or functionName in cases below):
        // Cases: "Integer a = b"
        //        "Integer a = functionName(...)"
        if (exprToken.getTokenType() == TokenType.ID_KEYWORD) {
            // if our exprNode (IdNode) is an id that is a functionName (rather than an id of a variable)
            if (functionSymbolTable.containsKey(exprToken.getToken())) {
                //if id type is not equal to return type for function
                String exprIdfunctionReturnType = functionSymbolTable.get(exprToken.getToken()).getReturnType();
                if (!Objects.equals(idTokenJottType, exprIdfunctionReturnType)) {
                    try {
                        throw new Exception(String.format("Semantic Error:\n return type of function \"%s\" is of type " +
                                "'%s', and does not match type '%s' \n%s:%d\n", exprToken.getToken(), exprIdfunctionReturnType,
                                idTokenJottType, exprToken.getFilename(), exprToken.getLineNum()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            //in second portion of if condition, getToken should return id Name?
            String exprIdJottType = localVariableSymbolTable.get(exprToken.getToken());
            if ( !Objects.equals(idTokenJottType, exprIdJottType) ) {
                try {
                    throw new Exception(String.format("Semantic Error:\n variable \"%s\" is of type '%s', and does not " +
                            "match type '%s'\n%s:%d\n", exprToken.getToken(), exprIdJottType, idTokenJottType,
                            exprToken.getFilename(), exprToken.getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (addToLocalVarSymbolTable) {
            localVariableSymbolTable.put(this.idNode.getIdName(), this.typeNode.getType());
        }
        return true;
    }


    @Override
    public boolean containsReturn() {
        return false;
    }
}
