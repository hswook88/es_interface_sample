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
public class EmployCertReportParser extends BaseParser {

	public EmployCertReportParser(String source) {
		super(source);
	}

	/**
	 * ﻿<?xml version="1.0" encoding="UTF-8"?> <root> <result>SUCCESS</result>
	 * <employees_number>01</employees_number> <employees_name>홍길동</employees_name>
	 * <employees_dpmt>정보전략팀</employees_dpmt>
	 * <employees_position>과장</employees_position>
	 * <employees_join_dt>20190101</employees_join_dt>
	 * <employees_resign_dt>99999999</employees_resign_dt> </root>
	 * 
	 * @return
	 * @see kr.co.shop.site.utils.parser.BaseParser#parse()
	 */
	@Override
	public EmployCertReport parse() throws MemberShipProcessException {
		EmployCertReport employReport = new EmployCertReport();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			String success = getSingleValue(xPath, xmlDoc, "/root/result");
			log.debug("success > " + success);
			if (!SUCCESS.equals(success)) {
				log.debug(UtilsText.concat("result > ", success));
			} else {
				employReport.setSuccessFlag(success);
				employReport.setEmployeesNumber(getSingleValue(xPath, xmlDoc, "/root/employees_number"));
				employReport.setEmployeesName(getSingleValue(xPath, xmlDoc, "/root/employees_name"));
				employReport.setEmployeesDpmt(getSingleValue(xPath, xmlDoc, "/root/employees_dpmt"));
				employReport.setEmployeesPosition(getSingleValue(xPath, xmlDoc, "/root/employees_position"));
				employReport.setEmployeesJoinDt(getSingleValue(xPath, xmlDoc, "/root/employees_join_dt"));
				employReport.setEmployeesResignDt(getSingleValue(xPath, xmlDoc, "/root/employees_resign_dt"));
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

		return employReport;
	}
}
