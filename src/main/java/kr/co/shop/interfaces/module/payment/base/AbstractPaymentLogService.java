package kr.co.shop.interfaces.module.payment.base;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 로그 생성
 * @FileName : AbstractPaymentLogService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Component
@Slf4j
public abstract class AbstractPaymentLogService {

	/**
	 * @Desc : DB 로그
	 * @Method Name : dbLog
	 * @Date : 2019. 1. 31.
	 * @Author :KTH
	 * @param object {@link Object}
	 * @throws PaymentException
	 */
	public void dbLog(Object object) throws PaymentException {
		log.debug("------------insert db");
	}

	/**
	 * @Desc :파일 로그
	 * @Method Name : fileLog
	 * @Date : 2019. 1. 31.
	 * @Author : KTH
	 * @param object {@link Object}
	 * @throws PaymentException
	 */
	public void fileLog(Object object) throws PaymentException {
		log.debug("------------write file");
	}

}
