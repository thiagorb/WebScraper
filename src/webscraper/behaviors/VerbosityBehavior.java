package webscraper.behaviors;

public interface VerbosityBehavior {
	void startProcessing();

	void startScraping();
	
	void endProcessing();

	void endScraping();
}