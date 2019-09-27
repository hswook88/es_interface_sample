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
public class CardConfirmParser extends BaseParser {

	public CardConfirmParser(String source) {
		super(source);
	}

	/**
	 * ﻿<?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * <card_stat>02</card_stat> <safekey_check>00</safekey_check> </root>
	 * 
	 * @return
	 * @see kr.co.shop.site.utils.parser.BaseParser#parse()
	 */
	@Override
	public CardValidate parse() throws MemberShipProcessException {
		CardValidate cardValidate = new CardValidate();
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
				cardValidate.setCardStat(getSingleValue(xPath, xmlDoc, "/root/card_stat"));
				cardValidate.setSafekeyCheck(getSingleValue(xPath, xmlDoc, "/root/safekey_check"));
				/*
				 * 카드번호를 잘못입력했을 경우 CardStat가 ""으로 넘어오므로 메시지 처리를 위해 ""일 경우 통과 시킴 if
				 * (StringUtils.isEmpty(cardValidate.getCardStat())) {
				 * log.info("XML IN String format is: \n" + getStringFromDocument(xmlDoc));
				 * throw new
				 * MemberShipProcessException(String.format("xml포맷이 다릅니다. 필요한 태그 [%s]",
				 * "/root/card_stat")); }
				 */
				if (UtilsText.isEmpty(cardValidate.getSafekeyCheck())) {
					log.info("XML IN String format is: \n" + getStringFromDocument(xmlDoc));
					log.debug(UtilsText.concat("xml포맷이 다릅니다. 필요한 태그 > ", "/root/safekey_check"));
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

		return cardValidate;
	}

}
