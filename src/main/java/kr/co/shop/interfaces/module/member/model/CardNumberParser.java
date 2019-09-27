package kr.co.shop.interfaces.module.member.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

/**
 * @Desc : 회원카드번호 리퀘스트 XML파서
 * @FileName : CardNumberParser.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 20.
 * @Author : 백인천
 */
@Slf4j
public class CardNumberParser extends BaseParser {

	public CardNumberParser(String source) {
		super(source);
	}

	/**
	 * 카드번호 조회
	 */
	@Override
	public CardNumber parse() throws MemberShipProcessException {
		CardNumber cardNumber = new CardNumber();
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
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if ("card_no".equals(node.getNodeName())) {
							cardNumber.setCardNumber(node.getTextContent());
						} else if ("card_flag".equals(node.getNodeName())) {
							cardNumber.setCardGbnCode(node.getTextContent());
						} else if ("card_issue_dt".equals(node.getNodeName())) {
							cardNumber.setCardIssueDate(node.getTextContent());
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

		return cardNumber;
	}
}
