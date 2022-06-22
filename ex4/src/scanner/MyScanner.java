package scanner;

import exceptions.*;

/**
 * Scanner, connect the OberonScanner with the parser
 */
public class MyScanner {
	/** JFlex OberonScanner */
	private OberonScanner scanner;

	/**
	 * 构造函数
	 * @param _scanner JFlex OberonScanner
	 * @throws java.io.IOException IOException
	 * @throws LexicalException LexicalException
	 */
	public MyScanner(OberonScanner _scanner) throws java.io.IOException, LexicalException {
		scanner = _scanner;
	}

	/**
	 * 获取下一个token
	 * @return Symbols next token
	 * @throws java.io.IOException IOException
	 * @throws LexicalException LexicalException
	 */
	public Symbols next_token() throws java.io.IOException, LexicalException {
		while (!scanner.yyatEOF()) {
			String workClass = scanner.yylex();
			int line = scanner.getLine();
			int column = scanner.getColumn();
			String value = scanner.yytext();
			switch (workClass) {
				case "Integer":
					return new Symbols("NUMBER", line, column, value.toUpperCase());
				case "Comment":
					break;
				case "Identifier":
					return new Symbols("IDENTIFIER", line, column, value.toUpperCase());
				default:
					return new Symbols(value.toUpperCase(), line, column);
			}
		}
		return new Symbols("_", scanner.getLine(), scanner.getColumn());
	}
};