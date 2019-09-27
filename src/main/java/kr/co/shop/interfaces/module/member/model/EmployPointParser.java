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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployPointParser extends BaseParser {

	public EmployPointParser(String source) {
		super(source);
	}

	/**
	 * ﻿<?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * <total_point>1000</total_point> <balance_point>50000</balance_point> </root>
	 * 
	 * @return
	 * @see kr.co.shop.site.utils.parser.BaseParser#parse()
	 */
	@Override
	public EmployPoint parse() {
		EmployPoint employUsePoint = new EmployPoint();
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
				employUsePoint.setSuccessFlag(success);
				employUsePoint.setTotalPoint(getSingleValue(xPath, xmlDoc, "/root/total_point"));
				employUsePoint.setBalancePoint(getSingleValue(xPath, xmlDoc, "/root/balance_point"));
			}
		} catch (ParserConfigurationException e) {
			log.debug(UtilsText.concat("ParserConfigurationException > ", e.getMessage()));
		} catch (SAXException e) {
			log.debug(UtilsText.concat("SAXException > ", e.getMessage()));
		} catch (IOException e) {
			log.debug(UtilsText.concat("IOException > ", e.getMessage()));
		} catch (XPathExpressionException e) {
			log.debug(UtilsText.concat("XPathExpressionException > ", e.getMessage()));
		}

		return employUsePoint;
	}
}
