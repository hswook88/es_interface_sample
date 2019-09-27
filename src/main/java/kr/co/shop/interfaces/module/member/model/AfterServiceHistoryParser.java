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
public class AfterServiceHistoryParser extends BaseParser {

	public AfterServiceHistoryParser(String source) {
		super(source);
	}

	/**
	 * 1. 성공 <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * <row> <csNo>SM1608220001</csNo> <reqDate>20160822</reqDate> <storeNm>ST
	 * 강남논현점</storeNm> <productInfo> NIKE / 315123.001.260</productInfo>
	 * <reqType>심의</reqType> <reqStatus>심의접수</reqStatus> </root>
	 *
	 * 2. 실패 <?xml version="1.0" encoding="UTF-8"?> <root> <result>FAILURE</result>
	 * </root>
	 */
	@Override
	public List<AfterServiceHistory> parse() throws MemberShipProcessException {
		List<AfterServiceHistory> results = null;
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
				results = new ArrayList<AfterServiceHistory>(xmlitems.getLength());
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					AfterServiceHistory afterServiceHistory = new AfterServiceHistory();
					results.add(afterServiceHistory);
					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if (this.property.contains(node.getNodeName())) {
							propertyCopy(node, afterServiceHistory);
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
			add("csNo");
			add("regDate");
			add("storeNm");
			add("productInfo");
			add("reqType");
			add("reqStatus");
		}

	};

	private void propertyCopy(Node node, AfterServiceHistory afterServiceHistory) {
		if ("csNo".equals(node.getNodeName())) {
			afterServiceHistory.setCsNo(node.getTextContent());
		} else if ("regDate".equals(node.getNodeName())) {
			afterServiceHistory.setRegDate(node.getTextContent());
		} else if ("storeNm".equals(node.getNodeName())) {
			afterServiceHistory.setStoreNm(node.getTextContent());
		} else if ("productInfo".equals(node.getNodeName())) {
			afterServiceHistory.setProductInfo(node.getTextContent());
		} else if ("reqType".equals(node.getNodeName())) {
			afterServiceHistory.setReqType(node.getTextContent());
		} else if ("reqStatus".equals(node.getNodeName())) {
			afterServiceHistory.setReqStatus(node.getTextContent());
		}
	}

}
