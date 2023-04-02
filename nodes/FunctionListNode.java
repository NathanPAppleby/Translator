package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;
import symbols.FunctionParameter;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionListNode implements JottTree {
    private final ArrayList<FunctionDefNode> functionDefNodes;
    public FunctionListNode(ArrayList<FunctionDefNode> functionDefNodes) {
        this.functionDefNodes = functionDefNodes;
    }

    static FunctionListNode parseFunctionListNode(ArrayList<Token> tokens) throws Exception {
        ArrayList<FunctionDefNode> functionDefNodes = new ArrayList<>();
        getFunctionDefNodes(tokens, functionDefNodes);
        return new FunctionListNode(functionDefNodes);
    }

    static void getFunctionDefNodes(ArrayList<Token> tokens, ArrayList<FunctionDefNode> functionDefNodes) throws Exception {
        if(!tokens.isEmpty()) {
            FunctionDefNode fn = FunctionDefNode.parseFunctionDefNode(tokens);
            functionDefNodes.add(fn);
            getFunctionDefNodes(tokens, functionDefNodes);
        }
    }

    public boolean validateTree(HashMap<String, FunctionDef> functionSymbolTable,
                                HashMap<String, ArrayList<String>> localVariableSymbolTable) throws Exception {

        ArrayList<FunctionParameter> printParams = new ArrayList<>(){
            {
                add(new FunctionParameter("toPrint", "String"));
            }
        };
        FunctionDef print = new FunctionDef("print", printParams, "Void");
        functionSymbolTable.put("print", print);

        ArrayList<FunctionParameter> concatParams = new ArrayList<>(){
            {
                add(new FunctionParameter("concat1", "String"));
                add(new FunctionParameter("concat2", "String"));
            }
        };
        FunctionDef concat = new FunctionDef("concat", concatParams, "String");
        functionSymbolTable.put("concat", concat);

        ArrayList<FunctionParameter> lengthParams = new ArrayList<>(){
            {
                add(new FunctionParameter("toLength", "String"));
            }
        };
        FunctionDef length = new FunctionDef("length", lengthParams, "Integer");
        functionSymbolTable.put("length", length);


        for (FunctionDefNode fn : this.functionDefNodes) {
            // Validation
            fn.validateTree(functionSymbolTable, localVariableSymbolTable);
            // After validation, add function to function symbol table
            if (functionSymbolTable.containsKey(fn.getFunctionName())) {
                String fileInfo = fn.getFilenameAndLine();
                throw new Exception(String.format("Semantic Error:\n\tFunction \"%s\" already exists\n\t%s",
                        fn.getFunctionName(), fileInfo));
            }
            functionSymbolTable.put(fn.getFunctionName(), FunctionDef.buildFunctionDef(fn));
        }
        // Check to make sure there is a correctly defined main function
        if (!functionSymbolTable.containsKey("main")) {
            String fileInfo = this.functionDefNodes.get(this.functionDefNodes.size()-1).getFilenameAndLine();
            throw new Exception(
                    String.format("Semantic Error:\n\tMissing main function definition\n\t%s", fileInfo));
        } else {
            // main function must return "Void" or "Integer" types
            FunctionDef fd = functionSymbolTable.get("main");
            if (!fd.returnType.equals("Void") && !fd.returnType.equals("Integer")) {
                String fileInfo = this.functionDefNodes.get(this.functionDefNodes.size()-1).getFilenameAndLine();
                throw new Exception(
                        String.format("Semantic Error:\n\tMain function must return type \"Void\" or \"Integer\"\n\t%s",
                        fileInfo));
            }
        }
        return true;
    }

    @Override
    public String convertToJott() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToJott()).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToJava(className)).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToC()).append("\n");
        }
        return output.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder output = new StringBuilder();
        for(FunctionDefNode defNode : this.functionDefNodes){
            output.append(defNode.convertToPython()).append("\n");
        }
        return output.toString();
    }
}
