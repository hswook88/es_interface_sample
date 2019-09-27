package kr.co.shop.interfaces.module.product.offlineproduct.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsNumber;
import kr.co.shop.interfaces.module.member.model.BaseParser;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;

/**
 * 
 * @Desc : 오프라인 재고조회 xml파서
 * @FileName : ProductOfflineStockParser.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 25.
 * @Author : 김영진
 */
public class ProductOfflineStockParser extends BaseParser {

	public ProductOfflineStockParser(String source) {
		super(source);
	}

	public ProductOfflineStockParser(InputStream is) {
		super(is);
	}

	/**
	 * <pre>
	 *  PARAMETER : sangpumfullcd   상품full코드  VARCHAR(13)
	 *  memB060a
	 *  성공
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <root>
	 *    <result>SUCCESS</result>
	 *    <row>
	 *         <gubun>AS</gubun>
	 *         <curr_qty>0</curr_qty>
	 *    </row>
	 *    <row>
	 *         <gubun>AW</gubun>
	 *         <curr_qty>0</curr_qty>
	 *    </row>
	 * </root>
	 *  
	 *  실패 
	 *  <?xml version="1.0" encoding="UTF-8"?>
	 *  <root>
	 *      <result>FAILURE</result>
	 *  </root>
	 * </pre>
	 */
	@Override
	public List<ProductOfflineStockHttp> parse() {
		List<ProductOfflineStockHttp> results = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");

			if (!SUCCESS.equals(success)) {
				throw new MemberShipProcessException(String.format("result[%s]", success));
			} else {
				NodeList xmlitems = (NodeList) xPath.compile("/root/row").evaluate(xmlDoc, XPathConstants.NODESET);
				results = new ArrayList<ProductOfflineStockHttp>(xmlitems.getLength());

				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();

					ProductOfflineStockHttp item = new ProductOfflineStockHttp();
					results.add(item);

					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if (this.property.contains(node.getNodeName())) {
							propertyCopy(node, item);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			throw new MemberShipProcessException(e);
		} catch (SAXException e) {
			throw new MemberShipProcessException(e);
		} catch (IOException e) {
			throw new MemberShipProcessException(e);
		} catch (XPathExpressionException e) {
			throw new MemberShipProcessException(e);
		}

		return results;
	}

	private void propertyCopy(Node node, ProductOfflineStockHttp item) {
		if ("gubun".equalsIgnoreCase(node.getNodeName())) {
			item.setGubun(node.getTextContent());
		} else if ("curr_qty".equalsIgnoreCase(node.getNodeName())) {
			item.setCurrQty(UtilsNumber.toInt(node.getTextContent(), 0));
		}
	}

	@SuppressWarnings("serial")
	private final Set<String> property = new HashSet<String>(11) {
		{
			add("gubun");
			add("curr_qty");

		}
	};

}
