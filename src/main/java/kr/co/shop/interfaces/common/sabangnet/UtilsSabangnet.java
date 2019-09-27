package kr.co.shop.interfaces.common.sabangnet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;

import kr.co.shop.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsSabangnet {
	// 상품등록 및 수정 & 상품요약 CDATA 사용컬럼
	private static String[] goodsInfo = { "GOODS_NM", "GOODS_KEYWORD", "MODEL_NM", "MODEL_NO", "BRAND_NM",
			"COMPAYNY_GOODS_CD", "GOODS_SEARCH", "CLASS_CD1", "CLASS_CD2", "CLASS_CD3", "CLASS_CD4", "PARTNER_ID",
			"DPARTNER_ID", "MAKER", "ORIGIN", "MAKE_YEAR", "MAKE_DM", "CHAR_1_NM", "CHAR_1_VAL", "CHAR_2_NM",
			"CHAR_2_VAL", "IMG_PATH", "IMG_PATH1", "IMG_PATH2", "IMG_PATH3", "IMG_PATH4", "IMG_PATH5", "IMG_PATH6",
			"IMG_PATH7", "IMG_PATH8", "IMG_PATH9", "IMG_PATH10", "IMG_PATH11", "IMG_PATH12", "IMG_PATH13", "IMG_PATH14",
			"IMG_PATH15", "IMG_PATH16", "IMG_PATH17", "IMG_PATH18", "IMG_PATH19", "IMG_PATH20", "IMG_PATH21",
			"IMG_PATH22", "IMG_PATH23", "IMG_PATH24", "GOODS_REMARKS", "CERTNO", "CERT_AGENCY", "CERTFIELD",
			"STOCK_USE_YN", "OPT_TYPE", "PROP_VAL1", "PROP_VAL2", "PROP_VAL3", "PROP_VAL4", "PROP_VAL5", "PROP_VAL6",
			"PROP_VAL7", "PROP_VAL8", "PROP_VAL9", "PROP_VAL10", "PROP_VAL11", "PROP_VAL12", "PROP_VAL13", "PROP_VAL14",
			"PROP_VAL15", "PROP_VAL16", "PROP_VAL17", "PROP_VAL18", "PROP_VAL19", "PROP_VAL20", "PROP_VAL21",
			"PROP_VAL22", "PROP_VAL23", "PROP_VAL24", "PROP_VAL25", "PROP_VAL26", "PROP_VAL27", "PROP_VAL28",
			"PACK_CODE_STR", "GOODS_NM_EN", "GOODS_NM_PR", "GOODS_REMARKS2", "GOODS_REMARKS3", "GOODS_REMARKS4",
			"IMPORTNO", "GOODS_COST2", "ORIGIN2", "EXPIRE_DM", "SUPPLY_SAVE_YN", "DESCRITION" };

	// 송장등록 CDATA 사용컬럼
	private static String[] orderInvoice = { "SABANGNET_IDX", "TAK_CODE", "TAK_INVOICE", "DELV_HOPE_DATE" };

	/**
	 * CDATA 사용여부 확인 후 적용하여 값 반환
	 * 
	 * @param col
	 * @param val
	 * @param gubun
	 * @return
	 */
	public static String validateHeaderCulomn(String col, String val, String gubun) {
		boolean chkFlag = false;

		switch (gubun) {
		case "goodsInfo":
			chkFlag = matchColumn(goodsInfo, col);
			break;
		case "goodsInfo2":
			chkFlag = matchColumn(goodsInfo, col);
			break;
		case "orderInvoice":
			chkFlag = matchColumn(orderInvoice, col);
			break;
		}

		if (chkFlag && !val.equals("")) {
			val = UtilsText.concat("<![CDATA[", val, "]]>");
		}

		return val;
	}

	/**
	 * 컬럼 비교하여 매칭여부를 반환
	 * 
	 * @param chkCol
	 * @param orignalCol
	 * @return
	 */
	public static boolean matchColumn(String[] chkCol, String orignalCol) {
		boolean chkColFlag = false;

		for (String cul : chkCol) {
			if (cul.equals(orignalCol)) {
				chkColFlag = true;
				break;
			} else {
				continue;
			}
		}

		return chkColFlag;
	}

	/**
	 * xml파일 생성
	 * 
	 * @param doc
	 * @param xmlPath
	 */
	public static boolean makeSabangnetXml(Document doc, String xmlPath) {
		boolean successFlag = false;
		// XML 파일로 생성
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		try {
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("encoding", "euc-kr");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File(xmlPath)));

			transformer.transform(source, result);
			successFlag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successFlag;
	}

	/**
	 * 사방넷 엘리먼트명과 동일하게 변경
	 * 
	 * @param bfCul
	 * @param afCul
	 * @param key
	 * @return
	 */
	public static String changeSbnColumn(char bfCul, String afCul, String key) {
		for (int a = 0; a < key.length(); a++) {
			bfCul = key.charAt(a);
			if (bfCul >= 65 && bfCul <= 90) {
				afCul += UtilsText.concat("_", String.valueOf(bfCul));
			} else if (bfCul >= 97 && bfCul <= 122) {
				afCul += String.valueOf(bfCul).toUpperCase();
			} else {
				if (key.equals("char1Nm") || key.equals("char1Val") || key.equals("char2Nm")
						|| key.equals("char2Val")) {
					afCul += UtilsText.concat("_", String.valueOf(bfCul));
				} else {
					afCul += String.valueOf(bfCul);
				}
			}
		}

		return afCul;
	}

	/**
	 * 사방넷 API호출 httpGet
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static HttpResponse sendSabangnetHttp(String apiUrl) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent("HttpComponents/1.1").build();

		HttpGet httpget = new HttpGet(apiUrl);
		HttpResponse httpResponse = null;

		try {
			httpResponse = httpclient.execute(httpget);
			// HttpEntity entry = httpResponse.getEntity();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// returnString = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// returnString = e.getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// returnString = e.getMessage();
		}

		return httpResponse;
	}

}
