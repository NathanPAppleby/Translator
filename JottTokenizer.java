package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
		ArrayList<Token> outList = new ArrayList<>();
		int lineNum = 0;
		File file = new File(filename);
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String next_line = scan.nextLine();
				lineNum++;
				for (int i = 0; i < next_line.length(); i++) {
					char next_char = next_line.charAt(i);
					if (next_char == '#')
					{
						break;
					} else if (next_char == ',')
					{
						Token newToken = new Token(",", filename, lineNum, TokenType.COMMA);
						outList.add(newToken);
					} else if (next_char == ']') {
						Token newToken = new Token("]", filename, lineNum, TokenType.R_BRACKET);
						outList.add(newToken);
					} else if (next_char == '[') {
						Token newToken = new Token("[", filename, lineNum, TokenType.L_BRACKET);
						outList.add(newToken);
					} else if (next_char == '}') {
						Token newToken = new Token("}", filename, lineNum, TokenType.R_BRACE);
						outList.add(newToken);
					} else if (next_char == '{') {
						Token newToken = new Token("{", filename, lineNum, TokenType.L_BRACE);
						outList.add(newToken);
					} else if (next_char == '=') {
						char look_ahead = next_line.charAt(i + 1);
						Token newToken;
						if (look_ahead == '=') {
							newToken = new Token("==", filename, lineNum, TokenType.REL_OP);
						} else {
							newToken = new Token("=", filename, lineNum, TokenType.ASSIGN);
						}
						outList.add(newToken);
					} else if (next_char == '<') {
						char look_ahead = next_line.charAt(i + 1);
						Token newToken;
						if (look_ahead == '=') {
							newToken = new Token("<=", filename, lineNum, TokenType.REL_OP);
						} else {
							newToken = new Token("<", filename, lineNum, TokenType.REL_OP);
						}
						outList.add(newToken);
					} else if (next_char == '>') {
						char look_ahead = next_line.charAt(i + 1);
						Token newToken;
						if (look_ahead == '=') {
							newToken = new Token(">=", filename, lineNum, TokenType.REL_OP);
						} else {
							newToken = new Token(">", filename, lineNum, TokenType.REL_OP);
						}
						outList.add(newToken);
					} else if (next_char == '*' || next_char == '/' || next_char == '+' || next_char == '-') {
						Token newToken = new Token("" + next_char, filename, lineNum, TokenType.MATH_OP);
						outList.add(newToken);
					} else if (next_char == ';') {
						Token newToken = new Token(";", filename, lineNum, TokenType.SEMICOLON);
						outList.add(newToken);
					} else if (next_char == '.') {
						int j = i + 1;
						char look_ahead = next_line.charAt(j);
						String newTok = ".";
						if (!isDigit(look_ahead)) {
							System.err.printf("Syntax Error:\nInvalid Token \".%c\"\n%s:%d", look_ahead, filename, lineNum);
						}
						while (isDigit(look_ahead)) {
							newTok += look_ahead;
							j++;
							look_ahead = next_line.charAt(j);
						}
						Token newToken = new Token(newTok, filename, lineNum, TokenType.NUMBER);
						outList.add(newToken);
					} else if (isDigit(next_char)) {
						String newTok = "" + next_char;
						int j = i + 1;
						char look_ahead = next_line.charAt(j);
						while (isDigit(look_ahead)) {
							newTok += look_ahead;
							j++;
							look_ahead = next_line.charAt(j);
						}
						if (look_ahead == '.') {
							newTok += ".";
							if (!isDigit(look_ahead)) {
								System.err.printf("Syntax Error:\nInvalid Token \"%s%c\"\n%s:%d", newTok, look_ahead, filename, lineNum);
							}
							while (isDigit(look_ahead)) {
								newTok += look_ahead;
								j++;
								look_ahead = next_line.charAt(j);
							}
						}
						Token newToken = new Token(newTok, filename, lineNum, TokenType.NUMBER);
						outList.add(newToken);
					} else if (isLetter(next_char))
					{
						String newTok = "" + next_char;
						int j = i + 1;
						char look_ahead = next_line.charAt(j);
						while(isLetter(look_ahead) || isDigit(look_ahead)) {
							newTok += look_ahead;
							j++;
							look_ahead = next_line.charAt(j);
						}
						Token newToken = new Token(newTok, filename, lineNum, TokenType.ID_KEYWORD);
						outList.add(newToken);
					}
					else if (next_char == ':') {
						Token newToken = new Token(":", filename, lineNum, TokenType.COLON);
						outList.add(newToken);
					} else if (next_char == '!') {
						if (next_line.charAt(i + 1) == '=') {
							Token newToken = new Token("!=", filename, lineNum, TokenType.REL_OP);
							outList.add(newToken);
						} else {
							System.err.printf("Syntax Error:\nInvalid Token \" ! \"\n%s:%d", filename, lineNum);
						}
					} else if (next_char == '"') {
						String newTok = "\"";
						int j = i + 1;
						char look_ahead = next_line.charAt(j);
						while(isLetter(look_ahead) || isDigit(look_ahead) || look_ahead == ' ') {
							newTok += look_ahead;
							j++;
							look_ahead = next_line.charAt(j);
						}
						if (look_ahead == '"') {
							Token newToken = new Token(newTok, filename, lineNum, TokenType.ID_KEYWORD);
							outList.add(newToken);
						} else {
							System.err.printf("Syntax Error:\nInvalid Token \"%s%c\"\n%s:%d", newTok, look_ahead, filename, lineNum);
						}
					}


				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return outList;
	}

	private static boolean isDigit(char c) {
		return switch (c) {
			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
			default -> false;
		};
	}

	private static boolean isLetter(char c) {
		return switch (c) {
			case 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f' -> true;
			case 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l' -> true;
			case 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r' -> true;
			case 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x' -> true;
			case 'Y', 'y', 'Z', 'z' -> true;
			default -> false;
		};
	}
}