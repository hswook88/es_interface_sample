package kr.co.shop.interfaces.module.payment.nice;

import org.json.simple.JSONObject;

import kr.co.shop.interfaces.module.payment.nice.common.NmcConstants;
import kr.co.shop.interfaces.module.payment.nice.common.NmcException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NmcLiteOrder extends NmcLite {
	public NmcLiteOrder(String key, String iv, String actionType) {
		super(key, iv, actionType);
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

		plainObject.put("event_id", getReqValue("event_id"));
		plainObject.put("request_no", getReqValue("request_no"));
		plainObject.put("data_hash", hashedData);
		plainObject.put("data", encryptedData);

		return plainObject.toString();
	}

	protected JSONObject createEncryptedJsonData() {
		JSONObject json = new JSONObject();
		json.put("tr_id", getReqValue("tr_id"));
		json.put("member_id", getReqValue("member_id"));
		return json;
	}
}
