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

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable, HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception{
        for (BodyStmtNode bsn : this.bodyStmtNodes) {
            bsn.validateTree(functionSymbolTable, localVariableSymbolTable);
        }
        return this.returnStmtNode == null ||
                this.returnStmtNode.validateTree(functionSymbolTable, localVariableSymbolTable);
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
        StringBuilder output = new StringBuilder();
        for(BodyStmtNode bodyStmtNode: this.bodyStmtNodes){
            output.append(bodyStmtNode.convertToJava(className));
            if (output.charAt(output.length()-1) != '}'){
                output.append(";");
            }
            output.append("\n");
        }
        if(this.returnStmtNode != null){
            output.append(this.returnStmtNode.convertToJava(className)).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder output = new StringBuilder();
        for(BodyStmtNode bodyStmtNode: this.bodyStmtNodes){
            output.append(bodyStmtNode.convertToC());
            if (output.charAt(output.length()-1) != '}'){
                output.append(";");
            }
            output.append("\n");
        }
        if(this.returnStmtNode != null){
            output.append(this.returnStmtNode.convertToC()).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToPython(int depth) {
        StringBuilder output = new StringBuilder();
        for(BodyStmtNode bodyStmtNode: this.bodyStmtNodes){
            output.append(bodyStmtNode.convertToPython(depth+1));
            output.append("\n");
        }
        if(this.returnStmtNode != null){
            output.append(this.returnStmtNode.convertToPython(depth+1)).append("\n");
        }
        return output.toString();
    }

    public boolean validateReturn(HashMap<String, FunctionDef> functionSymbolTable,
                                  HashMap<String, ArrayList<String>> localVariableSymbolTable, String returnType) throws Exception {
        boolean foundReturn = false;

        for (BodyStmtNode bodyStmtNode : bodyStmtNodes) {
            if(!foundReturn) {
                if (bodyStmtNode.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType)) {
                    foundReturn = true;
                }
            }
            else{
                throw new Exception(String.format("Semantic Error:\n\tUnreachable code\n%s", bodyStmtNode.getLocation()));
            }
        }
        if (this.returnStmtNode != null) {
            if (!foundReturn) {
                foundReturn = this.returnStmtNode.validateReturn(functionSymbolTable, localVariableSymbolTable, returnType);
            }
            else{
                throw new Exception(String.format("Semantic Error:\n\tUnreachable code\n\t%s", this.returnStmtNode.getLocation()));
            }
        }

        return foundReturn;
    }
}

