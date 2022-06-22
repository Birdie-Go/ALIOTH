import java_cup.runtime.*;
import java.util.*;
import exceptions.*;

/**
 * 扫描器，用于将JFlex扫描得到的token转化为cup所需要的Symbol形式
 */
public class MyScanner {
	/** lookahead token string */
	protected static String lookahead;
	/** lookahead token type */
	protected static String type;
	/** scanner result from jflex */
	protected static OberonScanner scannerResult;
	/** all tokens */
	protected static ArrayList <String> tokens;
	/** token's line */
	protected static ArrayList <Integer> lines;
	/** token's column */
	protected static ArrayList <Integer> columns;
	/** index in tokens */
	protected static int index;
	/** we use a SymbolFactory to generate Symbols */
	private SymbolFactory symbolFactory = new DefaultSymbolFactory();

	/**
	 * 将JFlex得到token及其行列号存储在ArrayList中
	 * @param scanner JFelx's scanner
	 * @throws java.io.IOException IOException
	 * @throws LexicalException LexicalException
	 */
	public MyScanner(OberonScanner scanner) throws java.io.IOException, LexicalException {
		tokens = new ArrayList<>();
		lines = new ArrayList<>();
		columns = new ArrayList<>();
		index = 0;
		lookahead = new String();

		while (!scanner.yyatEOF()) {
			String workClass = scanner.yylex();
			switch (workClass) {
				case "Integer":
					tokens.add("NUMBER");
					break;
				case "Comment":
					break;
				case "Identifier":
					tokens.add("IDENTIFIER");
					break;
				default:
					tokens.add(scanner.yytext());
			}
			lines.add(scanner.getLine());
			columns.add(scanner.getColumn());
		}
		tokens.add("_");
		lines.add(scanner.getLine());
		columns.add(scanner.getColumn());

		// for (int i = 0; i < tokens.size(); i++)
		// System.out.print(tokens.get(i) + "  ");
		// System.out.println();
	}

	/**
	 * advance input by one character
	 * @throws java.io.IOException IOException
	 */
	protected static void advance() throws java.io.IOException {
		// System.out.print(index);
		// System.out.print(" and ");
		// System.out.print(tokens.size());
		lookahead = tokens.get(index);
		// System.out.println("lookahead : \"" + lookahead + "\"");
		index++;
	}

	/**
	 * get the token's line
	 * @return int, token's line
	 */
	protected static int getLine() {
		return lines.get(index-1);
	}

	/**
	 * get the token's column
	 * @return int, token's column
	 */
	protected static int getColumn() {
		return columns.get(index-1);
	}

	/**
	 * initialize the scanner
	 * @throws Exception Lexical Excepiton
	 */
	public static void init() throws Exception {
		advance();
	}

	/**
	 * recognize and return the next complete token
	 * @return Symbol, the next complete token
	 * @throws java.io.IOException IOException
	 */
	public Symbol next_token() throws java.io.IOException {
		switch (lookahead.toUpperCase()) {
			case "MODULE":
				advance();
				return symbolFactory.newSymbol("MODULE", Symbols.MODULE);
			case "BEGIN":
				advance();
				return symbolFactory.newSymbol("BEGIN", Symbols.BEGIN);
			case "END":
				advance();
				return symbolFactory.newSymbol("END", Symbols.END);
			case "CONST":
				advance();
				return symbolFactory.newSymbol("CONST", Symbols.CONST);
			case "TYPE":
				advance();
				return symbolFactory.newSymbol("TYPE", Symbols.TYPE);
			case "VAR":
				advance();
				return symbolFactory.newSymbol("VAR", Symbols.VAR);
			case "PROCEDURE":
				advance();
				return symbolFactory.newSymbol("PROCEDURE", Symbols.PROCEDURE);
			case "RECORD":
				advance();
				return symbolFactory.newSymbol("RECORD", Symbols.RECORD);
			case "ARRAY":
				advance();
				return symbolFactory.newSymbol("ARRAY", Symbols.ARRAY);
			case "OF":
				advance();
				return symbolFactory.newSymbol("OF", Symbols.OF);
			case "WHILE":
				advance();
				return symbolFactory.newSymbol("WHILE", Symbols.WHILE);
			case "DO":
				advance();
				return symbolFactory.newSymbol("DO", Symbols.DO);
			case "IF":
				advance();
				return symbolFactory.newSymbol("IF", Symbols.IF);
			case "THEN":
				advance();
				return symbolFactory.newSymbol("THEN", Symbols.THEN);
			case "ELSIF":
				advance();
				return symbolFactory.newSymbol("ELSIF", Symbols.ELSIF);
			case "ELSE":
				advance();
				return symbolFactory.newSymbol("ELSE", Symbols.ELSE);
			case ";":
				advance();
				return symbolFactory.newSymbol("SEMI", Symbols.SEMI);
			case ".":
				advance();
				return symbolFactory.newSymbol("DOT", Symbols.DOT);
			case "=":
				advance();
				return symbolFactory.newSymbol("EQUAL", Symbols.EQUAL);
			case ":":
				advance();
				return symbolFactory.newSymbol("COLON", Symbols.COLON);
			case "(":
				advance();
				return symbolFactory.newSymbol("LEFTPAR", Symbols.LEFTPAR);
			case ")":
				advance();
				return symbolFactory.newSymbol("RIGHTPAR", Symbols.RIGHTPAR);
			case "[":
				advance();
				return symbolFactory.newSymbol("LEFTMIDPAR", Symbols.LEFTMIDPAR);
			case "]":
				advance();
				return symbolFactory.newSymbol("RIGHTMIDPAR", Symbols.RIGHTMIDPAR);
			case ",":
				advance();
				return symbolFactory.newSymbol("COMMA", Symbols.COMMA);
			case ":=":
				advance();
				return symbolFactory.newSymbol("COLONEQ", Symbols.COLONEQ);
			case "#":
				advance();
				return symbolFactory.newSymbol("NOTEQUAL", Symbols.NOTEQUAL);
			case "<":
				advance();
				return symbolFactory.newSymbol("LESS", Symbols.LESS);
			case "<=":
				advance();
				return symbolFactory.newSymbol("LEQ", Symbols.LEQ);
			case ">":
				advance();
				return symbolFactory.newSymbol("GREAT", Symbols.GREAT);
			case ">=":
				advance();
				return symbolFactory.newSymbol("GEQ", Symbols.GEQ);
			case "+":
				advance();
				return symbolFactory.newSymbol("ADD", Symbols.ADD);
			case "-":
				advance();
				return symbolFactory.newSymbol("MINUS", Symbols.MINUS);
			case "OR":
				advance();
				return symbolFactory.newSymbol("OR", Symbols.OR);
			case "*":
				advance();
				return symbolFactory.newSymbol("MUL", Symbols.MUL);
			case "DIV":
				advance();
				return symbolFactory.newSymbol("DIV", Symbols.DIV);
			case "MOD":
				advance();
				return symbolFactory.newSymbol("MOD", Symbols.MOD);
			case "&":
				advance();
				return symbolFactory.newSymbol("AND", Symbols.AND);
			case "~":
				advance();
				return symbolFactory.newSymbol("NOT", Symbols.NOT);
			case "IDENTIFIER":
				advance();
				return symbolFactory.newSymbol("IDENTIFIER", Symbols.IDENTIFIER);
			case "INTEGER":
				advance();
				return symbolFactory.newSymbol("INT", Symbols.INT);
			case "BOOLEAN":
				advance();
				return symbolFactory.newSymbol("BOOL", Symbols.BOOL);
			case "READ":
				advance();
				return symbolFactory.newSymbol("READ", Symbols.READ);
			case "WRITE":
				advance();
				return symbolFactory.newSymbol("WRITE", Symbols.WRITE);
			case "WRITELN":
				advance();
				return symbolFactory.newSymbol("WRITELN", Symbols.WRITELN);
			case "NUMBER":
				advance();
				return symbolFactory.newSymbol("NUMBER", Symbols.NUMBER);
			case "_":
				return symbolFactory.newSymbol("EOF", Symbols.EOF);
			default:
				advance();
				break;
		}
		return symbolFactory.newSymbol("EOF", Symbols.EOF);
	}
};