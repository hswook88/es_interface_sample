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

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StorePointHistoryParser extends BaseParser {

	public StorePointHistoryParser(String source) {
		super(source);
	}

	/**
	 * <pre>
	 * ﻿<?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result> <row>
	 * <save_date>20120101</save_date> <store_cd>8800</store_cd>
	 * <save_gubun>가입적립</save_gubun> <save_point>20</save_point> </row> </root>
	 * 
	 * *******************2016.08.09 변경 후
	 * 
	 * 4.RETURN RESULT 성공여부 결과있을경우 ROW생성 SAVE_DATE 적립일자 STORE_CD 점코드 deal_no 거래번호
	 * point_gubun 적립-이벤트 구분 (신규) SAVE_GUBUN 적립/사용구분(가입적립,강제적립,매출적립,사용) 반품시 차감구분 필요
	 * - 팀장님 수정(20120416) SAVE_POINT 적립포인트 양도/양수구분 필요
	 * 
	 * @param pr
	 */
	@Override
	public List<StorePointHistory> parse() throws MemberShipProcessException {
		List<StorePointHistory> results = null;
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
				results = new ArrayList<StorePointHistory>(xmlitems.getLength());
				for (int i = 0; i < xmlitems.getLength(); i++) {
					Node xmlitem = xmlitems.item(i);
					NodeList child = xmlitem.getChildNodes();
					StorePointHistory storePointHistory = new StorePointHistory();
					results.add(storePointHistory);
					for (int j = 0; j < child.getLength(); j++) {
						Node node = child.item(j);
						if (this.property.contains(node.getNodeName())) {
							propertyCopy(node, storePointHistory);
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
			add("save_date");
			add("store_cd");
			add("save_gubun");
			add("point_gubun"); // 적립-이벤트구분 (신규)
			add("save_point");
			add("deal_no");
			add("INS_TIME");
		}

	};

	private void propertyCopy(Node node, StorePointHistory storePointHistory) {
		if ("save_date".equals(node.getNodeName())) {
			storePointHistory.setSaveDate(node.getTextContent());
		} else if ("store_cd".equals(node.getNodeName())) {
			storePointHistory.setStoreId(node.getTextContent());
		} else if ("save_gubun".equals(node.getNodeName())) {
			storePointHistory.setSaveGubun(node.getTextContent());
		} else if ("point_gubun".equals(node.getNodeName())) {// 적립-이벤트구분 (신규)
			storePointHistory.setPointGubun(node.getTextContent());
		} else if ("save_point".equals(node.getNodeName())) {
			storePointHistory.setSavePoint(NumberUtils.toInt(node.getTextContent(), 0));
		} else if ("deal_no".equals(node.getNodeName())) {
			storePointHistory.setDealNumber(node.getTextContent());
		} else if ("INS_TIME".equals(node.getNodeName())) {
			storePointHistory.setInsTime(node.getTextContent());
		}
	}

}
