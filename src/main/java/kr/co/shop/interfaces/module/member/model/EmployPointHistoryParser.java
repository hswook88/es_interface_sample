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

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployPointHistoryParser extends BaseParser {

	public EmployPointHistoryParser(String source) {
		super(source);
	}

	/**
	 * <pre>
	 * <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result> <row>
	 * <use_point>20120416</use_point> <storeNm>명동중앙점</storeNm>
	 * <use_gubun>0015</use_gubun> <product_nm>TASMINA</product_nm>
	 * <total_point>CORAL</total_point> <balance_point>250</balance_point> </row>
	 * </root>
	 * 
	 * @param pr
	 */
	@Override
	public List<EmployPointHistory> parse() throws MemberShipProcessException {
		List<EmployPointHistory> results = null;
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
				results = new ArrayList<EmployPointHistory>(xmlitems.getLength());
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					EmployPointHistory employPointHistory = new EmployPointHistory();
					results.add(employPointHistory);
					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if (this.property.contains(node.getNodeName())) {
							propertyCopy(node, employPointHistory);
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

	@SuppressWarnings("serial")
	private final Set<String> property = new HashSet<String>(11) {
		{
			add("use_point");
			add("use_gubun");
			add("ins_date");
			add("total_point");
			add("balance_point");
		}

	};

	private void propertyCopy(Node node, EmployPointHistory employPointHistory) {
		if ("use_point".equals(node.getNodeName())) {
			employPointHistory.setUsePoint(node.getTextContent());
		} else if ("use_gubun".equals(node.getNodeName())) {
			employPointHistory.setUseGubun(node.getTextContent());
		} else if ("ins_date".equals(node.getNodeName())) {
			employPointHistory.setInsDate(node.getTextContent());
		} else if ("total_point".equals(node.getNodeName())) {
			employPointHistory.setTotalPoint(node.getTextContent());
		} else if ("balance_point".equals(node.getNodeName())) {
			employPointHistory.setBalancePoint(node.getTextContent());
		}
	}
}
