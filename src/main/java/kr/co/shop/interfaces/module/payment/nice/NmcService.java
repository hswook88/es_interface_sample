package kr.co.shop.interfaces.module.payment.nice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.shop.interfaces.module.payment.nice.model.NmcVO;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NmcService {
	@Value("${nice.nmc.rcompany_id}")
	private String rcompanyId;

	@Value("${nice.nmc.branch_code}")
	private String branchCode;

	@Value("${nice.nmc.symmKey}")
	private String symmKey;

	@Value("${nice.nmc.symmIv}")
	private String symmIv;

	@Value("${nice.nmc.memberId}")
	private String memberId;

	@Value("${nice.nmc.eventId}")
	private String eventId;

	@Value("${nice.nmc.goodsId}")
	private String goodsId;

	@Value("${nice.nmc.symmKeyOrderSend}")
	private String symmKeyOrderSend;

	@Value("${nice.nmc.symmIvOrderSend}")
	private String symmIvOrderSend;

	@Value("${nice.nmc.testServer}")
	private boolean testServer;

	/**
	 * @Desc :카카오쿠폰 정보 조회
	 * @Method Name : getKakaoCouponInfo
	 * @Date : 2019. 5. 21.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NmcVO getKakaoCouponInfo(NmcVO param) throws Exception {

		NmcVO result = new NmcVO();

		Map<String, Object> trData = initDataSet();

		NmcLiteExchangeQuery nmcLite = new NmcLiteExchangeQuery(symmKey, symmIv);

		// 테스트 연동 (운영 변환시 주석 처리)
		if (testServer) {
			log.debug("##### testServer run!!");
			nmcLite.setTestServer(testServer);
		}

		nmcLite.setReqField("rcompany_id", rcompanyId); // 사용처ID
		nmcLite.setReqField("branch_code", branchCode); // 지점ID
		nmcLite.setReqField("request_no", System.currentTimeMillis() + ""); // 요청 번호 (단순 로그 추적용)
		nmcLite.setReqField("tr_id", trData.get("tr_id").toString()); // 거래번호 (유일값)
		nmcLite.setReqField("coupon_num", param.getCouponNum()); // 쿠폰번호
		nmcLite.setReqField("origin_admit_num", param.getOriginAdmitNum()); // 원거래 승인번호
		nmcLite.setReqField("pos_code", "0000"); // POS번호
		nmcLite.setReqField("pos_date", trData.get("pos_date").toString()); // 요청일자
		nmcLite.setReqField("pos_time", trData.get("pos_time").toString()); // 요청시간
		nmcLite.setReqField("sale_date", trData.get("pos_date").toString()); // 영업일자(정산유형이 영업일기준인 경우 해당날짜로 정산)
		nmcLite.setReqField("sale_time", trData.get("pos_time").toString()); // 영업시간(정산유형이 영업일기준인 경우 해당날짜로 정산)

		// 조회 요청
		nmcLite.startAction();

		result.setResultCode(nmcLite.getResValue("result_code"));
		result.setResultMsg(nmcLite.getResValue("result_msg"));
		result.setResponseNo(nmcLite.getResValue("response_no"));
		result.setCouponNum(nmcLite.getResValue("coupon_num"));
		result.setProductType(nmcLite.getResValue("product_type"));
		result.setOrderBalance(nmcLite.getResValue("order_balance"));
		result.setProductCnt(nmcLite.getResValue("product_cnt"));
		result.setProductBarcode1(nmcLite.getResValue("product_barcode1"));
		result.setUsableCnt(nmcLite.getResValue("usable_cnt"));
		result.setDataHash(nmcLite.getResValue("data_hash"));

		return result;
	}

	/**
	 * @Desc : 카카오쿠폰 사용
	 * @Method Name : setKakaoCouponUse
	 * @Date : 2019. 5. 21.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NmcVO setKakaoCouponUse(NmcVO param) throws Exception {
		log.debug("##### nmcVO: {}", param);
		NmcVO result = new NmcVO();

		Map<String, Object> trData = initDataSet();

		NmcLiteExchange nmcLite = new NmcLiteExchange(symmKey, symmIv);

		// 테스트 연동 (운영 변환시 주석 처리)
		if (testServer) {
			log.debug("##### testServer run!!");
			nmcLite.setTestServer(testServer);
		}

		nmcLite.setReqField("rcompany_id", rcompanyId); // 사용처ID
		nmcLite.setReqField("branch_code", branchCode); // 지점ID
		nmcLite.setReqField("tr_id", trData.get("tr_id").toString()); // 거래번호(유일값)
		nmcLite.setReqField("request_no", System.currentTimeMillis() + ""); // 요청 번호 (단순 로그 추적용)
		nmcLite.setReqField("coupon_num", param.getCouponNum()); // 쿠폰번호
		nmcLite.setReqField("auth_pnm", param.getAuthPnm()); // 바코드 유형이 Two-Barcode 형태인 경우만 사용
		nmcLite.setReqField("use_amount", param.getUseAmount()); // 사용금액 (사용금액이 0원 이상 이어야 합니다.)
		nmcLite.setReqField("pos_code", "0000"); // POS번호
		nmcLite.setReqField("pos_date", trData.get("pos_date").toString()); // 요청일자
		nmcLite.setReqField("pos_time", trData.get("pos_time").toString()); // 요청시간
		nmcLite.setReqField("sale_date", trData.get("pos_date").toString()); // 영업일자(정산유형이 영업일기준인 경우 해당날짜로 정산)
		nmcLite.setReqField("sale_time", trData.get("pos_time").toString()); // 영업시간(정산유형이 영업일기준인 경우 해당날짜로 정산)

		// 승인 요청
		nmcLite.startAction();

		result.setResultCode(nmcLite.getResValue("result_code"));
		result.setResultMsg(nmcLite.getResValue("result_msg"));
		result.setResponseNo(nmcLite.getResValue("response_no"));
		result.setCouponNum(nmcLite.getResValue("coupon_num"));
		result.setUseAmount(nmcLite.getResValue("use_amount"));
		result.setOrderBalance(nmcLite.getResValue("order_balance"));
		result.setAdmitNum(nmcLite.getResValue("admit_num"));
		result.setCashReceiptAmount(nmcLite.getResValue("cash_receipt_amount"));
		result.setNoTaxAmount(nmcLite.getResValue("no_tax_amount"));
		result.setDataHash(nmcLite.getResValue("data_hash"));
		result.setTrId(trData.get("tr_id").toString());

		return result;
	}

	/**
	 * @Desc : 카카오톡 쿠폰 사용 취소
	 * @Method Name : setKakaoCouponUseCancel
	 * @Date : 2019. 5. 21.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NmcVO setKakaoCouponUseCancel(NmcVO param) throws Exception {
		log.debug("##### nmcVO: {}", param);
		NmcVO result = new NmcVO();

		Map<String, Object> trData = initDataSet();

		NmcLiteExchangeCancel nmcLite = new NmcLiteExchangeCancel(symmKey, symmIv);

		// 테스트 연동 (운영 변환시 주석 처리)
		if (testServer) {
			log.debug("##### testServer run!!");
			nmcLite.setTestServer(testServer);
		}

		nmcLite.setReqField("rcompany_id", rcompanyId); // 사용처ID
		nmcLite.setReqField("branch_code", branchCode); // 지점ID
		nmcLite.setReqField("request_no", System.currentTimeMillis() + ""); // 요청 번호 (단순 로그 추적용)
		nmcLite.setReqField("tr_id", trData.get("tr_id").toString()); // 거래번호(유일값)
		nmcLite.setReqField("coupon_num", param.getCouponNum()); // 쿠폰번호
		nmcLite.setReqField("origin_admit_num", param.getOriginAdmitNum()); // 원 승인번호
		nmcLite.setReqField("pos_code", "0000"); // POS번호
		nmcLite.setReqField("pos_date", trData.get("pos_date").toString()); // 요청일자
		nmcLite.setReqField("pos_time", trData.get("pos_time").toString()); // 요청시간
		nmcLite.setReqField("sale_date", trData.get("sale_date").toString()); // 영업일자(정산유형이 영업일기준인 경우 해당날짜로 정산)
		nmcLite.setReqField("sale_time", trData.get("sale_time").toString()); // 영업시간(정산유형이 영업일기준인 경우 해당날짜로 정산)

		// 취소 요청
		nmcLite.startAction();

		result.setResultCode(nmcLite.getResValue("result_code"));
		result.setResultMsg(nmcLite.getResValue("result_msg"));
		result.setResponseNo(nmcLite.getResValue("response_no"));
		result.setCouponNum(nmcLite.getResValue("coupon_num"));
		result.setOrderBalance(nmcLite.getResValue("order_balance"));
		result.setAdmitNum(nmcLite.getResValue("admit_num"));

		return result;
	}

	/**
	 * @Desc : 기프트카드 판매
	 * @Method Name : setOrderSendResult
	 * @Date : 2019. 5. 21.
	 * @Author : nalpari
	 * @param param
	 * @return
	 */
	public NmcVO setOrderSendResult(NmcVO param) {
		log.debug("##### nmcVO: {}", param);
		NmcVO result = new NmcVO();

		Map<String, Object> trData = initDataSet();

		NmcLiteOrderSend nmcLite = new NmcLiteOrderSend(symmKeyOrderSend, symmIvOrderSend);

		// 테스트 연동 (운영 변환시 주석 처리)
		if (testServer) {
			log.debug("##### testServer run!!");
			nmcLite.setTestServer(testServer);
		}

		nmcLite.setReqField("event_id", eventId); // 이벤트ID
		nmcLite.setReqField("member_id", memberId); // 판매대행사 멤버ID
		nmcLite.setReqField("goods_id", goodsId); // 판매상품ID
		nmcLite.setReqField("tr_id", trData.get("tr_id").toString()); // 거래 ID (취소 시 사용)
		nmcLite.setReqField("request_no", System.currentTimeMillis() + ""); // 요청 번호 (단순 로그 추적용)
		nmcLite.setReqField("order_mobile", param.getOrderMobile()); // 발송번호 (나이스모바일쿠폰시스템에 사전에 등록 되어야 함.)
		nmcLite.setReqField("receivermobile", param.getReceiverMobile()); // 수신자번호
		nmcLite.setReqField("sms_type", param.getSmsType()); // 발송유형
		nmcLite.setReqField("title", param.getTitle()); // 문자메시지 제목
		nmcLite.setReqField("content", param.getContent()); // 문자메시지 내용

		nmcLite.startAction();

		result.setResultCode(nmcLite.getResValue("result_code"));
		result.setResultMsg(nmcLite.getResValue("result_msg"));
		result.setResponseNo(nmcLite.getResValue("response_no"));
		result.setCouponNum(nmcLite.getResValue("coupon_num"));
		result.setAuthPnm(nmcLite.getResValue("auth_phm"));
		result.setExchangeEnddate(nmcLite.getResValue("exchange_enddate"));
		result.setDataHash(nmcLite.getResValue("data_hash"));

		return result;
	}

	public Map<String, Object> initDataSet() {
		Map<String, Object> result = new HashMap<>();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tf = new SimpleDateFormat("HHmmss");
		SimpleDateFormat trdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		Date today = new Date();
//        String pos_date = df.format(today);
//        String pos_time = tf.format(today);
//        String tr_id = trdf.format(today);

		result.put("pos_date", df.format(today));
		result.put("pos_time", tf.format(today));
		result.put("tr_id", trdf.format(today));

		return result;
	}
}
