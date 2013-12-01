package webscraper;

import java.util.List;

public interface WebScraperListener {
	void wordsCount(int wordsCount);

	void charsCount(int charsCount);
	
	void wordSentences(String word, List<String> sentences);

	void endProcessing(long millisecondsSpent);

	void endScraping(long millisecondsSpent);
}