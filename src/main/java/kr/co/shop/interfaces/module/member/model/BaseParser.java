package kr.co.shop.interfaces.module.member.model;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import kr.co.shop.interfaces.module.member.utils.Util;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 멤버십 http통신의 기본파서
 * @FileName : BaseParser.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
@Slf4j
@Data
public abstract class BaseParser {

	protected final static String SUCCESS = "SUCCESS";

	private String source;

	public BaseParser(String source) {
		if (log.isDebugEnabled()) {
			log.debug("[" + source + "]");
		}

		this.source = removeBomCode(source);
	}

	public BaseParser(InputStream is) {
		this.source = Util.getString(is);
	}

	abstract public Object parse();

	/**
	 * <pre>
	 * 노드에 해당하는 단일테그
	 * </pre>
	 * 
	 * @param xPath
	 * @param xmlDoc
	 * @param path
	 * @return
	 * @throws XPathExpressionException
	 */
	protected String getSingleValue(XPath xPath, Document xmlDoc, String path) throws XPathExpressionException {
		XPathExpression expr = xPath.compile(path);
		Object result = expr.evaluate(xmlDoc, XPathConstants.NODE);
		Node node = (Node) result;
		return node == null ? null : node.getTextContent();
	}

	/**
	 * @Desc : 입력받은 스트링에서 BOM을 제거
	 * @Method Name : removeBomCode
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param str
	 * @return
	 */
	public static String removeBomCode(String str) {
		char ch = str.charAt(0);
		if (ch != '<') {
			str = str.substring(1);
		}

		return str;
	}

	/**
	 * @Desc : Document를 String으로 변환
	 * @Method Name : getStringFromDocument
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param doc
	 * @return
	 */
	public String getStringFromDocument(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}