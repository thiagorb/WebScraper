package webscraper.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webscraper.lexer.HtmlLexer;

public class HtmlTextNode extends HtmlNode {
	private String content;
	private static Map<String, String> encodedCharactersMap;
	private static final Pattern iso88591Symbols = Pattern.compile("&#([0-9]{1,3});");

	public HtmlTextNode(String content) {
		for (String key: getEncodedCharacterMap().keySet()) {
			content = content.replaceAll(key, getEncodedCharacterMap().get(key));
		}
		
		StringBuilder newContent = new StringBuilder();
		int nextCopiedIndex = 0;
		Matcher matcher = iso88591Symbols.matcher(content);
		while (matcher.find()) {
			newContent.append(content.substring(nextCopiedIndex, matcher.start()));
			int charCode = Integer.parseInt(matcher.group(1));
			newContent.append(Character.toChars(charCode));
			nextCopiedIndex = matcher.end();
		}
		if (nextCopiedIndex < content.length()) {
			newContent.append(content.substring(nextCopiedIndex));
		}
		this.content = newContent.toString();
	}
	
	@Override
	public String getHtml() {
		return content;
	}

	@Override
	public String getText() {
		return HtmlLexer.whiteSpacePattern.matcher(content).replaceAll(" ");
	}
	
	private static Map<String, String> getEncodedCharacterMap() {
		if (encodedCharactersMap != null)
			return encodedCharactersMap;

		encodedCharactersMap = new HashMap<>();
		
		encodedCharactersMap.put("&Aacute;", "Á");
		encodedCharactersMap.put("&aacute;", "á");
		encodedCharactersMap.put("&Acirc;", "Â");
		encodedCharactersMap.put("&acirc;", "â");
		encodedCharactersMap.put("&acute;", "´");
		encodedCharactersMap.put("&AElig;", "Æ");
		encodedCharactersMap.put("&aelig;", "æ");
		encodedCharactersMap.put("&Agrave;", "À");
		encodedCharactersMap.put("&agrave;", "à");
		encodedCharactersMap.put("&Alpha;", "Α");
		encodedCharactersMap.put("&alpha;", "α");
		encodedCharactersMap.put("&amp;", "&");
		encodedCharactersMap.put("&and;", "∧");
		encodedCharactersMap.put("&ang;", "∠");
		encodedCharactersMap.put("&Aring;", "Å");
		encodedCharactersMap.put("&aring;", "å");
		encodedCharactersMap.put("&asymp;", "≈");
		encodedCharactersMap.put("&Atilde;", "Ã");
		encodedCharactersMap.put("&atilde;", "ã");
		encodedCharactersMap.put("&Auml;", "Ä");
		encodedCharactersMap.put("&auml;", "ä");
		encodedCharactersMap.put("&bdquo;", "„");
		encodedCharactersMap.put("&Beta;", "Β");
		encodedCharactersMap.put("&beta;", "β");
		encodedCharactersMap.put("&brvbar;", "¦");
		encodedCharactersMap.put("&bull;", "•");
		encodedCharactersMap.put("&cap;", "∩");
		encodedCharactersMap.put("&Ccedil;", "Ç");
		encodedCharactersMap.put("&ccedil;", "ç");
		encodedCharactersMap.put("&cedil;", "¸");
		encodedCharactersMap.put("&cent;", "¢");
		encodedCharactersMap.put("&Chi;", "Χ");
		encodedCharactersMap.put("&chi;", "χ");
		encodedCharactersMap.put("&circ;", "ˆ");
		encodedCharactersMap.put("&clubs;", "♣");
		encodedCharactersMap.put("&cong;", "≅");
		encodedCharactersMap.put("&copy;", "©");
		encodedCharactersMap.put("&crarr;", "↵");
		encodedCharactersMap.put("&cup;", "∪");
		encodedCharactersMap.put("&curren;", "¤");
		encodedCharactersMap.put("&dagger;", "†");
		encodedCharactersMap.put("&Dagger;", "‡");
		encodedCharactersMap.put("&darr;", "↓");
		encodedCharactersMap.put("&deg;", "°");
		encodedCharactersMap.put("&Delta;", "Δ");
		encodedCharactersMap.put("&delta;", "δ");
		encodedCharactersMap.put("&diams;", "♦");
		encodedCharactersMap.put("&divide;", "÷");
		encodedCharactersMap.put("&Eacute;", "É");
		encodedCharactersMap.put("&eacute;", "é");
		encodedCharactersMap.put("&Ecirc;", "Ê");
		encodedCharactersMap.put("&ecirc;", "ê");
		encodedCharactersMap.put("&Egrave;", "È");
		encodedCharactersMap.put("&egrave;", "è");
		encodedCharactersMap.put("&empty;", "∅");
		encodedCharactersMap.put("&emsp;", " ");
		encodedCharactersMap.put("&ensp;", " ");
		encodedCharactersMap.put("&Epsilon;", "Ε");
		encodedCharactersMap.put("&epsilon;", "ε");
		encodedCharactersMap.put("&equiv;", "≡");
		encodedCharactersMap.put("&Eta;", "Η");
		encodedCharactersMap.put("&eta;", "η");
		encodedCharactersMap.put("&ETH;", "Ð");
		encodedCharactersMap.put("&eth;", "ð");
		encodedCharactersMap.put("&Euml;", "Ë");
		encodedCharactersMap.put("&euml;", "ë");
		encodedCharactersMap.put("&euro;", "€");
		encodedCharactersMap.put("&exist;", "∃");
		encodedCharactersMap.put("&fnof;", "ƒ");
		encodedCharactersMap.put("&forall;", "∀");
		encodedCharactersMap.put("&frac12;", "½");
		encodedCharactersMap.put("&frac14;", "¼");
		encodedCharactersMap.put("&frac34;", "¾");
		encodedCharactersMap.put("&Gamma;", "Γ");
		encodedCharactersMap.put("&gamma;", "γ");
		encodedCharactersMap.put("&ge;", "≥");
		encodedCharactersMap.put("&gt;", ">");
		encodedCharactersMap.put("&harr;", "↔");
		encodedCharactersMap.put("&hearts;", "♥");
		encodedCharactersMap.put("&hellip;", "…");
		encodedCharactersMap.put("&Iacute;", "Í");
		encodedCharactersMap.put("&iacute;", "í");
		encodedCharactersMap.put("&Icirc;", "Î");
		encodedCharactersMap.put("&icirc;", "î");
		encodedCharactersMap.put("&iexcl;", "¡");
		encodedCharactersMap.put("&Igrave;", "Ì");
		encodedCharactersMap.put("&igrave;", "ì");
		encodedCharactersMap.put("&infin;", "∞");
		encodedCharactersMap.put("&int;", "∫");
		encodedCharactersMap.put("&Iota;", "Ι");
		encodedCharactersMap.put("&iota;", "ι");
		encodedCharactersMap.put("&iquest;", "¿");
		encodedCharactersMap.put("&isin;", "∈");
		encodedCharactersMap.put("&Iuml;", "Ï");
		encodedCharactersMap.put("&iuml;", "ï");
		encodedCharactersMap.put("&Kappa;", "Κ");
		encodedCharactersMap.put("&kappa;", "κ");
		encodedCharactersMap.put("&Lambda;", "Λ");
		encodedCharactersMap.put("&lambda;", "λ");
		encodedCharactersMap.put("&laquo;", "«");
		encodedCharactersMap.put("&larr;", "←");
		encodedCharactersMap.put("&lceil;", "⌈");
		encodedCharactersMap.put("&ldquo;", "“");
		encodedCharactersMap.put("&le;", "≤");
		encodedCharactersMap.put("&lfloor;", "⌊");
		encodedCharactersMap.put("&lowast;", "∗");
		encodedCharactersMap.put("&loz;", "◊");
		encodedCharactersMap.put("&lrm;", "‎");
		encodedCharactersMap.put("&lsaquo;", "‹");
		encodedCharactersMap.put("&lsquo;", "‘");
		encodedCharactersMap.put("&lt;", "<");
		encodedCharactersMap.put("&macr;", "¯");
		encodedCharactersMap.put("&mdash;", "—");
		encodedCharactersMap.put("&micro;", "µ");
		encodedCharactersMap.put("&middot;", "·");
		encodedCharactersMap.put("&minus;", "−");
		encodedCharactersMap.put("&Mu;", "Μ");
		encodedCharactersMap.put("&mu;", "μ");
		encodedCharactersMap.put("&nabla;", "∇");
		encodedCharactersMap.put("&nbsp;", " ");
		encodedCharactersMap.put("&ndash;", "–");
		encodedCharactersMap.put("&ne;", "≠");
		encodedCharactersMap.put("&ni;", "∋");
		encodedCharactersMap.put("&not;", "¬");
		encodedCharactersMap.put("&notin;", "∉");
		encodedCharactersMap.put("&nsub;", "⊄");
		encodedCharactersMap.put("&Ntilde;", "Ñ");
		encodedCharactersMap.put("&ntilde;", "ñ");
		encodedCharactersMap.put("&Nu;", "Ν");
		encodedCharactersMap.put("&nu;", "ν");
		encodedCharactersMap.put("&Oacute;", "Ó");
		encodedCharactersMap.put("&oacute;", "ó");
		encodedCharactersMap.put("&Ocirc;", "Ô");
		encodedCharactersMap.put("&ocirc;", "ô");
		encodedCharactersMap.put("&OElig;", "Œ");
		encodedCharactersMap.put("&oelig;", "œ");
		encodedCharactersMap.put("&Ograve;", "Ò");
		encodedCharactersMap.put("&ograve;", "ò");
		encodedCharactersMap.put("&oline;", "‾");
		encodedCharactersMap.put("&Omega;", "Ω");
		encodedCharactersMap.put("&omega;", "ω");
		encodedCharactersMap.put("&Omicron;", "Ο");
		encodedCharactersMap.put("&omicron;", "ο");
		encodedCharactersMap.put("&oplus;", "⊕");
		encodedCharactersMap.put("&or;", "∨");
		encodedCharactersMap.put("&ordf;", "ª");
		encodedCharactersMap.put("&ordm;", "º");
		encodedCharactersMap.put("&Oslash;", "Ø");
		encodedCharactersMap.put("&oslash;", "ø");
		encodedCharactersMap.put("&Otilde;", "Õ");
		encodedCharactersMap.put("&otilde;", "õ");
		encodedCharactersMap.put("&otimes;", "⊗");
		encodedCharactersMap.put("&Ouml;", "Ö");
		encodedCharactersMap.put("&ouml;", "ö");
		encodedCharactersMap.put("&para;", "¶");
		encodedCharactersMap.put("&part;", "∂");
		encodedCharactersMap.put("&permil;", "‰");
		encodedCharactersMap.put("&perp;", "⊥");
		encodedCharactersMap.put("&Phi;", "Φ");
		encodedCharactersMap.put("&phi;", "φ");
		encodedCharactersMap.put("&Pi;", "Π");
		encodedCharactersMap.put("&pi;", "π");
		encodedCharactersMap.put("&piv;", "ϖ");
		encodedCharactersMap.put("&plusmn;", "±");
		encodedCharactersMap.put("&pound;", "£");
		encodedCharactersMap.put("&prime;", "′");
		encodedCharactersMap.put("&Prime;", "″");
		encodedCharactersMap.put("&prod;", "∏");
		encodedCharactersMap.put("&prop;", "∝");
		encodedCharactersMap.put("&Psi;", "Ψ");
		encodedCharactersMap.put("&psi;", "ψ");
		encodedCharactersMap.put("&quot;", "\"");
		encodedCharactersMap.put("&radic;", "√");
		encodedCharactersMap.put("&raquo;", "»");
		encodedCharactersMap.put("&rarr;", "→");
		encodedCharactersMap.put("&rceil;", "⌉");
		encodedCharactersMap.put("&rdquo;", "”");
		encodedCharactersMap.put("&reg;", "®");
		encodedCharactersMap.put("&rfloor;", "⌋");
		encodedCharactersMap.put("&Rho;", "Ρ");
		encodedCharactersMap.put("&rho;", "ρ");
		encodedCharactersMap.put("&rlm;", "‏");
		encodedCharactersMap.put("&rsaquo;", "›");
		encodedCharactersMap.put("&rsquo;", "’");
		encodedCharactersMap.put("&sbquo;", "‚");
		encodedCharactersMap.put("&Scaron;", "Š");
		encodedCharactersMap.put("&scaron;", "š");
		encodedCharactersMap.put("&sdot;", "⋅");
		encodedCharactersMap.put("&sect;", "§");
		encodedCharactersMap.put("&Sigma;", "Σ");
		encodedCharactersMap.put("&sigma;", "σ");
		encodedCharactersMap.put("&sigmaf;", "ς");
		encodedCharactersMap.put("&sim;", "∼");
		encodedCharactersMap.put("&spades;", "♠");
		encodedCharactersMap.put("&sub;", "⊂");
		encodedCharactersMap.put("&sube;", "⊆");
		encodedCharactersMap.put("&sum;", "∑");
		encodedCharactersMap.put("&sup;", "⊃");
		encodedCharactersMap.put("&sup1;", "¹");
		encodedCharactersMap.put("&sup2;", "²");
		encodedCharactersMap.put("&sup3;", "³");
		encodedCharactersMap.put("&supe;", "⊇");
		encodedCharactersMap.put("&szlig;", "ß");
		encodedCharactersMap.put("&Tau;", "Τ");
		encodedCharactersMap.put("&tau;", "τ");
		encodedCharactersMap.put("&there4;", "∴");
		encodedCharactersMap.put("&Theta;", "Θ");
		encodedCharactersMap.put("&theta;", "θ");
		encodedCharactersMap.put("&thetasym;", "ϑ");
		encodedCharactersMap.put("&thinsp;", " ");
		encodedCharactersMap.put("&THORN;", "Þ");
		encodedCharactersMap.put("&thorn;", "þ");
		encodedCharactersMap.put("&tilde;", "˜");
		encodedCharactersMap.put("&times;", "×");
		encodedCharactersMap.put("&trade;", "™");
		encodedCharactersMap.put("&Uacute;", "Ú");
		encodedCharactersMap.put("&uacute;", "ú");
		encodedCharactersMap.put("&uarr;", "↑");
		encodedCharactersMap.put("&Ucirc;", "Û");
		encodedCharactersMap.put("&ucirc;", "û");
		encodedCharactersMap.put("&Ugrave;", "Ù");
		encodedCharactersMap.put("&ugrave;", "ù");
		encodedCharactersMap.put("&uml;", "¨");
		encodedCharactersMap.put("&upsih;", "ϒ");
		encodedCharactersMap.put("&Upsilon;", "Υ");
		encodedCharactersMap.put("&upsilon;", "υ");
		encodedCharactersMap.put("&Uuml;", "Ü");
		encodedCharactersMap.put("&uuml;", "ü");
		encodedCharactersMap.put("&Xi;", "Ξ");
		encodedCharactersMap.put("&xi;", "ξ");
		encodedCharactersMap.put("&Yacute;", "Ý");
		encodedCharactersMap.put("&yacute;", "ý");
		encodedCharactersMap.put("&yen;", "¥");
		encodedCharactersMap.put("&yuml;", "ÿ");
		encodedCharactersMap.put("&Yuml;", "Ÿ");
		encodedCharactersMap.put("&Zeta;", "Ζ");
		encodedCharactersMap.put("&zeta;", "ζ");
		encodedCharactersMap.put("&zwj;", "‍");
		encodedCharactersMap.put("&zwnj;", "‌");

		return encodedCharactersMap;
	}
}
