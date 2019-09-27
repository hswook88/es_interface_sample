package kr.co.shop.interfaces.module.payment.base;

import kr.co.shop.interfaces.module.payment.base.model.PaymentInfo;

/**
 * @Desc : 결제모듈 인터페이스
 * @FileName : IPaymentService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
public interface IPaymentService {

	/**
	 * @Desc : 결제방법
	 * @Method Name : getPayMethodType
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @return
	 * @throws PaymentException
	 */
	public String getPayMethodType() throws PaymentException;

	/**
	 * @Desc : 인증
	 * @Method Name : authority
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult authority(PaymentInfo payInfo) throws PaymentException;

	/**
	 * @Desc : 결제승인
	 * @Method Name : approval
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult approval(PaymentInfo payInfo) throws PaymentException;

	/**
	 * @Desc : 결제취소
	 * @Method Name : cancel
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param payInfo {@link PaymentInfo}
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult cancel(PaymentInfo payInfo) throws PaymentException;

	/**
	 * @Desc : 환불
	 * @Method Name : refund
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param payInfo
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult refund(PaymentInfo payInfo) throws PaymentException;

}
