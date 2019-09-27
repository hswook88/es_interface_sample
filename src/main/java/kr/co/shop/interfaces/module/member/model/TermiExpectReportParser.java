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

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : memA910a - 총 스탬프, 현 스탬프, 가용포인트, 전체포인트, 소멸포인트
 * @FileName : TermiExpectReportParser.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 18.
 * @Author : 백인천
 */
@Slf4j
public class TermiExpectReportParser extends BaseParser {

	public TermiExpectReportParser(String source) {
		super(source);
	}

	public TermiExpectReportParser(InputStream is) {
		super(is);
	}

	/**
	 * <pre>
	 * <?xml version="1.0" encoding="UTF-8"?>  
	 * <root>                                  
	 *   <result>SUCCESS</result>              
	 *   <expire_point>2000</expire_point>     
	 * </root>
	 * </pre>
	 * 
	 * @param pr
	 */
	@Override
	public TermiExpectReport parse() throws MemberShipProcessException {
		TermiExpectReport pr = new TermiExpectReport();
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
				pr.setExpire_point(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/expire_point"), 0));
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

		return pr;
	}
}