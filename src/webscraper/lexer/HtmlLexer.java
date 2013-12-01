package webscraper.lexer;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Pattern;

public class HtmlLexer {
	public static final Pattern whiteSpacePattern = Pattern.compile("[ \t\r\n]+");
	private Character currentChar = null;
	private int currentPosition = 0;
	private Reader reader;

	public HtmlLexer(Reader reader) {
		this.reader = reader;
	}
	
	public HtmlToken readToken() throws IOException, ParseException {
		if (getCurrentChar() == null)
			return null;
		
		if (getCurrentChar() != '<') 
			return readText();

		return readTag();
	}
	
	private HtmlToken readTag() throws IOException, ParseException {
		int position = currentPosition;
		String content = readUntilChar('>');
		if (getCurrentChar() != '>')
			throw new ParseException("Unexpected end of input", currentPosition);
		nextChar();
		
		HtmlTokenType type;
		if (content.toLowerCase().startsWith("<!doctype ")) {
			type = HtmlTokenType.DOCTYPE;
		} else if (content.toLowerCase().startsWith("</")) {
			type = HtmlTokenType.TAG_END;
		} else if (content.toLowerCase().endsWith("/>")) {
			type = HtmlTokenType.TAG_SELF_CONTAINED;
		} else {
			type = HtmlTokenType.TAG_START;
		}
		return new HtmlToken(type, content + ">", position);
	}

	private HtmlToken readText() throws IOException {
		int position = currentPosition;
		String text = readUntilChar('<');
		return new HtmlToken(HtmlTokenType.TEXT, text, position);
	}
	
	private String readUntilChar(char c) throws IOException {
		StringBuilder string = new StringBuilder();
		Character next;
		while ((next = getCurrentChar()) != null && next != c) {
			string.append(nextChar());
		}
		return string.toString();
	}
	
	private Character getCurrentChar() throws IOException {
		if (currentChar == null) {
			char[] buffer = new char[1];
			int charsRead = reader.read(buffer);
			currentPosition++;
			if (charsRead > 0) currentChar = buffer[0]; 
		}
		return currentChar;
	}
	
	private Character nextChar() throws IOException {
		Character current = currentChar;
		currentChar = null;
		return current;
	}
}
