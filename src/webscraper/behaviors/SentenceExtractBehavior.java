package webscraper.behaviors;

import webscraper.parser.HtmlElementNode;

public interface SentenceExtractBehavior {
	void extractSentences(HtmlElementNode html);
}