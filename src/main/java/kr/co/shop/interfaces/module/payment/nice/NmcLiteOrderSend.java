package kr.co.shop.interfaces.module.payment.nice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.simple.JSONObject;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NmcLiteOrderSend extends NmcLiteOrder {
	public NmcLiteOrderSend(String key, String iv) {
		super(key, iv, "OrderSend");
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = null;
		try {
			json = super.createEncryptedJsonData();

			json.put("goods_id", getReqValue("goods_id"));
			json.put("order_mobile", getReqValue("order_mobile"));
			json.put("receivermobile", getReqValue("receivermobile"));
			json.put("sms_type", getReqValue("sms_type"));
			json.put("title", URLEncoder.encode(getReqValue("title"), "EUC-KR"));
			json.put("content", URLEncoder.encode(getReqValue("content"), "EUC-KR"));
			return json;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	protected String createHashData() throws Exception {
		String[] hashFields = { "event_id", "tr_id", "member_id", "goods_id", "order_mobile", "receivermobile",
				"sms_type" };
		StringBuilder sb = new StringBuilder();
		for (String field : hashFields) {
			sb.append(getReqValue(field));
		}
		return makeHash(sb.toString());
	}

	protected String getUri() {
		return NmcConstants.NMC_ORDER_SEND_URI;
	}

	@Override
	protected void verifyHashData() throws Exception {
		// TODO Auto-generated method stub

	}
}
