package webscraper.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HtmlIterator implements Iterator<HtmlNode> {
	private List<HtmlNode> nodeStack = new LinkedList<>();
	
	public HtmlIterator(HtmlElementNode root) {
		nodeStack.add(root);
	}

	@Override
	public boolean hasNext() {
		return nodeStack.size() > 0;
	}

	@Override
	public HtmlNode next() {
		HtmlNode node = nodeStack.remove(0);
		if (node.getClass() == HtmlTextNode.class)
			return node;
		
		HtmlElementNode element = (HtmlElementNode)node;
		nodeStack.addAll(element.getChildren());
		return node;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public List<HtmlNode> getNodeStack() {
		return nodeStack;
	}
}
