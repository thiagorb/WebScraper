package webscraper.behaviors;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import webscraper.WebScraper;
import webscraper.WebScraperListener;
import webscraper.parser.HtmlElementNode;
import webscraper.parser.HtmlNode;
import webscraper.parser.HtmlTextNode;

public class SentenceExtractPerform implements SentenceExtractBehavior {
	private WebScraper webScraper;

	public SentenceExtractPerform(WebScraper webScraper) {
		this.webScraper = webScraper;
	}
	
	@Override
	public void extractSentences(HtmlElementNode html) {
    	for (String word: webScraper.getWords()) {
        	Iterator<HtmlNode> iterator = html.iterator();
        	Set<HtmlNode> containers = new HashSet<>();
        	while (iterator.hasNext()) {
        		HtmlNode node = iterator.next();
        		if (node.getClass() == HtmlTextNode.class) {
        			HtmlNode container = extractSentenceContainerFromText(word, (HtmlTextNode)node);
        			if (container != null && !containers.contains(container)) {
        				containers.add(container);
        			}
        		}
        	}
        	if (containers.size() > 0) {
        		List<String> sentences = new LinkedList<String>();
        		for (HtmlNode container: containers) {
        			sentences.add(webScraper.getExtractor().getText(container));
        		}
            	for (WebScraperListener listener: webScraper.getListeners()) {
            		listener.wordSentences(word, sentences);
            	}
        	}
    	}
	}
	
	private HtmlNode extractSentenceContainerFromText(String word, HtmlTextNode textNode) {
		if (!webScraper.getExtractor().getText(textNode).contains(word))
			return null;
		HtmlElementNode parentNode = textNode.getParentNode();
		while (parentNode != null && !WebScraper.isSentenceDelimiter(parentNode.getTagName())) {
			parentNode = parentNode.getParentNode();
		}
		if (parentNode == null) { // Should not happen
			return textNode;
		} else {
			if (!webScraper.getExtractor().getText(parentNode).contains(word))
				return null;
			return parentNode;
		}
	}
}