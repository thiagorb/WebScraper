package webscraper.lexer;

public class HtmlToken {
	private final HtmlTokenType type;
	private final String lexeme;
	private final int offset;
	
	public HtmlToken(HtmlTokenType type, String lexeme, int offset) {
		this.type = type;
		this.lexeme = lexeme;
		this.offset = offset;
	}

	public HtmlTokenType getType() {
		return type;
	}

	public String getLexeme() {
		return lexeme;
	}

	public int getOffset() {
		return offset;
	}
}
