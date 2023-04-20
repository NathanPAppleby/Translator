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
        output += "#define print(X) _Generic((X), \\\n" +
                "    int: printInteger, \\\n" +
                "    double: printDouble, \\\n" +
                "    char*: printString, \\\n" +
                "    bool: printBoolean \\\n" +
                "    )(X)\n";
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
        output += "void printInteger(int i1) {\n" +
                "   printf(\"%d\\n\", i1);\n" +
                "}\n" +
                "void printDouble(double d1) {\n" +
                "   printf(\"%f\\n\", d1);\n" +
                "}\n" +
                "void printString(char* s1) {\n" +
                "   printf(\"%s\\n\", s1);\n" +
                "}\n" +
                "void printBoolean(bool b1) {\n" +
                "   printf(\"%s\\n\", b1?\"True\":\"False\");\n" +
                "}\n";
        output += this.functionListNode.convertToC();
        return output;
    }

    @Override
    public String convertToPython(int depth) {
        return this.functionListNode.convertToPython(depth) + "\nmain()";
    }

    public String convertToPython() { return convertToPython(0); }
}
