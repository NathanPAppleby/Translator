

import nodes.ProgramNode;
import provided.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length==2) {
            String jottFile = args[0];

            ArrayList<Token> tokenList = JottTokenizer.tokenize(jottFile);

            ProgramNode programNode = ProgramNode.parseProgramNode(tokenList);

            if(programNode.validateTree()) {
                System.out.println(programNode.convertToJott());

                File outFile = new File(args[1]);
                if (outFile.createNewFile()) {
                    FileWriter writer = new FileWriter(args[1]);
                    writer.write(programNode.convertToJott());
                    writer.close();
                }
            }

        }
        else{
            throw new Exception("Invalid input. Valid input:\njava Main.java {input file} {output file");
        }

    }

}
