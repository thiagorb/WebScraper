package webscraper.behaviors;

import java.util.Date;

import webscraper.WebScraper;
import webscraper.WebScraperListener;

public class VerbosityVerbose implements VerbosityBehavior {
	private WebScraper webScraper;
	private long processingStart = 0;
	private long scrapingStart = 0;

	public VerbosityVerbose(WebScraper webScraper) {
		this.webScraper = webScraper;
	}

	@Override
	public void startProcessing() {
		processingStart = new Date().getTime();
	}

	@Override
	public void startScraping() {
		scrapingStart = new Date().getTime();
	}

	@Override
	public void endProcessing() {
    	for (WebScraperListener listener: webScraper.getListeners()) {
    		listener.endProcessing(new Date().getTime() - processingStart);
    	}
	}

	@Override
	public void endScraping() {
    	for (WebScraperListener listener: webScraper.getListeners()) {
    		listener.endScraping(new Date().getTime() - scrapingStart);
    	}
	}
}