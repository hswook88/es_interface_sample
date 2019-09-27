package kr.co.shop.interfaces.module.payment.kakao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.shop.interfaces.module.payment.base.AbstractPaymentLogService;
import kr.co.shop.interfaces.module.payment.base.IPaymentService;
import kr.co.shop.interfaces.module.payment.base.PaymentConst;
import kr.co.shop.interfaces.module.payment.base.PaymentException;
import kr.co.shop.interfaces.module.payment.base.PaymentResult;
import kr.co.shop.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.shop.interfaces.module.payment.config.KakaoPaymentConfig;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentApproval;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentApprovalReturn;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentAuthority;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentAuthorityReturn;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentCancel;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentCancelReturn;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentOrder;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentOrderList;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentOrderListReturn;
import kr.co.shop.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 카카오 페이 연동 service
 * @FileName : KakaoPaymentService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Component
@Slf4j
public class KakaoPaymentService extends AbstractPaymentLogService implements IPaymentService {

	private String payMethodType = PaymentConst.PAYMENT_METHOD_TYPE_KAKAO;

	@Autowired
	KakaoPaymentConfig kakaoPaymentConfig;

	@Autowired
	KakaoPaymentAPI kakaoPaymentAPI;

	/**
	 * @Desc : 결제방법 코드
	 * @return {@link PaymentConst#PAYMENT_METHOD_TYPE_NAVER}
	 * @throws PaymentException
	 */
	@Override
	public String getPayMethodType() throws PaymentException {
		return payMethodType;
	}

	/**
	 * @Desc :카카오 페이 인증.
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult authority(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " kakao pay authority ::::::::::");

		super.fileLog(payInfo);

		KakaoPaymentAuthority kakaoPaymentAuthority = (KakaoPaymentAuthority) payInfo.getPayInfo();

		Map<String, Object> kakaoMap = new HashMap<String, Object>();

		kakaoMap.put("cid", kakaoPaymentConfig.getCid());
		kakaoMap.put("partner_order_id", kakaoPaymentAuthority.getPartnerOrderId());
		kakaoMap.put("partner_user_id", kakaoPaymentAuthority.getPartnerUserId());

		kakaoMap.put("item_name", kakaoPaymentAuthority.getItemName());
		kakaoMap.put("quantity", Integer.toString(kakaoPaymentAuthority.getQuantity()));
		kakaoMap.put("total_amount", Integer.toString(kakaoPaymentAuthority.getTotalAmount()));
		kakaoMap.put("tax_free_amount", Integer.toString(kakaoPaymentAuthority.getTaxFreeAmount()));

		kakaoMap.put("approval_url", kakaoPaymentAuthority.getApprovalUrl()); // 상수처리여부 확인
		kakaoMap.put("cancel_url", kakaoPaymentAuthority.getCancelUrl()); // 상수처리여부 확인
		kakaoMap.put("fail_url", kakaoPaymentAuthority.getFailUrl()); // 상수처리여부 확인

		// PaymentResult paymentResult = new PaymentResult("Y");

		try {

			ObjectMapper mapper = new ObjectMapper();
			KakaoPaymentAuthorityReturn response = mapper.readValue(
					kakaoPaymentAPI.sendHttpsRequest(kakaoPaymentConfig.getReady(), kakaoMap).toString(),
					KakaoPaymentAuthorityReturn.class);

			log.debug("response   >" + response);

			if (response.getCode() == null) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, response.getCode(), "", response,
						PaymentConst.PAYMENT_AUTHORITY_SUCCESS_MESSAGE, response.getMsg());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, response.getCode(), "", response,
						PaymentConst.PAYMENT_AUTHORITY_FAIL_MESSAGE, response.getMsg());
				return paymentResult;
			}

		} catch (Exception e) {
			log.debug(" PaymentException " + e.getMessage());
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * @Desc :카카오 페이 승인.
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult approval(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " kakao pay approval ::::::::::");

		super.fileLog(payInfo);

		KakaoPaymentApproval kakaoPaymentApproval = (KakaoPaymentApproval) payInfo.getPayInfo();

		Map<String, Object> kakaoMap = new HashMap<String, Object>();

		kakaoMap.put("cid", kakaoPaymentConfig.getCid());
		kakaoMap.put("tid", kakaoPaymentApproval.getTid());
		kakaoMap.put("partner_order_id", kakaoPaymentApproval.getPartnerOrderId());
		kakaoMap.put("partner_user_id", kakaoPaymentApproval.getPartnerUserId());
		kakaoMap.put("pg_token", kakaoPaymentApproval.getPgToken());

		try {

			ObjectMapper mapper = new ObjectMapper();
			KakaoPaymentApprovalReturn response = mapper.readValue(
					kakaoPaymentAPI.sendHttpsRequest(kakaoPaymentConfig.getApprove(), kakaoMap).toString(),
					KakaoPaymentApprovalReturn.class);

			log.debug("response   >" + response);
			if (response.getCode() == null) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, response.getCode(), "", response,
						PaymentConst.PAYMENT_APPROVAL_SUCCESS_MESSAGE, response.getMsg());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, response.getCode(), "", response,
						PaymentConst.PAYMENT_APPROVAL_FAIL_MESSAGE, response.getMsg());
				return paymentResult;
			}

		} catch (Exception e) {
			log.debug(" PaymentException " + e.getMessage());
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * @Desc :카카오 페이 취소.
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult cancel(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " kakao pay cancel ::::::::::");

		super.fileLog(payInfo);

		KakaoPaymentCancel kakaoPaymentCancel = (KakaoPaymentCancel) payInfo.getPayInfo();

		Map<String, Object> kakaoMap = new HashMap<String, Object>();

		kakaoMap.put("cid", kakaoPaymentConfig.getCid()); // 가맹점 코드
		kakaoMap.put("tid", kakaoPaymentCancel.getTid());
		kakaoMap.put("cancel_amount", String.valueOf(kakaoPaymentCancel.getCancelAmount())); // 취소 금액
		kakaoMap.put("cancel_tax_free_amount", String.valueOf(kakaoPaymentCancel.getCancelTaxFreeAmount())); // 취소 비과세

		try {

			ObjectMapper mapper = new ObjectMapper();
			KakaoPaymentCancelReturn response = mapper.readValue(
					kakaoPaymentAPI.sendHttpsRequest(kakaoPaymentConfig.getCancel(), kakaoMap).toString(),
					KakaoPaymentCancelReturn.class);

			log.debug("response   >" + response);
			if (response.getCode() == null) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, response.getCode(), "", response,
						PaymentConst.PAYMENT_CANCEL_SUCCESS_MESSAGE, response.getMsg());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, response.getCode(), "", response,
						PaymentConst.PAYMENT_CANCEL_FAIL_MESSAGE, response.getMsg());
				return paymentResult;
			}

		} catch (Exception e) {
			log.debug(" PaymentException " + e.getMessage());
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * 
	 * @Desc : 카카오 페이 주문 상세조회
	 * @Method Name : orderDetail
	 * @Date : 2019. 1. 31.
	 * @Author : flychani@3top.co.kr
	 * @param payInfo
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult orderDetail(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " kakao pay orderDetail ::::::::::");

		super.fileLog(payInfo);

		KakaoPaymentOrder kakaoPaymentOrder = (KakaoPaymentOrder) payInfo.getPayInfo();

		Map<String, Object> kakaoMap = new HashMap<String, Object>();

		kakaoMap.put("cid", kakaoPaymentConfig.getCid()); // 가맹점 코드
		kakaoMap.put("tid", kakaoPaymentOrder.getTid());

		try {

			ObjectMapper mapper = new ObjectMapper();
			KakaoPaymentOrderReturn response = mapper.readValue(
					kakaoPaymentAPI.sendHttpsRequest(kakaoPaymentConfig.getOrder(), kakaoMap).toString(),
					KakaoPaymentOrderReturn.class);

			log.debug("response   >" + response);

			if (response.getCode() == null) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, response.getCode(), "", response,
						"주문 조회 성공", response.getMsg());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, response.getCode(), "", response,
						"주문 조회 실패", response.getMsg());
				return paymentResult;
			}

		} catch (Exception e) {
			log.debug(" PaymentException " + e.getMessage());
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * 
	 * @Desc : 카카오 페이 주문 내역 조회
	 * @Method Name : orderList
	 * @Date : 2019. 1. 31.
	 * @Author : flychani@3top.co.kr
	 * @param payInfo
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult orderList(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " kakao pay orderList ::::::::::");

		super.fileLog(payInfo);

		KakaoPaymentOrderList kaoPaymentOrderList = (KakaoPaymentOrderList) payInfo.getPayInfo();

		Map<String, Object> kakaoMap = new HashMap<String, Object>();

		kakaoMap.put("cid", kakaoPaymentConfig.getCid()); // 가맹점 코드
//		kakaoMap.put("tid", kaoPaymentOrderList.getTid()); // 가맹점 코드
		kakaoMap.put("payment_request_date", kaoPaymentOrderList.getPaymentRequestDate()); // yyyyMMdd

		try {

			ObjectMapper mapper = new ObjectMapper();
			KakaoPaymentOrderListReturn response = mapper.readValue(
					kakaoPaymentAPI.sendHttpsRequest(kakaoPaymentConfig.getOrders(), kakaoMap).toString(),
					KakaoPaymentOrderListReturn.class);

			if (response.getCode() == null) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, response.getCode(), "", response,
						"주문내역 조회 성공", response.getMsg());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, response.getCode(), "", response,
						"주문내역 조회 실패", response.getMsg());
				return paymentResult;
			}

		} catch (Exception e) {
			log.debug(" PaymentException " + e.getMessage());
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * 환불
	 */
	@Override
	public PaymentResult refund(PaymentInfo payInfo) throws PaymentException {
		// 구현없음
		return null;
	}

}
