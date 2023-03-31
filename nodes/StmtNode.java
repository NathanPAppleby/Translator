package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public interface StmtNode extends BodyStmtNode, JottTree {

    static StmtNode parseStmtNode(ArrayList<Token> tokens) throws Exception {
        // firsts for both varDec and Assign are from <type>
        if (tokens.get(0).getToken().equals("Double") ||
                tokens.get(0).getToken().equals("Integer") ||
                tokens.get(0).getToken().equals("String") ||
                tokens.get(0).getToken().equals("Boolean")){
            if(tokens.get(2).getToken().equals(";")){
                return VarDecNode.parseVarDecNode(tokens);
            }
            else{
                return AssignNode.parseAssignNode(tokens);
            }
        }
        else{
            //if Assigns first isn't from type, it should begin w id followed by '='
            if(tokens.get(1).getToken().equals("=")){
                return AssignNode.parseAssignNode(tokens);
            }
            else{
                FuncCallNode funcCallNode = FuncCallNode.parseFuncCallNode(tokens);
                if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
                    Token errToken = tokens.get(0);
                    throw new Exception(String.format("Statement Error:\n\tReceived token \"%s\" expected \";\".\n\t%s:%d\n", errToken.getToken(), errToken.getFilename(), errToken.getLineNum()));
                }
                tokens.remove(0);
                return funcCallNode;
            }
        }
    }

    boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                         HashMap<String, String> localVariableSymbolTable) throws Exception;
}
