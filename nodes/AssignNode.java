package nodes;

import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

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
    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {

        boolean addToLocalVarSymbolTable = false; //default assumes already exists in table
        String idTokenJottType;
        if (this.typeNode == null) { //variable has already been declared and should exist in localVarSymTable: <id> = <expr>
            if(localVariableSymbolTable.containsKey(this.idNode.getIdName())) {
                localVariableSymbolTable.get(this.idNode.getIdName()).set(1, "True");
            }
            else{
                String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
                throw new Exception(String.format("Semantic Error:\n\tVariable \"%s\" does not exist\n\t%s", this.idNode.getIdName(), file));
            }
            idTokenJottType = localVariableSymbolTable.get(this.idNode.getIdName()).get(0);
        } else { //variable is being declared in same statement as assignment: <type> <id> = <expr>
            addToLocalVarSymbolTable = true;
            idTokenJottType = this.typeNode.getType();
        }

        this.idNode.validateTree(functionSymbolTable, localVariableSymbolTable);

        Token expressionToken = this.exprNode.getTokenObj();
        //if our expression is a variable or a function name, we check to make sure it is declared
        if ((expressionToken.getTokenType() == TokenType.ID_KEYWORD)
                && (!Objects.equals(expressionToken.getToken(), "True"))
                && (!Objects.equals(expressionToken.getToken(), "False"))) {
            // if our exprssionToken (IdNode) being a functionName or a variable, ensure it has been declared/defined
            exprNode.isInitialized(functionSymbolTable, localVariableSymbolTable);
            /*
            if (!localVariableSymbolTable.containsKey(expressionToken.getToken()) || !functionSymbolTable.containsKey(expressionToken.getToken())) {
                try {
                    throw new Exception(String.format("Semantic Error:\n\tVariable or Function '%s' is undefined " +
                                    "\n\t%s:%d\n", expressionToken.getToken(), expressionToken.getFilename(),
                            expressionToken.getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

             */
        }

        //handle operation return check FIRST
        if (this.exprNode.isOperation()) {
            //first we make sure all variables in operation have been initalized and declared.
            if (!exprNode.isInitialized(functionSymbolTable, localVariableSymbolTable)) {
                try {
                    String file = this.idNode.getTokenObj().getFilename() + ":" + this.idNode.getTokenObj().getLineNum();
                    throw new Exception(String.format("Semantic Error:\n\tVariable in operation has not been initialized\n\t"+file));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            String exprTokenJottType = this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable);
            if (!ExprNode.typeMatch(idTokenJottType, exprTokenJottType)) {
                try { //could specify entire operation in output?
                    throw new Exception(String.format("Semantic Error:\n\tOperation return is of type \"%s\", and " +
                                    "does not match type \"%s\"\n\t%s:%d", exprTokenJottType, idTokenJottType,
                            this.exprNode.getTokenObj().getFilename() , this.exprNode.getTokenObj().getLineNum()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (addToLocalVarSymbolTable) {
                localVariableSymbolTable.put(this.idNode.getIdName(), new ArrayList<>());
                localVariableSymbolTable.get(this.idNode.getIdName()).add(this.typeNode.getType());
                localVariableSymbolTable.get(this.idNode.getIdName()).add("True");
            }
            return true; //if return type of entire operation checks out, then it's operands are of proper type as well
        }
        Token exprToken = this.exprNode.getTokenObj();
        //if exprNode is A Constant Node
        if (exprToken.getTokenType() == TokenType.NUMBER || exprToken.getTokenType() == TokenType.STRING ||
                Objects.equals(exprToken.getToken(), "True") || Objects.equals(exprToken.getToken(), "False")) {
            //if type of idNode is not equal to type of constant
            String exprTokenJottType = this.exprNode.getJottType(functionSymbolTable, localVariableSymbolTable);
            if ( !ExprNode.typeMatch(idTokenJottType, exprTokenJottType) ) {
                try {
                    throw new Exception(String.format("Semantic Error:\n\tConstant \"%s\" is of type \"%s\", and does not " +
                                    "match type \"%s\" \n\t%s:%d", exprToken.getToken(), exprTokenJottType,
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
        //we handle constants up top we don't want to consider handling Boolean constant here
        // even though 'True', 'False' are of type ID_KEYWORD, hence 2nd/3rd portions of conditional
        if ((exprToken.getTokenType() == TokenType.ID_KEYWORD)
                && (!Objects.equals(exprToken.getToken(), "True"))
                && (!Objects.equals(exprToken.getToken(), "False"))) {
            // if our exprNode (IdNode) is an id that is a functionName (rather than an id of a variable)
            if (functionSymbolTable.containsKey(exprToken.getToken())) {
                //if id type is not equal to return type for function
                String exprIdfunctionReturnType = functionSymbolTable.get(exprToken.getToken()).getReturnType();
                if (!Objects.equals(idTokenJottType, exprIdfunctionReturnType)) {
                    try {
                        throw new Exception(String.format("Semantic Error:\n\tReturn type of function \"%s\" is of type " +
                                        "\"%s\", and does not match type \"%s\" \n\t%s:%d", exprToken.getToken(), exprIdfunctionReturnType,
                                idTokenJottType, exprToken.getFilename(), exprToken.getLineNum()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                //we also want to validate the function call as part of the assignment statement
                this.exprNode.validateTree(functionSymbolTable, localVariableSymbolTable);
            } else {

                if (!exprNode.isInitialized(functionSymbolTable, localVariableSymbolTable)) {
                    try {
                        throw new Exception(String.format("Semantic Error:\n\tVariable \"%s\" has not been initialized " +
                                        "\n\t%s:%d", exprToken.getToken(), exprToken.getFilename(), exprToken.getLineNum()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                //in second portion of if condition, getToken should return id Name?
                String exprIdJottType = localVariableSymbolTable.get(exprToken.getToken()).get(0);
                if ( !Objects.equals(idTokenJottType, exprIdJottType) ) {
                    try {
                        throw new Exception(String.format("Semantic Error:\n\tVariable \"%s\" is of type\"%s\", and does not " +
                                        "match type \"%s\"\n\t%s:%d", exprToken.getToken(), exprIdJottType, idTokenJottType,
                                exprToken.getFilename(), exprToken.getLineNum()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (addToLocalVarSymbolTable) {
            localVariableSymbolTable.put(this.idNode.getIdName(), new ArrayList<>());
            localVariableSymbolTable.get(this.idNode.getIdName()).add(this.typeNode.getType());
            localVariableSymbolTable.get(this.idNode.getIdName()).add("True");
        }
        return true;
    }

    @Override
    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        return false;
    }

    @Override
    public String getLocation() {
        return String.format("%s:%s",this.exprNode.getTokenObj().getFilename(), this.exprNode.getTokenObj().getLineNum());
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
    public String convertToPython(int depth) {
        return null;
    }



}
