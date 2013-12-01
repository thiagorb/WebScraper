package webscraper.parser;

public abstract class HtmlNode {
	private HtmlElementNode parentNode;
	
	public abstract String getHtml();

	public abstract String getText();

	public HtmlElementNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(HtmlElementNode parentNode) {
		this.parentNode = parentNode;
	}
}
