package webscraper.behaviors;

import webscraper.WebScraper;
import webscraper.WebScraperListener;

public class CountCharsPerform implements CountCharsBehavior {
	private WebScraper webScraper;

	public CountCharsPerform(WebScraper webScraper) {
		this.webScraper = webScraper;
	}
	
	@Override
	public void countChars(String text) {
    	for (WebScraperListener listener: webScraper.getListeners()) {
    		listener.charsCount(text.length());
    	}
	}
}