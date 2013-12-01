package tests.webscrapper;

import static org.junit.Assert.*;

import org.junit.Test;

import webscraper.WebScraper;

public class WebScraperTest {
	@Test
	public void testIsUrl() {
		assertTrue(WebScraper.isURL("http://www.google.com.br"));
		assertFalse(WebScraper.isURL("/home/thiago/file"));
		assertFalse(WebScraper.isURL("C:/thiago/file"));
	}
	
	@Test
	public void testIsSentenceDelimiter() {
		assertTrue(WebScraper.isSentenceDelimiter("meta"));
		assertTrue(WebScraper.isSentenceDelimiter("title"));
		assertTrue(WebScraper.isSentenceDelimiter("body"));
		assertTrue(WebScraper.isSentenceDelimiter("div"));
		assertTrue(WebScraper.isSentenceDelimiter("section"));
		assertTrue(WebScraper.isSentenceDelimiter("header"));
		assertTrue(WebScraper.isSentenceDelimiter("footer"));
		assertTrue(WebScraper.isSentenceDelimiter("article"));
		assertTrue(WebScraper.isSentenceDelimiter("aside"));
		assertTrue(WebScraper.isSentenceDelimiter("nav"));
		assertTrue(WebScraper.isSentenceDelimiter("li"));
		assertTrue(WebScraper.isSentenceDelimiter("dt"));
		assertTrue(WebScraper.isSentenceDelimiter("dd"));
		assertTrue(WebScraper.isSentenceDelimiter("button"));
		assertTrue(WebScraper.isSentenceDelimiter("p"));
		assertTrue(WebScraper.isSentenceDelimiter("h1"));
		assertTrue(WebScraper.isSentenceDelimiter("h2"));
		assertTrue(WebScraper.isSentenceDelimiter("h3"));
		assertTrue(WebScraper.isSentenceDelimiter("h4"));
		assertTrue(WebScraper.isSentenceDelimiter("h5"));
		assertTrue(WebScraper.isSentenceDelimiter("h6"));
		assertTrue(WebScraper.isSentenceDelimiter("td"));
		assertTrue(WebScraper.isSentenceDelimiter("th"));
		assertTrue(WebScraper.isSentenceDelimiter("tr"));
		assertTrue(WebScraper.isSentenceDelimiter("tbody"));
		assertTrue(WebScraper.isSentenceDelimiter("table"));
		assertTrue(WebScraper.isSentenceDelimiter("textarea"));
		assertTrue(WebScraper.isSentenceDelimiter("option"));
		assertTrue(WebScraper.isSentenceDelimiter("label"));

		assertFalse(WebScraper.isSentenceDelimiter("span"));
		assertFalse(WebScraper.isSentenceDelimiter("strong"));
		assertFalse(WebScraper.isSentenceDelimiter("i"));
		assertFalse(WebScraper.isSentenceDelimiter("b"));
		assertFalse(WebScraper.isSentenceDelimiter("em"));
	}
}
