package kr.co.shop.interfaces.module.payment;

import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.module.payment.base.IPaymentService;
import kr.co.shop.interfaces.module.payment.base.PaymentException;
import kr.co.shop.interfaces.module.payment.base.PaymentResult;
import kr.co.shop.interfaces.module.payment.base.PaymentServiceFactory;
import kr.co.shop.interfaces.module.payment.base.model.PaymentInfo;

/**
 * @Desc : 결제모듈 서비스 호출
 * @FileName : PaymentEntrance.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Service
public class PaymentEntrance {

	/**
	 * @Desc : 인증
	 * @Method Name : authority
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult authority(PaymentInfo payInfo) throws PaymentException {
		IPaymentService service = (IPaymentService) PaymentServiceFactory.getService(payInfo.getPayMethodCode());

		return service.authority(payInfo);
	}

	/**
	 * @Desc : 결제승인
	 * @Method Name : approval
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult approval(PaymentInfo payInfo) throws PaymentException {
		IPaymentService service = (IPaymentService) PaymentServiceFactory.getService(payInfo.getPayMethodCode());

		return service.approval(payInfo);
	}

	/**
	 * @Desc : 결제취소
	 * @Method Name : cancel
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult cancel(PaymentInfo payInfo) throws PaymentException {
		IPaymentService service = (IPaymentService) PaymentServiceFactory.getService(payInfo.getPayMethodCode());

		return service.cancel(payInfo);
	}

	/**
	 * @Desc : 환불
	 * @Method Name : refund
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param payInfo
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult refund(PaymentInfo payInfo) throws PaymentException {
		IPaymentService service = (IPaymentService) PaymentServiceFactory.getService(payInfo.getPayMethodCode());

		return service.refund(payInfo);
	}

}
