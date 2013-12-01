package webscraper.parser;

import java.util.HashSet;
import java.util.Set;

import webscraper.lexer.HtmlLexer;

public class TextExtractor {
	private final Set<String> ignoreTextTags = new HashSet<>();
	
	public TextExtractor() {
		
	}
	
	public String getText(HtmlNode node) {
		if (node.getClass() == HtmlTextNode.class)
			return ((HtmlTextNode)node).getText();
		
		HtmlElementNode element = (HtmlElementNode)node;
		
		if (ignoreTextTags.contains(element.getTagName())) 
			return "";

		StringBuilder text = new StringBuilder();
		for (HtmlNode child: element.getChildren()) {
			text.append(getText(child));
		}
		return HtmlLexer.whiteSpacePattern.matcher(text.toString()).replaceAll(" ");
	}
	
	public void addIgnoreTextTags(String tag) {
		ignoreTextTags.add(tag);
	}
}
