package kr.co.shop.interfaces.module.member.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralConfirmParser extends BaseParser {

	public GeneralConfirmParser(String source) {
		super(source);
	}

	/**
	 * <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * </root>
	 * 
	 * @return
	 * @see kr.co.shop.site.utils.parser.BaseParser#parse()
	 */
	@Override
	public String parse() throws MemberShipProcessException {
		String general = SUCCESS;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");
			if (!SUCCESS.equals(success)) {
				general = success;
				log.debug(UtilsText.concat("result > ", success));
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

		return general;
	}

}
