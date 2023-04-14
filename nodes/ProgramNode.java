package nodes;

import provided.JottTree;
import provided.Token;
import symbols.FunctionDef;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;

    public ProgramNode(FunctionListNode functionListNode){
        this.functionListNode = functionListNode;
    }

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        Token lastToken = tokens.get(tokens.size()-1);
        try {
            FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
            return new ProgramNode(functionListNode);
        }
        catch (Exception e) {
            if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                System.err.printf("General Tokens Error: \n\tPrematurely ran out of tokens in parser.\n\t%s:%d\n%n", lastToken.getFilename(), lastToken.getLineNum());
            }
            else {
                System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            }
            return null;
        }
    }

    public boolean validateTree() throws Exception {
        HashMap<String, FunctionDef> newFunctionSymbolTable = new HashMap<>();
        try {
            functionListNode.validateTree(newFunctionSymbolTable, null);
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getLocalizedMessage() == null ? e.toString() : e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public String convertToJott() {
        return this.functionListNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return "public class " + className + " {\n" + this.functionListNode.convertToJava(className) + "}\n";
    }

    @Override
    public String convertToC() {
        // Basic functions and imports
        String output = "#include <stdio.h>\n" +
                "#include <stdlib.h>\n" +
                "#include <string.h>\n" +
                "#include <stdbool.h>\n";
        output += "char* concat(char* s1, char* s2) {\n" +
                "   int length = strlen(s1) + strlen(s2);\n" +
                "   char *buffer = malloc(length + 1);\n" +
                "   strcat(buffer, s1);\n" +
                "   strcat(buffer, s2);\n" +
                "   return buffer;\n" +
                "}\n";
        output += "int length(char* s1) {\n" +
                "   return strlen(s1);\n" +
                "}\n";
        output += "void print(void* v1) {\n" +
                "   printf(\"Todo: fix print statement\");\n" +
                "}\n";
        output += this.functionListNode.convertToC();
        return output;
    }

    @Override
    public String convertToPython(int depth) {
        return this.functionListNode.convertToPython(depth);
    }

    public String convertToPython() { return convertToPython(0); }
}
