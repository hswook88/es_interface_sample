package kr.co.shop.interfaces.module.payment.base.model;

import lombok.Data;

/**
 * @Desc : 결제모듈 호출용 공통 model
 * @FileName : PaymentInfo.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class PaymentInfo extends PaymentBase {

	private static final long serialVersionUID = 4910010343634090213L;

	/**
	 * 기본 생성자
	 * 
	 * @param payMethodCode
	 * @param payInfo
	 */
	public PaymentInfo(String payMethodCode, Object payInfo) {
		this.payMethodCode = payMethodCode;
		this.payInfo = payInfo;
	}

	private String payMethodCode; // 결제방법
	private Object payInfo; // 전달용 object(각 서비스 호출에 사용할 model)

}
