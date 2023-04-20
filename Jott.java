

import nodes.ProgramNode;
import provided.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;

public class Jott {

    public static void main(String[] args) throws Exception {
        if(args.length != 3 && args.length != 2) {
            throw new Exception("Invalid input. Valid input:\njava Jott.java {input file} {output file} {output language}");
        }
        if (!new File(args[0]).isFile()) {
            throw new Exception("Invalid input file name: " + args[0]);
        }

        String jottFile = args[0];
        String language = "Jott";

        if(args.length == 3) {
            language = args[2];
        }
        ArrayList<Token> tokenList = JottTokenizer.tokenize(jottFile);
        if (tokenList == null) {
            return;
        }

        ProgramNode programNode = ProgramNode.parseProgramNode(tokenList);
        if (programNode == null) {
            return;
        }
        if(programNode.validateTree()) {
            //System.out.println(programNode.convertToJott());

            File outFile = new File(args[1]);
            if (outFile.createNewFile()) {
                FileWriter writer = new FileWriter(args[1]);
                String conversion;
                switch (language) {
                    case "Jott" -> conversion = programNode.convertToJott();
                    case "Java" -> {
                        String className = "";
                        for (int i = 0; i < args[1].length(); i++) {
                            if (args[1].charAt(i) == '.') {
                                break;
                            }
                            className += args[1].charAt(i);
                        }
                        conversion = programNode.convertToJava(className);
                    }
                    case "C" -> conversion = programNode.convertToC();
                    case "Python" -> conversion = programNode.convertToPython(0);
                    default -> throw new Exception("Invalid input. Valid input:\njava Jott.java {input file} {output file} {output language}");
                }
                System.out.println(conversion);
                writer.write(conversion);
                writer.close();
            }
            else {
                System.err.println("This file already exists");
            }
        }
    }
}
