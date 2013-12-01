package webscraper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Main implements WebScraperListener {
	public List<URL> urls = new ArrayList<>();
	public WebScraper webScraper;
	private long totalWordsCount = 0;
	private long totalCharsCount = 0;
	private long scrapingElapsedMilliseconds = 0;
	private long processingElapsedMilliseconds = 0;
	
	public Main(String[] args) throws MalformedURLException {
		if (args.length < 2)
			throw new IllegalArgumentException("Invalid number of parameters: " + args.length);
		
		List<String> stringUrls = handleUrlParameter(args[0]);
		for (String url: stringUrls) {
			urls.add(new URL(url));
		}
		
		String[] words = args[1].split(",");
		
		EnumSet<OptionalParameter> optionalParameters = EnumSet.noneOf(OptionalParameter.class);
		for (int i = 2; i < args.length; i++) {
			optionalParameters.add(parseOptionalParameter(args[i]));
		}
		
		webScraper = new WebScraper(words, optionalParameters);
		webScraper.addListener(this);
	}
	
	private void start() {
		for (URL url: urls) {
			System.out.println("Processing: " + url.toString());
			try {
				webScraper.proccess(url);
			} catch (IOException e) {
				System.out.println(String.format("Error while fetching data from %s: %s", url.toString(), e.getMessage()));
			} catch (ParseException e) {
				System.out.println(String.format("Error while parsing data from %s: %s", url.toString(), e.getMessage()));
			}
			System.out.println();
		}
		
		if (webScraper.hasParameter(OptionalParameter.COUNT_WORDS))
			System.out.println("Total words occurrences count: " + totalWordsCount);

		if (webScraper.hasParameter(OptionalParameter.COUNT_CHARS))
			System.out.println("Total chars count: " + totalCharsCount);

		if (webScraper.hasParameter(OptionalParameter.VERBOSE)) {
			System.out.println("Total milliseconds spent with processing: " + processingElapsedMilliseconds);
			System.out.println("Total milliseconds spent with scraping: " + scrapingElapsedMilliseconds);
		}
	}
	
	public static void main(String[] args) {
		try {
			Main main = new Main(args);
			main.start();
		} catch (IllegalArgumentException | MalformedURLException e) {
			System.out.print(
					e.getMessage() + ".\n" +
				    "Correct usage: <path> <words> [-v] [-w] [-c] [-e]\n" +
					"<path>: text file or URL path\n" +
					"<words>: list of ',' separated words\n" +
					"-v: verbose mode\n" +
					"-w: count words\n" +
					"-c: count chars\n" +
					"-e: extract sentences\n");
		}
	}
	
	public static List<String> parseUrls(BufferedReader reader) throws IOException {
		List<String> urls = new ArrayList<String>();
		for (String url = reader.readLine(); url != null; url = reader.readLine()) {
			urls.add(url);
		}
		reader.close();
		return urls;
	}
	
	private static List<String> handleUrlParameter(String parameter) {
		if (WebScraper.isURL(parameter)) {
			List<String> singleUrl = new ArrayList<String>(1);
			singleUrl.add(parameter);
			return singleUrl;
		};
		try {
			BufferedReader reader = new BufferedReader(new FileReader(parameter));
			return parseUrls(reader);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Could not find file " + parameter);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error reading file " + parameter);
		}
	}
	
	public static OptionalParameter parseOptionalParameter(String parameter) {
		if (parameter.startsWith("-") && parameter.length() == 2) {
			char option = parameter.toLowerCase().charAt(1);
			switch (option) {
			case 'v':
				return OptionalParameter.VERBOSE;
			case 'w':
				return OptionalParameter.COUNT_WORDS;
			case 'c':
				return OptionalParameter.COUNT_CHARS;
			case 'e':
				return OptionalParameter.EXTRACT_SENTENCES;
			}
		}
		throw new IllegalArgumentException("Invalid parameter '" + parameter + "'");
	}

	@Override
	public void wordsCount(int wordsCount) {
		System.out.println("Words occurrences count: " + wordsCount);
		totalWordsCount += wordsCount;
	}

	@Override
	public void charsCount(int charsCount) {
		System.out.println("Chars count: " + charsCount);
		totalCharsCount += charsCount;
	}

	@Override
	public void wordSentences(String word, List<String> sentences) {
		System.out.println(String.format("\nWord '%s' found in %d sentence(s):", word, sentences.size()));
		int i = 1;
		for (String sentence: sentences) {
			System.out.println(i++ + ": " + sentence.trim());
		}
	}

	@Override
	public void endProcessing(long millisecondsSpent) {
		processingElapsedMilliseconds += millisecondsSpent;
	}

	@Override
	public void endScraping(long millisecondsSpent) {
		scrapingElapsedMilliseconds += millisecondsSpent;
	}
}
