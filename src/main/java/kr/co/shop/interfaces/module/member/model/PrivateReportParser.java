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

@Slf4j
public class PrivateReportParser extends BaseParser {

	public PrivateReportParser(String source) {
		super(source);
	}

	public PrivateReportParser(InputStream is) {
		super(is);
	}

	/**
	 * <pre>
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * <root>
	 *    <result>SUCCESS</result>
	 *    <tot_save_point>51000</tot_save_point>
	 *    <save_point>10000</save_point>
	 *    <pre_save_point>10000</pre_save_point>
	 *    <poss_point>4560</poss_point>
	 *    <stamp_cnt>0</stamp_cnt>
	 *    <tot_stamp_cnt>0</tot_stamp_cnt>
	 *    <termi_point>0</termi_point>
	 * </root>
	 * </pre>
	 * 
	 * *********************************2016.08.09 변경
	 * 후************************************************** stamp_cnt, tot_stamp_cnt
	 * 가 event_point,event_date로 변경
	 * ***************************************************************************************************
	 * [ 성 공 ] [ 실 패 ] <?xml version="1.0" encoding="UTF-8"?> <?xml version="1.0"
	 * encoding="UTF-8"?> <root> <root> <result>SUCCESS</result>
	 * <result>FAILURE</result> <tot_save_point>2000</tot_save_point>
	 * <tot_save_point>0</tot_save_point> <save_point>2000</save_point>
	 * <save_point>0</save_point> <pre_save_point>10000</pre_save_point>
	 * <pre_save_point>0</pre_save_point> <poss_point>2000</poss_point>
	 * <poss_point>0</poss_point> <event_point>1000</event_point>
	 * <stamp_cnt>0</stamp_cnt> <event_date>2016-08-08</event_date>
	 * <tot_stamp_cnt>0</tot_stamp_cnt> <termi_point>1000</termi_point>
	 * <termi_point>0</termi_point> </root> </root>
	 *
	 * 
	 * 
	 * @param pr
	 */
	@Override
	public PrivateReport parse() throws MemberShipProcessException {
		PrivateReport pr = new PrivateReport();
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
				pr.setTotalPoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/tot_save_point"), 0));
				pr.setToDoSavePoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/save_point"), 0));
				pr.setPreSavePoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/pre_save_point"), 0));
				pr.setAccessPoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/poss_point"), 0));
				// pr.setStampCount(NumberUtils.toInt(getSingleValue(xPath, xmlDoc,
				// "/root/stamp_cnt"), 0));
				// pr.setTotalStampCount(NumberUtils.toInt(getSingleValue(xPath, xmlDoc,
				// "/root/tot_stamp_cnt"), 0));

				pr.setEventPoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/event_point"), 0));
				pr.setEventDate(getSingleValue(xPath, xmlDoc, "/root/event_date"));

				pr.setExtinctPoint(NumberUtils.toInt(getSingleValue(xPath, xmlDoc, "/root/termi_point"), 0));
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