package webscraper.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import webscraper.lexer.HtmlLexer;

public class HtmlElementNode extends HtmlNode implements Iterable<HtmlNode> {
	private String tag;
	private List<HtmlNode> children = new LinkedList<>();

	public HtmlElementNode(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String getHtml() {
		return String.format("<%s>%s</%s>", tag, getInnerHtml(), tag);
	}
	
	@Override
	public String getText() {
		StringBuilder text = new StringBuilder();
		for (HtmlNode child: children) {
			text.append(child.getText());
		}
		return HtmlLexer.whiteSpacePattern.matcher(text.toString()).replaceAll(" ");
	}
	
	public void addChild(HtmlNode node) {
		children.add(node);
		node.setParentNode(this);
	}

	private String getInnerHtml() {
		StringBuilder html = new StringBuilder();
		for (HtmlNode child: children) {
			html.append(child.getHtml());
		}
		return html.toString();
	}

	public List<HtmlNode> getChildren() {
		return children;
	}

	@Override
	public Iterator<HtmlNode> iterator() {
		return new HtmlIterator(this);
	}

	public String getTagName() {
		return tag;
	}
}
