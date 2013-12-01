package webscraper.parser;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webscraper.lexer.HtmlLexer;
import webscraper.lexer.HtmlToken;
import webscraper.lexer.HtmlTokenType;

public class HtmlParser {
	private HtmlLexer lexer;
	private HtmlToken currentToken;

	public HtmlParser(Reader reader) {
		this.lexer = new HtmlLexer(reader);
	}

	public HtmlElementNode parse() throws IOException, ParseException {
		return parseStart();
	}

	private HtmlElementNode parseStart() throws IOException, ParseException {
		skipComments();
		switch (getToken().getType()) {
		case DOCTYPE:
			nextToken();
			skipComments();
			return parseStart();
		case TAG_START:
			return (HtmlElementNode) parseNode();
		case TEXT:
			if (HtmlLexer.whiteSpacePattern.matcher(getToken().getLexeme()).find()) {
				nextToken();
				return parseStart();
			}
		default:
			throw new ParseException("Unexpected token: " + getToken().getLexeme(), getToken().getOffset());
		} 
	}
	
	private HtmlNode parseNode() throws IOException, ParseException {
		switch (getToken().getType()) {
		case TAG_SELF_CONTAINED:
			return new HtmlElementNode(tagNameFromLexeme(nextToken().getLexeme()));
		case TEXT:
			return new HtmlTextNode(nextToken().getLexeme());
		default:
			break;
		}
		HtmlToken tagStart = nextToken();
		String tagName = tagNameFromLexeme(tagStart.getLexeme());
		HtmlElementNode node = new HtmlElementNode(tagName);
		while (getToken() != null && getToken().getType() != HtmlTokenType.TAG_END) {
			node.addChild(parseNode());
		}
		if (getToken() == null)
			throw new ParseException("End of input before closing tag", tagStart.getOffset());
		String endTagName = tagNameFromLexeme(getToken().getLexeme());
		if (tagName.equals(endTagName))
			nextToken();
		else //Warn about unclosed tag
			;
		return node;
	}
	
	public static String tagNameFromLexeme(String lexeme) {
		Pattern pattern = Pattern.compile("</?([a-zA-Z0-9]+) ?");
		Matcher matcher = pattern.matcher(lexeme); 
		return matcher.find()? matcher.group(1): "";
	}

	private void skipComments() throws IOException, ParseException {
		while (getToken() != null && getToken().getType() == HtmlTokenType.COMMENT)
			nextToken();
	}

	private HtmlToken getToken() throws IOException, ParseException {
		if (currentToken == null) {
			currentToken = lexer.readToken();
		}
		return currentToken;
	}
	
	private HtmlToken nextToken() {
		HtmlToken current = currentToken;
		currentToken = null;
		return current;
	}
}
