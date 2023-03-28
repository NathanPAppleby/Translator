package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

public class BodyNode implements JottTree {
    private final ArrayList<BodyStmtNode> bodyStmtNodes;
    private final ReturnStmtNode returnStmtNode;
    private final HashMap<String, String> localVarSymbolTable;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode, HashMap<String, String> localVarSymbolTable) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
        this.localVarSymbolTable = localVarSymbolTable;
    }
    static BodyNode parseBodyNode(ArrayList<Token> tokens, HashMap<String, String> localVarSymbolTable) throws Exception {
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        ReturnStmtNode returnStmtNode = null;
        getBodyStmtNodes(tokens, bodyStmtNodes, localVarSymbolTable);
        if (tokens.get(0).getToken().equals("return")) {
            returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);
        }
        return new BodyNode(bodyStmtNodes, returnStmtNode, localVarSymbolTable);
    }

    static void getBodyStmtNodes(ArrayList<Token> tokens, ArrayList<BodyStmtNode> bodyStmtNodes, HashMap<String, String> localVarSymbolTable) throws Exception {
        if(!tokens.get(0).getToken().equals("return") &&
                tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            bodyStmtNodes.add(BodyStmtNode.parseBodyStmtNode(tokens, localVarSymbolTable));
            getBodyStmtNodes(tokens, bodyStmtNodes, localVarSymbolTable);
        }
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

    @Override
    public boolean validateTree(HashMap<String, String> localVariableSymbolTable) {
        return false;
    }
}

