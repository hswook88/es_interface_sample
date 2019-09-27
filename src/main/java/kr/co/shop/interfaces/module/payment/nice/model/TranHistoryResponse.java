package kr.co.shop.interfaces.module.payment.nice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 거래내역조회 응답전문
 * @FileName : TranHistoryResponse.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : nalpari
 */
@Data
@Slf4j
public class TranHistoryResponse extends CommonResponse {

	private static final long serialVersionUID = -2647978103623882389L;
	/**
	 * 거래내역 전체 리스트 갯수
	 */
	private String count;
	/**
	 * 거래내역 배열 max : 6
	 */
	List<TranHistory> tranArray;

	/**
	 * @Desc : 거래내역조회 응답전문 객체화
	 * @Method Name : convertObject
	 * @Date : 2019. 4. 10.
	 * @Author : nalpari
	 * @param param
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public TranHistoryResponse convertObject(HashMap<String, Object> param, String page) throws Exception {
		String responseBody = param.get("responseBody").toString();
		param.remove("responseBody");

		TranHistoryResponse result = new TranHistoryResponse();
//		NiceGiftUtil.convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		result.setCount(responseBody.substring(0, 2));
		log.debug("##### count: {}", responseBody.substring(0, 2));

		List<TranHistory> resArray = new ArrayList<>();

		if (!"00".equals(responseBody.substring(0, 2))) {
			int share = Integer.parseInt(responseBody.substring(0, 2)) / 6;
			if ((Integer.parseInt(responseBody.substring(0, 2)) % 6) > 0) {
				share += 1;
			}

			if (Integer.parseInt(page) > share) {
				throw new Exception("Over Page Exception");
			}

			int loop = 6;
//	        log.debug("if condition: {}", (Integer.parseInt(page) * 6) > Integer.parseInt(responseBody.substring(0, 2)));
			if ((Integer.parseInt(page) * 6) > Integer.parseInt(responseBody.substring(0, 2))) {
				loop = 6 - ((Integer.parseInt(page) * 6) - Integer.parseInt(responseBody.substring(0, 2)));
			}
			log.debug("##### loop: {}", loop);

			for (int i = 0; i < loop; i++) {
				TranHistory tmp = new TranHistory();
				int startPos = (48 * i) + 2;
				int endPos = (48 * (i + 1)) + 2;
				String singleRes = responseBody.substring(startPos, endPos);
				log.debug("##### i: {}", i);
				log.debug("##### singleRes: {}", singleRes);
				tmp.setTranType(singleRes.substring(0, 1));
				tmp.setTranDate(singleRes.substring(1, 7));
				tmp.setFranchisee("");
				tmp.setCatId(singleRes.substring(23, 33));
				tmp.setApprovalNum(singleRes.substring(33, 41));
				tmp.setTranAmount(Long.parseLong(singleRes.substring(41)));

				resArray.add(tmp);
			}
		}

		result.setTranArray(resArray);

		return result;
	}
}
