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

import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LatedSavePointParser extends BaseParser {

	public LatedSavePointParser(String source) {
		super(source);
	}

	/**
	 * URL :
	 * http://localhost/ngisns/jsp/mem/memB/memB300a.jsp?safe_key=안심키&date=구매일자&store_cd=매장코드&Pos_no=포스번호&Deal_no=영수증번호&Price=구입금액
	 * 성공 <?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * </root>
	 *
	 * 실패 <?xml version="1.0" encoding="UTF-8"?> <root> <result>FAILURE</result>
	 * <failure_result>04063805</failure_result> </root>
	 * 
	 * failure_result 값 04063801 구입내역없음 04063802 구입금액오류 04063803 구매확정된오류 04063804
	 * 반품된영수증오류 04063805 기타오류
	 */
	@Override
	public String parse() throws MemberShipProcessException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");

			if (!SUCCESS.equals(success)) {
				String failureResult = getSingleValue(xPath, xmlDoc, "/root/failure_result");
				return failureResult;
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
		return "";
	}

}
