package kr.co.shop.interfaces.module.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @Desc : Kakao 결제 config
 * @FileName : KakaoPaymentConfig.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Configuration
@Data
public class KakaoPaymentConfig {

	@Value("${kakao.api.url}")
	private String url;

	@Value("${kakao.api.cid}")
	private String cid;

	@Value("${kakao.api.adminkey}")
	private String adminKey;

	@Value("${kakao.api.url.ready}")
	private String ready;

	@Value("${kakao.api.url.approve}")
	private String approve;

	@Value("${kakao.api.url.cancel}")
	private String cancel;

	@Value("${kakao.api.url.order}")
	private String order;

	@Value("${kakao.api.url.orders}")
	private String orders;
}
