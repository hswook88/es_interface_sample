package kr.co.shop.interfaces.module.payment.nice;

import org.json.simple.JSONObject;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NmcLiteExchangeNetCancel extends NmcLite {
	public NmcLiteExchangeNetCancel(String key, String iv) {
		super(key, iv, "ExchangeNetCancel");
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = super.createEncryptedJsonData();

		json.put("use_amount", getReqValue("use_amount"));
		json.put("receipt_number", getReqValue("receipt_number"));

		return json;
	}

	@Override
	protected String createHashData() throws Exception {
		String[] hashFields = { "rcompany_id", "tr_id", "coupon_num", "branch_code", "pos_date", "pos_time" };
		StringBuilder sb = new StringBuilder();
		for (String field : hashFields) {
			sb.append(getReqValue(field));
		}

		return makeHash(sb.toString());
	}

	protected String getUri() {
		return NmcConstants.NMC_EXCHANGE_NETCANCEL_URI;
	}

	@Override
	protected void verifyHashData() throws Exception {
		// TODO Auto-generated method stub

	}
}
