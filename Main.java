

import provided.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String jottFile = args[0];

        ArrayList<Token> tokenList = JottTokenizer.tokenize(jottFile);

        try {
            JottTree tree = JottParser.parse(tokenList);

            System.out.println(tree.convertToJott());
        }
        catch(Exception e) {

        }

    }

}
