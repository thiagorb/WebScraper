package webscraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import webscraper.behaviors.*;
import webscraper.parser.HtmlElementNode;
import webscraper.parser.HtmlParser;
import webscraper.parser.TextExtractor;

public class WebScraper {
	private final String[] words;
	private EnumSet<OptionalParameter> optionalParameters;
	private List<WebScraperListener> listeners = new LinkedList<>();
	private TextExtractor extractor;
	private VerbosityBehavior verbosityBehavior; 
	private SentenceExtractBehavior sentenceExtractBehavior;
	private CountWordsBehavior countWordsBehavior;
	private CountCharsBehavior countCharsBehavior;

	public WebScraper(String[] words, EnumSet<OptionalParameter> optionalParameters) {
		this.words = words;
		this.optionalParameters = optionalParameters;
		
		extractor = new TextExtractor();
		extractor.addIgnoreTextTags("script");
		extractor.addIgnoreTextTags("style");

		verbosityBehavior = hasParameter(OptionalParameter.VERBOSE) ? new VerbosityVerbose(this) : new VerbositySilent();
		sentenceExtractBehavior = hasParameter(OptionalParameter.EXTRACT_SENTENCES) ? new SentenceExtractPerform(this) : new SentenceExtractIgnore();
		countWordsBehavior = hasParameter(OptionalParameter.COUNT_WORDS) ? new CountWordsPerform(this) : new CountWordsIgnore();
		countCharsBehavior = hasParameter(OptionalParameter.COUNT_CHARS) ? new CountCharsPerform(this) : new CountCharsIgnore();
	}

	public String[] getWords() {
		return this.words;
	}

	public boolean hasParameter(OptionalParameter parameter) {
		return this.optionalParameters.contains(parameter);
	}
	
	public static boolean isURL(String path) {
		URL url;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			return false;
		}
		return url.getProtocol() != "" && url.getHost() != "";
	}
	
	public void proccess(URL url) throws IOException, ParseException {
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder source = new StringBuilder();
        int bytesRead;
        char[] buffer = new char[4096];
        while ((bytesRead = reader.read(buffer)) > 0) {
        	source.append(new String(buffer, 0, bytesRead));
        }
        
		verbosityBehavior.startProcessing();
		HtmlElementNode html = new HtmlParser(new StringReader(source.toString())).parse();
		verbosityBehavior.endProcessing();

		verbosityBehavior.startScraping();
    	String text = extractor.getText(html);
    	countWordsBehavior.countWords(text);
    	countCharsBehavior.countChars(text);
        sentenceExtractBehavior.extractSentences(html);
		verbosityBehavior.endScraping();
	}
	
	public static boolean isSentenceDelimiter(String tagName) {
		switch (tagName.toLowerCase()) {
		case "html":
		case "meta":
		case "title":
		case "body":
		case "div":
		case "section":
		case "header":
		case "footer":
		case "article":
		case "aside":
		case "nav":
		case "li":
		case "dt":
		case "dd":
		case "button":
		case "p":
		case "h1":
		case "h2":
		case "h3":
		case "h4":
		case "h5":
		case "h6":
		case "td":
		case "th":
		case "tr":
		case "tbody":
		case "table":
		case "textarea":
		case "option":
		case "label":
			return true;
		}
		return false;
	}
	
	public void addListener(WebScraperListener listener) {
		listeners.add(listener);
	}

	public List<WebScraperListener> getListeners() {
		return listeners;
	}

	public TextExtractor getExtractor() {
		return extractor;
	}
}
