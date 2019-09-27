package kr.co.shop.interfaces.module.payment.nice;

import org.json.simple.JSONObject;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;

public class NmcLiteExchangeCancel extends NmcLite {

	public NmcLiteExchangeCancel(String key, String iv) {
		super(key, iv, "교환취소");
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = super.createEncryptedJsonData();

		json.put("origin_admit_num", getReqValue("origin_admit_num"));
		json.put("receipt_number", getReqValue("receipt_number"));

		return json;
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
		return NmcConstants.NMC_EXCHANGE_CANCEL_URI;
	}

	@Override
	protected void verifyHashData() throws Exception {
		// TODO Auto-generated method stub

	}
}
