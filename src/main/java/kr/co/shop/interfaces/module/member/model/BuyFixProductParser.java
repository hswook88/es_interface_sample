package kr.co.shop.interfaces.module.member.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsNumber;
import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuyFixProductParser extends BaseParser {

	public BuyFixProductParser(String source) {
		super(source);
	}

	/**
	 * 아 진짜 젭알좀
	 * 
	 * @return
	 * @see kr.co.shop.site.utils.parser.BaseParser#parse()
	 */
	@Override
	public List<BuyFixProduct> parse() throws MemberShipProcessException {
		List<BuyFixProduct> results = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");
			if (!SUCCESS.equals(success)) {
				log.debug(UtilsText.concat("result > ", success));
			} else {
				XPathExpression expr = xPath.compile("/root/row");
				Object result = expr.evaluate(xmlDoc, XPathConstants.NODESET);
				NodeList xmlitems = (NodeList) result;
				results = new ArrayList<BuyFixProduct>(xmlitems.getLength());
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					BuyFixProduct item = new BuyFixProduct();
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

	private void propertyCopy(Node node, BuyFixProduct item) {
		if ("SANGPUMFULLCD".equalsIgnoreCase(node.getNodeName())) {
			item.setFullCode(node.getTextContent());
			if ("0000000".equals(node.getTextContent())) {
				item.setResultYn("N");
			}
		} else if ("PRODUCTPOINT".equalsIgnoreCase(node.getNodeName())) {
			item.setPoint(UtilsNumber.toInt(node.getTextContent(), 0));
		}
	}

	@SuppressWarnings("serial")
	private final Set<String> property = new HashSet<String>(11) {
		{
			add("SANGPUMFULLCD");
			add("PRODUCTPOINT");
		}
	};
}
