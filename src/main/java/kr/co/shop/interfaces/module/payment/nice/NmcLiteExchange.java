package kr.co.shop.interfaces.module.payment.nice;

import org.json.simple.JSONObject;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import kr.co.shop.interfaces.module.payment.nice.common.NmcException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NmcLiteExchange extends NmcLite {
	public NmcLiteExchange(String key, String iv) {
		super(key, iv, "Exchange");
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = super.createEncryptedJsonData();

		json.put("use_amount", getReqValue("use_amount"));
		json.put("receipt_number", getReqValue("receipt_number"));

		return json;
	}

	@Override
	protected String createHashData() throws Exception {
		String[] hashFields = { "rcompany_id", "tr_id", "coupon_num", "use_amount", "branch_code", "pos_date",
				"pos_time" };
		StringBuilder sb = new StringBuilder();
		for (String field : hashFields) {
			sb.append(getReqValue(field));
		}
		return makeHash(sb.toString());
	}

	protected String getUri() {
		return NmcConstants.NMC_EXCHANGE_URI;
	}

	@Override
	protected void verifyHashData() throws Exception {

		String result_code = getResValue("result_code");
		if (!"0000".equals(result_code))
			return;

		String[] hashFields = { "result_code", "tr_id", "coupon_num", "admit_num" };
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
