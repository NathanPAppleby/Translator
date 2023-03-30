

import provided.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length==2) {
            String jottFile = args[0];

            ArrayList<Token> tokenList = JottTokenizer.tokenize(jottFile);

            try {
                JottTree tree = JottParser.parse(tokenList);

                System.out.println(tree.convertToJott());

                File outFile = new File(args[1]);
                if (outFile.createNewFile()) {
                    FileWriter writer = new FileWriter(args[1]);
                    writer.write(tree.convertToJott());
                    writer.close();
                }
            } catch (Exception e) {

            }
        }
        else{
            throw new Exception("Invalid input. Valid input:\njava Main.java {input file} {output file");
        }

    }

}
