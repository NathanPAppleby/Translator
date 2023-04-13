

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
            System.out.println(programNode.convertToJott());

            File outFile = new File(args[1]);
            if (outFile.createNewFile()) {
                FileWriter writer = new FileWriter(args[1]);
                switch (language) {
                    case "Jott":
                        writer.write(programNode.convertToJott());
                        break;
                    case "Java":
                        String className = "";
                        for (int i = 0; i < args[1].length(); i++) {
                            if(args[1].charAt(i) == '.') {
                                break;
                            }
                            className += args[1].charAt(i);
                        }
                        writer.write(programNode.convertToJava(className));
                        break;
                    case "C":
                        writer.write(programNode.convertToC());
                        break;
                    case "Python":
                        writer.write(programNode.convertToPython(0));
                        break;
                    default:
                        throw new Exception("Invalid input. Valid input:\njava Jott.java {input file} {output file} {output language}");
                }
                writer.close();
            }
        }
    }
}
