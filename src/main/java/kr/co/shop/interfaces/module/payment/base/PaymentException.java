package kr.co.shop.interfaces.module.payment.base;

/**
 * @Desc : 결제모듈 Exception
 * @FileName : PaymentException.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
public class PaymentException extends Exception {

	private static final long serialVersionUID = 7992972995129208940L;

	public PaymentException(String msg) {
		super(msg);
	}

}
