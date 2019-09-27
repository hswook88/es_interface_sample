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
public class StoreOrderHistoryParser extends BaseParser {

	public StoreOrderHistoryParser(String source) {
		super(source);
	}

	/**
	 * <pre>
	 * <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result> <row>
	 * <sale_date>20120416</sale_date> <storeNm>명동중앙점</storeNm>
	 * <deal_no>0015</deal_no> <product_nm>TASMINA</product_nm> <color>CORAL</color>
	 * <size_nm>250</size_nm> <sale_qty>5</sale_qty>
	 * <consumer_price>345000</consumer_price> <charge_price>69000</charge_price>
	 * <sales_price>276000</sales_price> <deal_flag>구매</deal_flag> </row> </root>
	 * 
	 * @param pr
	 */
	@Override
	public List<StoreOrderHistory> parse() throws MemberShipProcessException {
		List<StoreOrderHistory> results = null;
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
				results = new ArrayList<StoreOrderHistory>(xmlitems.getLength());
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					StoreOrderHistory storeOrderHistory = new StoreOrderHistory();
					results.add(storeOrderHistory);
					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if (this.property.contains(node.getNodeName())) {
							propertyCopy(node, storeOrderHistory);
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
			add("sale_date");
			add("storeNm");
			add("deal_no");
			add("product_nm");
			add("color");
			add("size_nm");
			add("sale_qty");
			add("consumer_price");
			add("charge_price");
			add("sales_price");
			add("deal_flag");
		}

	};

	private void propertyCopy(Node node, StoreOrderHistory storeOrderHistory) {
		if ("sale_date".equals(node.getNodeName())) {
			storeOrderHistory.setSaleDate(node.getTextContent());
		} else if ("storeNm".equals(node.getNodeName())) {
			storeOrderHistory.setStoreNm(node.getTextContent());
		} else if ("deal_no".equals(node.getNodeName())) {
			storeOrderHistory.setDealNo(node.getTextContent());
		} else if ("product_nm".equals(node.getNodeName())) {
			storeOrderHistory.setProductNm(node.getTextContent());
		} else if ("color".equals(node.getNodeName())) {
			storeOrderHistory.setColor(node.getTextContent());
		} else if ("size_nm".equals(node.getNodeName())) {
			storeOrderHistory.setSizeNm(node.getTextContent());
		} else if ("sale_qty".equals(node.getNodeName())) {
			storeOrderHistory.setSaleQty(node.getTextContent());
		} else if ("consumer_price".equals(node.getNodeName())) {
			storeOrderHistory.setConsumerPrice(node.getTextContent());
		} else if ("charge_price".equals(node.getNodeName())) {
			storeOrderHistory.setChargePrice(node.getTextContent());
		} else if ("sales_price".equals(node.getNodeName())) {
			storeOrderHistory.setSalesPrice(node.getTextContent());
		} else if ("deal_flag".equals(node.getNodeName())) {
			storeOrderHistory.setDealFlag(node.getTextContent());
		}
	}
}
