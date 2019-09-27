package kr.co.shop.interfaces.module.member.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsNumber;
import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GiftCardParser extends BaseParser {

	public GiftCardParser(String source) {
		super(source);
	}

	public GiftCardParser(InputStream is) {
		super(is);
	}

	/**
	 * <pre>
	 * <?xml version="1.0" encoding="UTF-8"?> 
	 * <root>
	 *   <result>SUCCESS</result> 
	 *   <card_no>2010130000000016</card_no> 
	 *   <balance>70000</balance> 
	 * </root>
	 * </pre>
	 * 
	 * @param pr
	 */
	@Override
	public GiftCard parse() throws MemberShipProcessException {
		GiftCard gc = new GiftCard();
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
				gc.setBalance(UtilsNumber.toInt(getSingleValue(xPath, xmlDoc, "/root/balance"), 0));
				gc.setGiftNo(getSingleValue(xPath, xmlDoc, "/root/card_no"));
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

		return gc;
	}
}
