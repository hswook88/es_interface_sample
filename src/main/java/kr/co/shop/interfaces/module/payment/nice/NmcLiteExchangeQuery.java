package kr.co.shop.interfaces.module.payment.nice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import kr.co.shop.interfaces.module.payment.nice.common.NmcException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NmcLiteExchangeQuery extends NmcLite {
	public NmcLiteExchangeQuery(String key, String iv) {
		super(key, iv, "ExchangeQuery");
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = super.createEncryptedJsonData();

		json.put("origin_admit_num", getReqValue("origin_admit_num"));

		return json;
	}

	protected void parseResult(String responeMsg) throws Exception {
		super.parseResult(responeMsg);

		String product_barcodeList = getResValue("product_barcodeList");
		if (!"".equals(product_barcodeList)) {
			JSONArray resultDecJsonArray = (JSONArray) JSONValue.parse(product_barcodeList);
			for (int i = 0; i < resultDecJsonArray.size(); ++i) {
				JSONObject resultDecJsonObject = (JSONObject) resultDecJsonArray.get(i);
				parseJsonOjbect(resultDecJsonObject);
			}
		}
	}

	@Override
	protected String createHashData() throws Exception {
		String[] hashFields = { "rcompany_id", "tr_id", "coupon_num", "origin_admit_num", "branch_code", "pos_date",
				"pos_time" };
		StringBuilder sb = new StringBuilder();
		for (String field : hashFields) {
			sb.append(getReqValue(field));
		}

		return makeHash(sb.toString());
	}

	protected String getUri() {
		return NmcConstants.NMC_EXCHANGE_QUERY_URI;
	}

	@Override
	protected void verifyHashData() throws Exception {

		String result_code = getResValue("result_code");
		if (!"0000".equals(result_code))
			return;

		String[] hashFields = { "result_code", "tr_id", "coupon_num" };
		StringBuilder sb = new StringBuilder();
		for (String field : hashFields) {
			sb.append(getResValue(field));
		}
		String hashValue = makeHash(sb.toString());
		String dataHash = getResValue("data_hash");

		if (!hashValue.equals(dataHash)) {
			log.error("Verify HashData Failed! [" + hashValue + "][" + dataHash + "]");
			throw new NmcException(NmcConstants.API_ERR_4004_CODE, NmcConstants.API_ERR_4004_MSG);
		}

	}
}
