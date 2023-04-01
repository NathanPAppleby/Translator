package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class BodyNode implements JottTree {
    private final ArrayList<BodyStmtNode> bodyStmtNodes;
    private final ReturnStmtNode returnStmtNode;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
    }
    static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        ReturnStmtNode returnStmtNode = null;
        getBodyStmtNodes(tokens, bodyStmtNodes);
        if (tokens.get(0).getToken().equals("return")) {
            returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);
        }
        return new BodyNode(bodyStmtNodes, returnStmtNode);
    }

    static void getBodyStmtNodes(ArrayList<Token> tokens, ArrayList<BodyStmtNode> bodyStmtNodes) throws Exception {
        if(!tokens.get(0).getToken().equals("return") &&
                tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens));
            getBodyStmtNodes(tokens, bodyStmtNodes);
        }
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, IdNode> localVariableSymbolTable) throws Exception{
        for (BodyStmtNode bsn : this.bodyStmtNodes) {
            bsn.validateTree(functionSymbolTable, localVariableSymbolTable);
        }
        return this.returnStmtNode == null ||
                this.returnStmtNode.validateTree(functionSymbolTable, localVariableSymbolTable);
    }

    public boolean alwaysReturns() {
        if (this.returnStmtNode != null) {
            return true;
        }
        // If the body does not have a return statement,
        // iterate backwards through the body statements to look for a body statement that always returns
        BodyStmtNode lastBdyStmtNode = this.bodyStmtNodes.get(this.bodyStmtNodes.size()-1);
        return lastBdyStmtNode.containsReturn();
    }

    @Override
    public String convertToJott() {
        StringBuilder output = new StringBuilder();
        for(BodyStmtNode bodyStmtNode: this.bodyStmtNodes){
            output.append(bodyStmtNode.convertToJott());
            if (output.charAt(output.length()-1) != '}'){
                output.append(";");
            }
            output.append("\n");
        }
        if(this.returnStmtNode != null){
            output.append(this.returnStmtNode.convertToJott()).append("\n");
        }
        return output.toString();
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

    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable,
                                  HashMap<String, IdNode> localVariableSymbolTable, String returnType) throws Exception {
        boolean foundReturn = false;
        if (this.returnStmtNode != null) {
            foundReturn = this.returnStmtNode.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);
        }

        for (BodyStmtNode bodyStmtNode : bodyStmtNodes) {
            if (bodyStmtNode.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType)) {
                if (foundReturn) {
                    throw new Exception("Found extra return");
                }
                else {
                    foundReturn = true;
                }
            }
        }

        return foundReturn;
    }
}

