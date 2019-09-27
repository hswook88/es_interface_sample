package kr.co.shop.interfaces.zinterfacesdb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.shop.common.util.UtilsDate;
import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.common.sabangnet.UtilsSabangnet;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetOrderList;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetPropertiesCode;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetSetItem;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetSetItem2;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesSabangnetDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource(value = { "classpath:interfaces-${spring.profiles.active}.properties" })
public class InterfacesSabangnetService {

	@Autowired
	private Environment env;

	@Autowired
	private InterfacesSabangnetDao interfacesSabangnetDao;

	private void xmlHeader(Document doc, Element header, String methodNm) {

		// SEND_COMPAYNY_ID
		Element sendCompId = doc.createElement("SEND_COMPAYNY_ID");
		sendCompId.appendChild(doc.createTextNode(env.getProperty("interfaces.sabangnet.api.companyId")));
		header.appendChild(sendCompId);

		// SEND_AUTH_KEY
		Element sendAuthKey = doc.createElement("SEND_AUTH_KEY");
		sendAuthKey.appendChild(doc.createTextNode(env.getProperty("interfaces.sabangnet.api.authKey")));
		header.appendChild(sendAuthKey);

		if (methodNm.equals("goodsInfo")) {
			// SEND_DATE
			Element sendDate = doc.createElement("SEND_DATE");
			sendDate.appendChild(doc.createTextNode(UtilsText.concat("<![CDATA[", UtilsDate.today("yyyyMMdd"), "]]>")));
			header.appendChild(sendDate);

			// SEND_GOODS_CD_RT
			Element sendGoodsCdRt = doc.createElement("SEND_GOODS_CD_RT");
			sendGoodsCdRt.appendChild(doc.createTextNode("Y"));
			header.appendChild(sendGoodsCdRt);
		} else if (methodNm.equals("otherInfo")) {
			// SEND_DATE
			Element sendDate = doc.createElement("SEND_DATE");
			sendDate.appendChild(doc.createTextNode(UtilsText.concat("<![CDATA[", UtilsDate.today("yyyyMMdd"), "]]>")));
			header.appendChild(sendDate);
		}

	}

	/**
	 * 사방넷 등록 아이템 수집
	 */
	public void makeSabangnetGoodsInfo() {
		// 테스트 후 설정쪽에서 선언하여 가져와서 사용으로 변경예정
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.goodsInfo");
		log.debug("sbnApiUrl > " + sbnApiUrl);
		// xml경로는 외부에서 접근 가능한 경로로 설정해야함
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"), "test.xml");
		log.debug("xmlPath > " + xmlPath);
		// xml파일 생성 성공여부
		boolean successFlag = false;

		List<InterfacesSabangnetSetItem> itemList = null;

		try {
			// 1. 사방넷에 등록 할 아이템을 조회
			itemList = interfacesSabangnetDao.selectAffltsSendItem();

			// 2. 조회된 아이템 리스트를 사방넷의 양식에 맞는 XML로 변환
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			// SABANG_GOODS_REGI
			Element sabangGoodsRegi = doc.createElement("SABANG_GOODS_REGI");
			doc.appendChild(sabangGoodsRegi);

			// HEADER
			Element header = doc.createElement("HEADER");
			sabangGoodsRegi.appendChild(header);

			/* ST: HEADER 엘리먼트 */
			xmlHeader(doc, header, "goodsInfo");
			/* ED: HEADER 엘리먼트 */

			// data 엘리먼트
			Element data = null;

			ObjectMapper oMapper = new ObjectMapper();

			/* ST: DATA 엘리먼트 */
			for (InterfacesSabangnetSetItem itemObj : itemList) {
				// DATA 생성
				data = doc.createElement("DATA");
				sabangGoodsRegi.appendChild(data);

				Map<String, Object> map = oMapper.convertValue(itemObj, Map.class);

				// 변수명을 사방넷 변수명과 동일하게 치환
				for (String key : map.keySet()) {
					char bfCul = 0;
					String afCul = "";

					// 사방넷 엘리먼트명에 맞춰서 컬럼명 변경
					afCul = UtilsSabangnet.changeSbnColumn(bfCul, afCul, key);

					String val = map.get(key).toString();

					if (afCul.indexOf("CLASS_CD") != -1) {

					}

					// CDATA 적용
					val = UtilsSabangnet.validateHeaderCulomn(afCul, val, "goodsInfo");

					// 치환이 완료된 변수명으로 XMLID로 사용
					Element objEl = doc.createElement(afCul);
					objEl.appendChild(doc.createTextNode(val));
					data.appendChild(objEl);
				}

			}
			/* ED: DATA 엘리먼트 */

			log.debug("doc > " + doc.toString());

			successFlag = UtilsSabangnet.makeSabangnetXml(doc, xmlPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3. 구성된 XML경로로 사방넷의 상품등록&수정 API를 호출
		// sbnApiUrl + xmlPath
		if (successFlag) {
			String url = UtilsText.concat(sbnApiUrl, xmlPath);

			HttpResponse httpResponse = UtilsSabangnet.sendSabangnetHttp(url);
			HttpEntity entry = httpResponse.getEntity();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(entry.getContent(), "euc-kr"));
				String buffer = null;
				String testa = "";

				while ((buffer = br.readLine()) != null) {
					// strBuf.append(buffer).append("\r\n");
					log.debug("buffer > " + buffer);
					testa += buffer;
				}

				log.debug("httpResponse getEntity > " + httpResponse.getStatusLine() + " / " + testa);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ClientParameter sbnParameter = ClientParameter.builder().target(url,
			// String.class).method("get").build();
			// String sbnResult = UtilsNet.send(sbnParameter).getData();

			// log.debug(UtilsText.concat("SabangNet Result > ", sbnResult));
		} else {
			log.debug("xml파일이 생성되지 않아 사방넷과 통신할 수 없습니다.");
		}

	}

	/**
	 * 사방넷 속성코드 조회
	 */
	public List<InterfacesSabangnetPropertiesCode> searchSabangnetPropertiesCode(String propCode) {
		// 테스트 후 설정쪽에서 선언하여 가져와서 사용으로 변경예정
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.goodsPropCodeInfo");
		// xml경로는 외부에서 접근 가능한 경로로 설정해야함
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"),
				"testPropertiesCode.xml");

		List<InterfacesSabangnetPropertiesCode> propertiesCode = new ArrayList<InterfacesSabangnetPropertiesCode>();

		// xml파일 생성 성공여부
		boolean successFlag = false;

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			// SABANG_CS_ANS_REGI
			Element sabangGoodsPropCodeInfoList = doc.createElement("SABANG_GOODS_PROP_CODE_INFO_LIST");
			doc.appendChild(sabangGoodsPropCodeInfoList);

			// HEADER
			Element header = doc.createElement("HEADER");
			sabangGoodsPropCodeInfoList.appendChild(header);

			/* ST: HEADER 엘리먼트 */
			xmlHeader(doc, header, "otherInfo");
			/* ED: HEADER 엘리먼트 */

			// DATA 생성
			Element data = doc.createElement("DATA");
			sabangGoodsPropCodeInfoList.appendChild(data);

			Element objEl = doc.createElement("PROP1_CD");
			objEl.appendChild(doc.createTextNode(propCode));
			data.appendChild(objEl);

			successFlag = UtilsSabangnet.makeSabangnetXml(doc, xmlPath);

			if (successFlag) {
				String url = UtilsText.concat(sbnApiUrl, xmlPath);

				HttpResponse httpResponse = UtilsSabangnet.sendSabangnetHttp(url);
				HttpEntity entry = httpResponse.getEntity();

				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(entry.getContent(), "euc-kr"));
					String buffer = null;
					String testa = "";

					while ((buffer = br.readLine()) != null) {
						// strBuf.append(buffer).append("\r\n");
						log.debug("buffer > " + buffer);
						testa += buffer;
					}

					log.debug("httpResponse getEntity > " + httpResponse.getStatusLine() + " / " + testa);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return propertiesCode;

	}

	/**
	 * 사방넷 요약수정
	 */
	public void makeSabangnetGoodsInfo2() {
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.goodsInfo2");
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"), "testGoodsInfo2.xml");

		// xml파일 생성 성공여부
		boolean successFlag = false;

		List<InterfacesSabangnetSetItem2> itemList = null;

		try {
			// 1. 사방넷에 등록 할 아이템을 조회
			itemList = interfacesSabangnetDao.selectAffltsSendItem2();

			// 2. 조회된 아이템 리스트를 사방넷의 양식에 맞는 XML로 변환
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			// SABANG_GOODS_REGI
			Element sabangGoodsRegi = doc.createElement("SABANG_GOODS_REGI");
			doc.appendChild(sabangGoodsRegi);

			// HEADER
			Element header = doc.createElement("HEADER");
			sabangGoodsRegi.appendChild(header);

			/* ST: HEADER 엘리먼트 */
			xmlHeader(doc, header, "goodsInfo");
			/* ED: HEADER 엘리먼트 */

			// data 엘리먼트
			Element data = null;

			ObjectMapper oMapper = new ObjectMapper();

			/* ST: DATA 엘리먼트 */
			for (InterfacesSabangnetSetItem2 itemObj : itemList) {
				// DATA 생성
				data = doc.createElement("DATA");
				sabangGoodsRegi.appendChild(data);

				Map<String, Object> map = oMapper.convertValue(itemObj, Map.class);

				// 변수명을 사방넷 변수명과 동일하게 치환
				for (String key : map.keySet()) {
					char bfCul = 0;
					String afCul = "";

					// 사방넷 엘리먼트명에 맞춰서 컬럼명 변경
					afCul = UtilsSabangnet.changeSbnColumn(bfCul, afCul, key);

					String val = map.get(key).toString();
					// CDATA 적용
					val = UtilsSabangnet.validateHeaderCulomn(afCul, val, "goodsInfo");

					// 치환이 완료된 변수명으로 XMLID로 사용
					Element objEl = doc.createElement(afCul);
					objEl.appendChild(doc.createTextNode(val));
					data.appendChild(objEl);
				}

				// 옵셩정보 조회 후 옵션 엘리먼트 구성
				Element objOptEl = doc.createElement("SKU_INFO");
				data.appendChild(objOptEl);

				// 옵션정보 구성
				String optionPatten = "옐로우:55^^99^^5000";

				Element objOptValEl = doc.createElement("SKU_VALUE");
				objOptValEl.appendChild(doc.createTextNode(optionPatten));
				objOptEl.appendChild(objOptValEl);

			}
			/* ED: DATA 엘리먼트 */

			successFlag = UtilsSabangnet.makeSabangnetXml(doc, xmlPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3. 구성된 XML경로로 사방넷의 상품등록&수정 API를 호출
		// sbnApiUrl + xmlPath
		if (successFlag) {
			String url = UtilsText.concat(sbnApiUrl, xmlPath);

			HttpResponse httpResponse = UtilsSabangnet.sendSabangnetHttp(url);
			HttpEntity entry = httpResponse.getEntity();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(entry.getContent(), "euc-kr"));
				String buffer = null;
				String testa = "";

				while ((buffer = br.readLine()) != null) {
					// strBuf.append(buffer).append("\r\n");
					log.debug("buffer > " + buffer);
					testa += buffer;
				}

				log.debug("httpResponse getEntity > " + httpResponse.getStatusLine() + " / " + testa);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			log.debug("xml파일이 생성되지 않아 사방넷과 통신할 수 없습니다.");
		}

	}

	/**
	 * 사방넷 카테고리 조회
	 */
	public void getSabangnetCategoryList() {
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.categoryInfo");
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"), "testCategoryList.xml");
		// xml파일 생성 성공여부
		boolean successFlag = false;

		try {
			// 2. 조회된 아이템 리스트를 사방넷의 양식에 맞는 XML로 변환
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			// SABANG_CATEGORY_LIST
			Element sabangCategoryList = doc.createElement("SABANG_CATEGORY_LIST");
			doc.appendChild(sabangCategoryList);

			// HEADER
			Element header = doc.createElement("HEADER");
			sabangCategoryList.appendChild(header);

			/* ST: HEADER 엘리먼트 */
			xmlHeader(doc, header, "otherInfo");
			/* ED: HEADER 엘리먼트 */

			successFlag = UtilsSabangnet.makeSabangnetXml(doc, xmlPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (successFlag) {
			String url = UtilsText.concat(sbnApiUrl, xmlPath);

			HttpResponse httpResponse = UtilsSabangnet.sendSabangnetHttp(url);
			HttpEntity entry = httpResponse.getEntity();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(entry.getContent(), "euc-kr"));
				String buffer = null;
				String testa = "";

				while ((buffer = br.readLine()) != null) {
					// strBuf.append(buffer).append("\r\n");
					log.debug("buffer > " + buffer);
					testa += buffer;
				}

				log.debug("httpResponse getEntity > " + httpResponse.getStatusLine() + " / " + testa);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 사방넷 주문정보 수집
	 */
	public void getSabangnetOrderList() {
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.categoryInfo");
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"), "testCategoryList.xml");
		// xml파일 생성 성공여부
		boolean successFlag = false;

		List<InterfacesSabangnetOrderList> itemList = null;
		InterfacesSabangnetOrderList element = new InterfacesSabangnetOrderList();

		try {
			// 1. 사방넷에 등록 할 아이템을 조회
			element.setOrdStDate(UtilsDate.today("yyyyMMdd"));
			element.setOrdEdDate(UtilsDate.today("yyyyMMdd") + 1);
			element.setOrdField(
					"<![CDATA[IDX|ORDER_ID|MALL_ID|MALL_USER_ID|ORDER_STATUS|USER_ID|USER_NAME|USER_TEL|USER_CEL|USER_EMAIL|RECEIVE_TEL|RECEIVE_CEL|RECEIVE_EMAIL|DELV_MSG|RECEIVE_NAME|RECEIVE_ZIPCODE|RECEIVE_ADDR|TOTAL_COST|PAY_COST|ORDER_DATE|PARTNER_ID|DPARTNER_ID|MALL_PRODUCT_ID|PRODUCT_ID|SKU_ID|P_PRODUCT_NAME|P_SKU_VALUE|PRODUCT_NAME|SALE_COST|MALL_WON_COST|WON_COST|SKU_VALUE|SALE_CNT|DELIVERY_METHOD_STR|DELV_COST|COMPAYNY_GOODS_CD|SKU_ALIAS|BOX_EA|JUNG_CHK_YN|MALL_ORDER_SEQ|MALL_ORDER_ID|ETC_FIELD3|ORDER_GUBUN|P_EA|REG_DATE|ORDER_ETC_1|ORDER_ETC_2|ORDER_ETC_3|ORDER_ETC_4|ORDER_ETC_5|ORDER_ETC_6|ORDER_ETC_7|ORDER_ETC_8|ORDER_ETC_9|ORDER_ETC_10|ORDER_ETC_11|ORDER_ETC_12|ORDER_ETC_13|ORDER_ETC_14|ord_field2|copy_idx|GOODS_NM_PR|GOODS_KEYWORD|ORD_CONFIRM_DATE|RTN_DT|CHNG_DT|DELIVERY_CONFIRM_DATE|CANCEL_DT|CLASS_CD1|CLASS_CD2|CLASS_CD3|CLASS_CD4|BRAND_NM|DELIVERY_ID|INVOICE_NO|HOPE_DELV_DATE|FLD_DSP|INV_SEND_MSG|MODEL_NO|SET_GUBUN|ETC_MSG|DELV_MSG1|MUL_DELV_MSG|BARCODE|INV_SEND_DM|DELIVERY_METHOD_STR2]]>");
			itemList.add(0, element);

			// 2. 조회된 아이템 리스트를 사방넷의 양식에 맞는 XML로 변환
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);

			// SABANG_CATEGORY_LIST
			Element sabangOrderList = doc.createElement("SABANG_ORDER_LIST");
			doc.appendChild(sabangOrderList);

			// HEADER
			Element header = doc.createElement("HEADER");
			sabangOrderList.appendChild(header);

			/* ST: HEADER 엘리먼트 */
			xmlHeader(doc, header, "otherInfo");
			/* ED: HEADER 엘리먼트 */

			// data 엘리먼트
			Element data = null;

			ObjectMapper oMapper = new ObjectMapper();

			/* ST: DATA 엘리먼트 */
			for (InterfacesSabangnetOrderList itemObj : itemList) {
				// DATA 생성
				data = doc.createElement("DATA");
				sabangOrderList.appendChild(data);

				Map<String, Object> map = oMapper.convertValue(itemObj, Map.class);

				// 변수명을 사방넷 변수명과 동일하게 치환
				for (String key : map.keySet()) {
					char bfCul = 0;
					String afCul = "";

					// 사방넷 엘리먼트명에 맞춰서 컬럼명 변경
					afCul = UtilsSabangnet.changeSbnColumn(bfCul, afCul, key);

					String val = map.get(key).toString();
					// CDATA 적용
					val = UtilsSabangnet.validateHeaderCulomn(afCul, val, "goodsInfo");

					// 치환이 완료된 변수명으로 XMLID로 사용
					Element objEl = doc.createElement(afCul);
					objEl.appendChild(doc.createTextNode(val));
					data.appendChild(objEl);
				}

				// 옵셩정보 조회 후 옵션 엘리먼트 구성
				Element objOptEl = doc.createElement("SKU_INFO");
				data.appendChild(objOptEl);

				// 옵션정보 구성
				String optionPatten = "옐로우:55^^99^^5000";

				Element objOptValEl = doc.createElement("SKU_VALUE");
				objOptValEl.appendChild(doc.createTextNode(optionPatten));
				objOptEl.appendChild(objOptValEl);

			}
			/* ED: DATA 엘리먼트 */

			successFlag = UtilsSabangnet.makeSabangnetXml(doc, xmlPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2. 사방넷에 보낼 Invoice 정보 구성
	}

	/**
	 * 사방넷 송장번호 등록
	 */
	public void setSabangnetDelivNumber() {
		String sbnApiUrl = env.getProperty("interfaces.sabangnet.api.url.orderInvoice");
		String xmlPath = UtilsText.concat(env.getProperty("interfaces.sabangnet.api.xmlPath"), "orderInvoice.xml");
		String deliveryUrl = env.getProperty("interfaces.sabangnet.api.url.deliveryInfo");

		StringBuffer strBuf = new StringBuffer();

		CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent("HttpComponents/1.1").build();

		// 1. 사방넷 택배사 코드조회하여 내부 택배사의 코드를 변경... 아참 우리는 대한통운만 쓰지... 아래 전부 주석..ㅠ
		HttpGet httpget = new HttpGet(deliveryUrl);

		try {
			HttpResponse httpResponse = httpclient.execute(httpget);

			HttpEntity entry = httpResponse.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entry.getContent(), "euc-kr"));

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(entry.getContent());
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();

			XPathExpression exprchk = xPath.compile("/SABANG_DELIVERY_LIST");
			Object resultObj = exprchk.evaluate(xmlDoc, XPathConstants.NODE);
			Node node = (Node) resultObj;

			log.debug("nodeName > " + node.getNodeValue());

			String success = node == null ? null : node.getTextContent();

			log.debug("success > " + success);

			String buffer = null;

			while ((buffer = br.readLine()) != null) {
				// strBuf.append(buffer).append("\r\n");
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2. 사방넷에 보낼 Invoice 정보 구성

	}

}
