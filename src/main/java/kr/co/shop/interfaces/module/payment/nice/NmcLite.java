package kr.co.shop.interfaces.module.payment.nice;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.MDC;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import kr.co.shop.interfaces.module.payment.nice.common.NmcException;
import kr.co.shop.interfaces.module.payment.nice.common.StopWatch;
import kr.co.shop.interfaces.module.payment.nice.common.StringEncrypter;
import kr.co.shop.interfaces.module.payment.nice.model.QueryString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NmcLite {
	protected ArrayList<QueryString> reqList;
	protected ArrayList<QueryString> resList;

	protected String symmKey;
	protected String symmIv;

	protected StringEncrypter stringEncrypter;
	protected String actionType = "";

	protected boolean testServer = false;
	protected boolean isTrNetCancel = false;

	public NmcLite(String key, String iv, String actionType) {

		MDC.put("TID", System.currentTimeMillis() + "");

		reqList = new ArrayList<QueryString>();
		resList = new ArrayList<QueryString>();

		symmKey = key;
		symmIv = iv;
		this.actionType = actionType;
	}

	public void setTestServer(boolean dev) {
		testServer = dev;
	}

	private void setError(String result_code, String result_msg) {
		setResField("result_code", result_code);
		setResField("result_msg", result_msg);
	}

	public void startAction() {

		StopWatch stopWatch = new StopWatch();
		log.info(NmcConstants.NMC_LIB_VERSION);
		try {
			doProcess();
		} catch (Exception e) {
			log.error(" doProcess Error", e);
		} finally {
			if (isTrNetCancel) {
				log.info("Start NetCancel!");
				startTrNetCancel();
			}
			log.info("END [" + actionType + "][" + getResValue("result_code") + "][" + getResValue("result_msg") + "]["
					+ stopWatch.getTotalTimeString() + "]");
		}
	}

	private void doProcess() throws Exception {

		stringEncrypter = new StringEncrypter(symmKey, symmIv);

		String msg = "";
		try {
			msg = makeMessage();
			log.info("Make Msg Ok!");
			System.out.println("###### msg: " + msg);
		} catch (NmcException e) {
			setError(e.getCode(), e.getMsg());
			log.error("Make Msg Faild", e);
			throw e;
		} catch (Exception e) {
			setError(NmcConstants.API_ERR_4001_CODE, NmcConstants.API_ERR_4001_MSG);
			log.error("Make Msg Faild", e);
			throw e;
		}

		String responeBody = "";
		try {
			responeBody = sendMessage(msg);
		} catch (NmcException e) {
			if (NmcConstants.API_ERR_5002_CODE.equals(e.getCode()) || NmcConstants.API_ERR_5003_CODE.equals(e.getCode())
					|| NmcConstants.API_ERR_5004_CODE.equals(e.getCode())) {
				setTrNetCancel(true);
			}
			log.error("Server Http Send & Recive Faild(NmcException)", e);
			setError(e.getCode(), e.getMsg());
			throw e;
		}

		try {
			parseResult(responeBody);
			log.info("Parse Result OK!");
		} catch (NmcException e) {
			setTrNetCancel(true);
			setError(e.getCode(), e.getMsg());
			throw e;
		} catch (Exception e) {
			setTrNetCancel(true);
			log.error("Parse Result Failed!", e);
			throw e;
		}

		try {
			verifyHashData();
			log.info("Verify HashData OK!");
		} catch (NmcException e) {
			setTrNetCancel(true);
			setError(e.getCode(), e.getMsg());
			throw e;
		} catch (Exception e) {
			log.error("Verify HashData Failed!", e);
			setTrNetCancel(true);
			throw e;
		}
	}

	public String sendMessage(String message) throws Exception {
		System.out.println("#### message : " + message);
		int timout = NmcConstants.SERVER_TIMEOUT_MILL;

		String url = getServiceURL();
		log.info("Server Url:[" + url + "], Timeout:[" + timout + "]");

		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_0);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timout);
		client.getHttpConnectionManager().getParams().setSoTimeout(timout);

		PostMethod post = new PostMethod(url);

		try {

			post.addRequestHeader("Content-Type", "application/json");
			post.setRequestEntity(new StringRequestEntity(message));
			log.info("executeMethod");
			int returnCode = client.executeMethod(post);
			if (returnCode != HttpStatus.SC_OK) {
				log.error("Remote Server Receive Failed(Internal Error Http Status Code:[" + returnCode + "])");
				throw new NmcException(NmcConstants.API_ERR_5002_CODE, NmcConstants.API_ERR_5002_MSG);
			}
			log.info("Recv Msg OK!");
			String body = post.getResponseBodyAsString();
			System.out.println("##### body " + body);
			return body;
		} catch (ConnectTimeoutException ex) {
			log.error("Remote Server Conenction Failed(ConnectTimeoutException)", ex);
			throw new NmcException(NmcConstants.API_ERR_5001_CODE, NmcConstants.API_ERR_5001_MSG);
		} catch (SocketTimeoutException ex) {
			log.error("Remote Server Receive Failed(SocketTimeoutException)", ex);
			throw new NmcException(NmcConstants.API_ERR_5003_CODE, NmcConstants.API_ERR_5003_MSG);
		} catch (NmcException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Remote Server Error(Exception)", ex);
			throw new NmcException(NmcConstants.API_ERR_5004_CODE, NmcConstants.API_ERR_5004_MSG);
		} finally {
			post.releaseConnection();
		}
	}

	protected String makeMessage() throws Exception {

		JSONObject plainObject = new JSONObject();
		JSONObject encJson = createEncryptedJsonData();

		String encryptedData = "";
		try {
			encryptedData = encrypt(encJson.toString());
		} catch (Exception e) {
			log.error("Encrypt Failed!", e);
			throw new NmcException(NmcConstants.API_ERR_4002_CODE, NmcConstants.API_ERR_4002_MSG);
		}

		String hashedData = "";
		try {
			hashedData = createHashData();
		} catch (Exception e) {
			log.error("Make Hash Failed!", e);
			throw new NmcException(NmcConstants.API_ERR_4003_CODE, NmcConstants.API_ERR_4003_MSG);
		}

		plainObject.put("rcompany_id", getReqValue("rcompany_id"));
		plainObject.put("request_no", getReqValue("request_no"));
		plainObject.put("data_hash", hashedData);
		plainObject.put("data", encryptedData);

		return plainObject.toString();
	}

	protected void parseResult(String responeMsg) throws Exception {

		try {
			JSONObject resultPlainObject = (JSONObject) JSONValue.parse(responeMsg);

			String result_code = (String) resultPlainObject.get("result_code");
			String result_msg = URLDecoder.decode((String) resultPlainObject.get("result_msg"), "EUC-KR");
			String data_hash = (String) resultPlainObject.get("data_hash");

			String result_data = (String) resultPlainObject.get("result_data");

			setResField("result_code", result_code);
			setResField("result_msg", result_msg);
			setResField("data_hash", data_hash);
			System.out.println("##### result_code : " + result_code);
			System.out.println("##### result_msg : " + result_msg);
			System.out.println("##### data_hash : " + data_hash);

			if (result_data != null) {
				String decryptedJsonString = URLDecoder.decode(stringEncrypter.decrypt(result_data), "EUC-KR");
				JSONObject resultDecJsonObject = (JSONObject) JSONValue.parse(decryptedJsonString);
				parseJsonOjbect(resultDecJsonObject);
				System.out.println("##### result_data : " + decryptedJsonString);
				System.out.println("##### parseObject : " + resultDecJsonObject.toString());
				System.out.println("##### parseObject : " + resultDecJsonObject.toJSONString());
			}

		} catch (Exception e) {
			log.error("Parse Result Failed!", e);
			throw new NmcException(NmcConstants.API_ERR_6001_CODE, NmcConstants.API_ERR_6001_MSG);
		}
	}

	protected void parseJsonOjbect(JSONObject jsonObj) {
		for (Object key : jsonObj.keySet()) {
			String keyStr = (String) key;
			Object keyValue = jsonObj.get(keyStr);
			if (keyValue instanceof JSONObject) {
				parseJsonOjbect((JSONObject) keyValue);
			} else {
				setResField(keyStr, keyValue + "");
			}
		}
	}

	protected JSONObject createEncryptedJsonData() {

		JSONObject json = new JSONObject();

		json.put("tr_id", getReqValue("tr_id"));
		json.put("coupon_num", getReqValue("coupon_num"));
		json.put("branch_code", getReqValue("branch_code"));
		json.put("pos_code", getReqValue("pos_code"));
		json.put("pos_date", getReqValue("pos_date"));
		json.put("pos_time", getReqValue("pos_time"));
		json.put("sale_date", getReqValue("sale_date"));
		json.put("sale_time", getReqValue("sale_time"));
		json.put("auth_pnm", getReqValue("auth_pnm"));

		return json;
	}

	protected JSONObject createOrderEncryptedJsonData() throws UnsupportedEncodingException {

		JSONObject json = new JSONObject();

		json.put("event_id", getReqValue("event_id"));
		json.put("tr_id", getReqValue("tr_id"));
		json.put("member_id", getReqValue("member_id"));
		json.put("goods_id", getReqValue("goods_id"));
		json.put("order_mobile", getReqValue("order_mobile"));
		json.put("receivermobile", getReqValue("receivermobile"));

		return json;
	}

	protected abstract String createHashData() throws Exception;

	protected abstract void verifyHashData() throws Exception;

	protected abstract String getUri();

	public void setReqField(String name, String value) {

		if (value == null)
			value = "";
		QueryString qs = getReqQueryString(name);
		if (qs != null)
			qs.setValue(value);
		qs = new QueryString(name, value);
		reqList.add(qs);
	}

	protected QueryString getReqQueryString(String name) {

		for (QueryString qs : reqList) {
			if (name.equals(qs.getName()))
				return qs;
		}

		return null;
	}

	protected String getReqValue(String name) {

		for (QueryString qs : reqList) {
			if (name.equals(qs.getName()))
				return qs.getValue();
		}

		return "";
	}

	public void setResField(String name, String value) {

		if (value == null)
			value = "";
		QueryString qs = getResQueryString(name);
		if (qs != null)
			qs.setValue(value);
		qs = new QueryString(name, value);
		resList.add(qs);
	}

	protected QueryString getResQueryString(String name) {

		for (QueryString qs : resList) {
			if (name.equals(qs.getName()))
				return qs;
		}

		return null;
	}

	public String getResValue(String name) {

		for (QueryString qs : resList) {
			if (name.equals(qs.getName()))
				return qs.getValue();
		}

		return "";
	}

	protected String encrypt(String plainText) throws Exception {
		return stringEncrypter.encrypt(plainText);
	}

	protected String makeHash(String plainText) throws Exception {
		return stringEncrypter.sha256(plainText);
	}

	private String getServiceURL() {
		if (testServer) {
			return NmcConstants.TEST_NMC_SERVER_DOMAIN + getUri();
		} else {
			return NmcConstants.NMC_SERVER_DOMAIN + getUri();
		}
	}

	private void setTrNetCancel(boolean trNetCancel) {
		isTrNetCancel = trNetCancel;
	}

	private void startTrNetCancel() {

		if ("Exchange".equals(actionType)) {

			SimpleDateFormat trdf = new SimpleDateFormat("yyyyMMddHHmmssSS");

			Date today = new Date();
			String tr_id = trdf.format(today);

			NmcLiteExchangeNetCancel nmcLite = new NmcLiteExchangeNetCancel(symmKey, symmIv);
			nmcLite.setTestServer(testServer);

			nmcLite.setReqField("rcompany_id", getReqValue("rcompany_id"));
			nmcLite.setReqField("request_no", System.currentTimeMillis() + "");// 요청 번호 (단순 로그 추적용)
			nmcLite.setReqField("tr_id", getReqValue("tr_id"));
			nmcLite.setReqField("coupon_num", getReqValue("coupon_num")); // 쿠폰번호
			nmcLite.setReqField("auth_pnm", getReqValue("auth_pnm")); // 바코드 유형이 Two-Barcode 형태인 경우만 사용
			nmcLite.setReqField("use_amount", getReqValue("use_amount")); // 사용 금액
			nmcLite.setReqField("branch_code", getReqValue("branch_code"));
			nmcLite.setReqField("pos_code", getReqValue("pos_code"));
			nmcLite.setReqField("pos_date", getReqValue("pos_date"));
			nmcLite.setReqField("pos_time", getReqValue("pos_time"));
			nmcLite.setReqField("sale_date", getReqValue("sale_date"));
			nmcLite.setReqField("sale_time", getReqValue("sale_time"));

			nmcLite.startAction();

			String result_code = nmcLite.getResValue("result_code"); // 응답코드 '0000' 경우 만 성공
			String result_msg = nmcLite.getResValue("result_msg"); // 응답메시지

			log.info("END NetCancelResult[" + actionType + "][" + result_code + "][" + result_msg + "]");
		}
	}
}
