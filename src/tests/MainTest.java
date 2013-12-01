package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import webscraper.Main;
import webscraper.OptionalParameter;
import webscraper.WebScraper;

public class MainTest {
	@Test
	public void testValidOptionalParameters() {
		assertEquals(OptionalParameter.VERBOSE, Main.parseOptionalParameter("-v"));
		assertEquals(OptionalParameter.COUNT_WORDS, Main.parseOptionalParameter("-w"));
		assertEquals(OptionalParameter.COUNT_CHARS, Main.parseOptionalParameter("-c"));
		assertEquals(OptionalParameter.EXTRACT_SENTENCES, Main.parseOptionalParameter("-e"));
		assertEquals(OptionalParameter.VERBOSE, Main.parseOptionalParameter("-V"));
	}
	
	@Test
	public void testInvalidOptionalParameters() {
		try {
			Main.parseOptionalParameter("v");
			fail();
		} catch(IllegalArgumentException e) { }
		
		try {
			Main.parseOptionalParameter("-vv");
			fail();
		} catch(IllegalArgumentException e) { }

		try {
			Main.parseOptionalParameter("-x");
			fail();
		} catch(IllegalArgumentException e) { }
		
		try {
			Main.parseOptionalParameter("-");
			fail();
		} catch(IllegalArgumentException e) { }
	}
	
	@Test
	public void testHandleParametersSimple() throws MalformedURLException {
		String[] args = new String[] { "http://test.url", "test,words" };
		WebScraper webScraper = new Main(args).webScraper;
		assertArrayEquals(new String[] { "test", "words" }, webScraper.getWords());
	}
	
	@Test
	public void testParseUrls() {
		String testUrls = "http://www.google.com.br/\nhttp://www.uol.com.br"; 
		BufferedReader reader = new BufferedReader(new StringReader(testUrls));
		try {
			List<String> urls = Main.parseUrls(reader);
			String[] urlsArray = new String[urls.size()];
			urls.toArray(urlsArray);
			assertArrayEquals(new String[] { "http://www.google.com.br/", "http://www.uol.com.br" }, urlsArray);
		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	public void testHandleParametersOptionalParameters() throws MalformedURLException {
		String[] args = new String[] { "http://test.url", "thiago,barcala", "-v", "-w" };
		WebScraper webScraper = new Main(args).webScraper;
		assertArrayEquals(new String[] { "thiago", "barcala" }, webScraper.getWords());
		assertTrue(webScraper.hasParameter(OptionalParameter.VERBOSE));
		assertTrue(webScraper.hasParameter(OptionalParameter.COUNT_WORDS));
		assertFalse(webScraper.hasParameter(OptionalParameter.COUNT_CHARS));
		assertFalse(webScraper.hasParameter(OptionalParameter.EXTRACT_SENTENCES));
	}
}
