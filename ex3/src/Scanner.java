import java_cup.runtime.*;
import java.util.*;
import exceptions.*;

public class Scanner {
	/** lookahead token string */
	protected static String lookahead;
	/** lookahead token type */
	protected static String type;
	/** scanner result from jflex */
	protected static OberonScanner scannerResult;
	/** all tokens */
	protected static ArrayList <String> tokens;
	/** index in tokens */
	protected static int index;
	/** we use a SymbolFactory to generate Symbols */
	private SymbolFactory symbolFactory = new DefaultSymbolFactory();

	public Scanner(OberonScanner scanner) throws java.io.IOException, LexicalException {
		tokens = new ArrayList<>();
		index = 0;

		while (!scanner.yyatEOF()) {
			String workClass = scanner.yylex();
			switch (workClass) {
				case "Integer":
					tokens.add("INT");
					break;
				case "Comment":
					break;
				case "Identifier":
					tokens.add("IDENTIFIER");
				default:
					tokens.add(scanner.yytext());
			}
		}
	}

	/**
	 * advance input by one character
	 * @throws java.io.IOException
	 */
	protected static void advance() throws java.io.IOException {
		lookahead = tokens.get(index);
		index++;
	}

	/**
	 * initialize the scanner
	 * @throws Exception Lexical Excepiton
	 */
	public static void init() throws Exception {
	}

	/**
	 * recognize and return the next complete token
	 * @return Symbol, the next complete token
	 * @throws java.io.IOException IOException
	 */
	public Symbol next_token() throws java.io.IOException {
		switch (lookahead) {
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
			case "SEMI":
				advance();
				return symbolFactory.newSymbol("SEMI", Symbols.SEMI);
			case "DOT":
				advance();
				return symbolFactory.newSymbol("DOT", Symbols.DOT);
			case "EQUAL":
				advance();
				return symbolFactory.newSymbol("EQUAL", Symbols.EQUAL);
			case "COLON":
				advance();
				return symbolFactory.newSymbol("COLON", Symbols.COLON);
			case "LEFTPAR":
				advance();
				return symbolFactory.newSymbol("LEFTPAR", Symbols.LEFTPAR);
			case "RIGHTPAR":
				advance();
				return symbolFactory.newSymbol("RIGHTPAR", Symbols.RIGHTPAR);
			case "COMMA":
				advance();
				return symbolFactory.newSymbol("COMMA", Symbols.COMMA);
			case "COLONEQ":
				advance();
				return symbolFactory.newSymbol("COLONEQ", Symbols.COLONEQ);
			case "NOTEQUAL":
				advance();
				return symbolFactory.newSymbol("NOTEQUAL", Symbols.NOTEQUAL);
			case "LESS":
				advance();
				return symbolFactory.newSymbol("LESS", Symbols.LESS);
			case "LEQ":
				advance();
				return symbolFactory.newSymbol("LEQ", Symbols.LEQ);
			case "GREAT":
				advance();
				return symbolFactory.newSymbol("GREAT", Symbols.GREAT);
			case "GEQ":
				advance();
				return symbolFactory.newSymbol("GEQ", Symbols.GEQ);
			case "ADD":
				advance();
				return symbolFactory.newSymbol("ADD", Symbols.ADD);
			case "UADD":
				advance();
				return symbolFactory.newSymbol("UADD", Symbols.UADD);
			case "MINUS":
				advance();
				return symbolFactory.newSymbol("MINUS", Symbols.MINUS);
			case "UMINUS":
				advance();
				return symbolFactory.newSymbol("UMINUS", Symbols.UMINUS);
			case "OR":
				advance();
				return symbolFactory.newSymbol("OR", Symbols.OR);
			case "MUL":
				advance();
				return symbolFactory.newSymbol("MUL", Symbols.MUL);
			case "DIV":
				advance();
				return symbolFactory.newSymbol("DIV", Symbols.DIV);
			case "MOD":
				advance();
				return symbolFactory.newSymbol("MOD", Symbols.MOD);
			case "AND":
				advance();
				return symbolFactory.newSymbol("AND", Symbols.AND);
			case "NOT":
				advance();
				return symbolFactory.newSymbol("NOT", Symbols.NOT);
			case "IDENTIFIER":
				advance();
				return symbolFactory.newSymbol("IDENTIFIER", Symbols.IDENTIFIER);
			case "INT":
				advance();
				return symbolFactory.newSymbol("INT", Symbols.INT);
			case "BOOL":
				advance();
				return symbolFactory.newSymbol("BOOL", Symbols.BOOL);
			case "NUMBER":
				advance();
				return symbolFactory.newSymbol("NUMBER", Symbols.NUMBER);
			case "READ":
				advance();
				return symbolFactory.newSymbol("READ", Symbols.READ);
			case "WRITE":
				advance();
				return symbolFactory.newSymbol("WRITE", Symbols.WRITE);
			case "WRITELN":
				advance();
				return symbolFactory.newSymbol("WRITELN", Symbols.WRITELN);
			case " ":
				return symbolFactory.newSymbol("EOF", Symbols.EOF);
			default:
				advance();
				break;
		}
		return symbolFactory.newSymbol("EOF", Symbols.EOF);
	}
};