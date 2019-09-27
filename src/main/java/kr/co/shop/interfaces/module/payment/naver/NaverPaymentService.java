package kr.co.shop.interfaces.module.payment.naver;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.shop.interfaces.module.payment.base.AbstractPaymentLogService;
import kr.co.shop.interfaces.module.payment.base.IPaymentService;
import kr.co.shop.interfaces.module.payment.base.PaymentConst;
import kr.co.shop.interfaces.module.payment.base.PaymentException;
import kr.co.shop.interfaces.module.payment.base.PaymentResult;
import kr.co.shop.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.shop.interfaces.module.payment.config.NaverPaymentConfig;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentApproval;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentApprovalReturn;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentCancel;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentCancelReturn;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentCardCheckReturn;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentCashAmountReturn;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentConfirm;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentConfirmReturn;
import kr.co.shop.interfaces.module.payment.naver.model.NaverPaymentListReturn;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 네이버페이 Service Class
 * @FileName : NaverPaymentService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
/**
 * @Desc :
 * @FileName : NaverPaymentService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Component
@Slf4j
public class NaverPaymentService extends AbstractPaymentLogService implements IPaymentService {

	private String payMethodType = PaymentConst.PAYMENT_METHOD_TYPE_NAVER;

	@Autowired
	NaverPaymentConfig naverPayConfig;

	@Autowired
	NaverPaymentAPI naverPayApi;

	/**
	 * @Desc : 결제방법 코드
	 * @return {@link PaymentConst#PAYMENT_METHOD_TYPE_NAVER}
	 * @throws PaymentException
	 */
	@Override
	public String getPayMethodType() throws PaymentException {
		return payMethodType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.shop.interfaces.module.payment.base.IPaymentService#authority(kr.co.
	 * abcmart.interfaces.module.payment.base.model.PaymentInfo)
	 */
	@Override
	public PaymentResult authority(PaymentInfo payInfo) throws PaymentException {
		// 구현 없음
		return null;
	}

	/**
	 * @Desc :네이버 페이 결제 승인.
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult approval(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " approval ::::::::::");

		NaverPaymentApproval naverPaymentApproval = (NaverPaymentApproval) payInfo.getPayInfo();
		super.fileLog(payInfo);

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("paymentId", naverPaymentApproval.getPaymentId());
		try {

			ObjectMapper mapper = new ObjectMapper();
			NaverPaymentApprovalReturn approvalReturn = mapper.readValue(
					naverPayApi.sendHttpsRequest(naverPayConfig.getApproval(), parameter).toString(),
					NaverPaymentApprovalReturn.class);
			if (StringUtils.equals(approvalReturn.getCode(), "Success")) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, approvalReturn.getCode(), "",
						approvalReturn, PaymentConst.PAYMENT_APPROVAL_SUCCESS_MESSAGE, approvalReturn.getMessage());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, approvalReturn.getCode(), "",
						approvalReturn, approvalReturn.getMessage(), approvalReturn.getMessage());
				return paymentResult;
			}

		} catch (Exception e) {
			throw new PaymentException(e.getMessage());
		}

	}

	/**
	 * @Desc : 네이버 페이 결제 취소
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult cancel(PaymentInfo payInfo) throws PaymentException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " cancel ::::::::::");

		NaverPaymentCancel naverPaymentCancel = (NaverPaymentCancel) payInfo.getPayInfo();

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("paymentId", naverPaymentCancel.getPaymentId());
		parameter.put("cancelAmount", naverPaymentCancel.getCancelAmount().longValue());
		parameter.put("taxScopeAmount", naverPaymentCancel.getCancelAmount().longValue());
		parameter.put("taxExScopeAmount", 0);
		parameter.put("cancelReason", naverPaymentCancel.getCancelReason());
		parameter.put("cancelRequester", naverPaymentCancel.getCancelRequester());

		try {
			ObjectMapper mapper = new ObjectMapper();
			NaverPaymentCancelReturn cancelReturn = mapper.readValue(
					naverPayApi.sendHttpsRequest(naverPayConfig.getCancel(), parameter).toString(),
					NaverPaymentCancelReturn.class);
			if (StringUtils.equals(cancelReturn.getCode(), "Success")) {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_Y, cancelReturn.getCode(), "",
						cancelReturn, PaymentConst.PAYMENT_APPROVAL_SUCCESS_MESSAGE, cancelReturn.getMessage());
				return paymentResult;
			} else {
				PaymentResult paymentResult = new PaymentResult(PaymentConst.YN_N, cancelReturn.getCode(), "",
						cancelReturn, PaymentConst.PAYMENT_APPROVAL_SUCCESS_MESSAGE, cancelReturn.getMessage());
				return paymentResult;
			}
		} catch (Exception e) {
			throw new PaymentException(e.getMessage());
		}
	}

	/**
	 * @Desc : 네이버 페이 결제 목록 조회, paymenyId가 넘어 왔을 경우 기간별 조회 무시.
	 * @Method Name : list
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param paymentId 네이버페이결제ID
	 * @param startDate 조회시작일
	 * @param endDate   조회종료일
	 * @return {@link NaverPaymentListReturn}
	 * @throws PaymentException
	 */
	public NaverPaymentListReturn list(String paymentId, String startDate, String endDate, int pageNum)
			throws PaymentException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(NaverPaymentConst.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getSimpleName() + " list ::::::::::");
			log.debug("params : [paymentId = " + paymentId + " , startDate = " + startDate + ", endDate = " + endDate
					+ ", pageNumber = " + pageNum + "]");
		}
		Map<String, Object> parameter = new HashMap<String, Object>();

		if (paymentId != null && paymentId != "")
			parameter.put("paymentId", paymentId);
		else {
//			parameter.put("endTime", dateFormat.format(endDate));
//			parameter.put("startTime", dateFormat.format(startDate));
			parameter.put("endTime", endDate);
			parameter.put("startTime", startDate);
			parameter.put("rowsPerPage", "50");
			parameter.put("pageNumber", 1);
		}

		log.debug("params : " + parameter);

		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(
					naverPayApi.sendHttpsRequest(naverPayConfig.getList(), parameter, PaymentConst.CONTENT_TYPE_JSON),
					NaverPaymentListReturn.class);

		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e.getMessage());
		}
	}

	/**
	 * @Desc : 네이버페이 거래 완료
	 * @Method Name : confirm
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param payInfo {@link PaymentInfo}
	 * @return {@link PaymentResult}
	 * @throws Exception
	 */
	public PaymentResult confirm(PaymentInfo payInfo) throws Exception {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getSimpleName() + " confirm ::::::::::");

		Map<String, Object> parameter = new HashMap<String, Object>();

		NaverPaymentConfirm naverPaymentConfirm = (NaverPaymentConfirm) payInfo.getPayInfo();
		parameter.put("paymentId", naverPaymentConfirm.getPaymentId());
		parameter.put("requester", naverPaymentConfirm.getRequester());

		PaymentResult paymentResult = new PaymentResult("Y");

		try {
			ObjectMapper mapper = new ObjectMapper();

			NaverPaymentConfirmReturn confirmReturn = mapper.readValue(
					naverPayApi.sendHttpsRequest(naverPayConfig.getConfirm(), parameter).toString(),
					NaverPaymentConfirmReturn.class);
			paymentResult.setData(confirmReturn);
		} catch (Exception e) {
			throw new PaymentException(e.getMessage());
		}

		return paymentResult;
	}

	/**
	 * @Desc : 네이버 페이 현금영수증 발행대상 금액조회
	 * @Method Name : viewCashAmount
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param paymentId 네이버페이결제ID
	 * @return {@link NaverPaymentCashAmountReturn}
	 * @throws Exception
	 */
	public NaverPaymentCashAmountReturn viewCashAmount(String paymentId) throws Exception {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getSimpleName() + " viewCashAmount ::::::::::");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("paymentId", paymentId);

		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(naverPayApi.sendHttpsRequest(naverPayConfig.getViewCashAmount(), parameter),
					NaverPaymentCashAmountReturn.class);

		} catch (Exception e) {
			throw new PaymentException(e.getMessage());
		}
	}

	/**
	 * @Desc : 네이버 페이 결제 예약 정기 결제 예약 기능으로 보임, 사용여부 확인 필요
	 * @Method Name : reserve
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param payInfo {@link PaymentInfo}
	 * @throws Exception
	 */
	public void reserve(PaymentInfo payInfo) throws Exception {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getSimpleName() + " reserve ::::::::::");

	}

	/**
	 * @Desc : 네이버 페이 신용카드 매출 전표 조회, 사용여부 확인 필요
	 * @Method Name : viewCardCheck
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param paymentId 네이버페이결제ID
	 * @return ({@link NaverPaymentCardCheckReturn}
	 * @throws Exception
	 */
	public NaverPaymentCardCheckReturn viewCardCheck(String paymentId) throws Exception {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getSimpleName() + " viewCardCheck ::::::::::");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("paymentId", paymentId);

		try {
			ObjectMapper mapper = new ObjectMapper();

			return mapper.readValue(naverPayApi.sendHttpsRequest(naverPayConfig.getViewCardCheck(), parameter),
					NaverPaymentCardCheckReturn.class);
		} catch (Exception e) {
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
