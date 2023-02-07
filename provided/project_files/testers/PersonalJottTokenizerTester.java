package provided.project_files.testers;

/*
 * Basic Jott Token Printer
 *
 * Alter personalTest.jott to change the output.
 *
 * @Author: Andrew Silverschotz (DJS3634)
 */

import provided.JottTokenizer;
import provided.Token;

import java.util.ArrayList;



public class PersonalJottTokenizerTester {
    public static void main(String[] args) {
        ArrayList<Token> result = JottTokenizer.tokenize("tokenizerTestCases/personalTest.jott");
        for (Token token : result){
            System.out.println("Token Type: " + token.getTokenType() +
                    "\nToken Contents: " + token.getToken());
        }
    }
}