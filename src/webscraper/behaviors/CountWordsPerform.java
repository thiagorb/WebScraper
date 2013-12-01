package webscraper.behaviors;

import webscraper.WebScraper;
import webscraper.WebScraperListener;

public class CountWordsPerform implements CountWordsBehavior {
	private WebScraper webScraper;

	public CountWordsPerform(WebScraper webScraper) {
		this.webScraper = webScraper;
	}
	
	@Override
	public void countWords(String text) {
    	int wordsCount = 0;
    	for (String word: webScraper.getWords()) {
    		wordsCount += countString(word, text);
    	}
    	for (WebScraperListener listener: webScraper.getListeners()) {
    		listener.wordsCount(wordsCount);
    	}
	}

	private static int countString(String lookFor, String lookInto) {
		int count = 0;
    	int nextStartIndex = 0;
    	while (nextStartIndex >= 0 && nextStartIndex < lookInto.length()) {
    		nextStartIndex = lookInto.indexOf(lookFor, nextStartIndex);
    		if (nextStartIndex >= 0) {
    			count++;
    			nextStartIndex++;
    		}
    	}
    	return count;
	}
}