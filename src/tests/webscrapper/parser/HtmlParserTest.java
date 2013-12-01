package tests.webscrapper.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.junit.Test;

import webscraper.parser.HtmlElementNode;
import webscraper.parser.HtmlParser;

public class HtmlParserTest {
	@Test
	public void testSimpleHtml() throws IOException, ParseException {
		HtmlElementNode html;

		html = new HtmlParser(new StringReader("<!doctype html><html><head><title>test html</title></head></html>")).parse();
		assertEquals("<html><head><title>test html</title></head></html>", html.getHtml());

		html = new HtmlParser(new StringReader("<!doctype html><html><head></head><body><div>test</div></body></html>")).parse();
		assertEquals("<html><head></head><body><div>test</div></body></html>", html.getHtml());

		html = new HtmlParser(new StringReader("<!doctype html><html><head></head></html>")).parse();
		assertEquals("<html><head></head></html>", html.getHtml());
	}
	
	@Test
	public void testSimpleText() throws IOException, ParseException {
		HtmlElementNode html;

		html = new HtmlParser(new StringReader("<!doctype html><html><head><title>test html</title></head></html>")).parse();
		assertEquals("test html", html.getText());

		html = new HtmlParser(new StringReader("<!doctype html><html><head></head><body><div>test</div></body></html>")).parse();
		assertEquals("test", html.getText());

		html = new HtmlParser(new StringReader("<!doctype html><html><head></head></html>")).parse();
		assertEquals("", html.getText());
	}
	
	@Test
	public void testTagNameFromLexeme() {
		assertEquals("html", HtmlParser.tagNameFromLexeme("<html>"));
		assertEquals("input", HtmlParser.tagNameFromLexeme("<input type=\"text\">"));
		assertEquals("html", HtmlParser.tagNameFromLexeme("</html>"));
		assertEquals("h6", HtmlParser.tagNameFromLexeme("<h6>"));
		assertEquals("div", HtmlParser.tagNameFromLexeme("<div class=\"test-class\">"));
	}
}
