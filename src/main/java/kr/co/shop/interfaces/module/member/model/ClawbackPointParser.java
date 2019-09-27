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
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClawbackPointParser extends BaseParser {

	public ClawbackPointParser(String source) {
		super(source);
	}

	public ClawbackPointParser(InputStream is) {
		super(is);
	}

	/**
	 * 5.성공 <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * <clawbackPoint>환수금액</clawbackpoint> </root>
	 * 
	 * 6.실패 <?xml version="1.0" encoding="UTF-8"?> <root> <result>FAILURE</result>
	 * <clawbackPoint>0</clawbackpoint> </root>
	 * 
	 * 
	 * @param pr
	 */
	@Override
	public Integer parse() throws MemberShipProcessException {
		int clawbackPoint = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");
			if (!SUCCESS.equals(success)) {
				return clawbackPoint;
			} else {
				clawbackPoint = (UtilsNumber.toInt(getSingleValue(xPath, xmlDoc, "/root/clawbackPoint"), 0));
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

		return clawbackPoint;
	}
}
