package kr.co.shop.interfaces.module.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @Desc : KCP 결제 config
 * @FileName : KcpPaymentConfig.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Configuration
@Data
public class KcpPaymentConfig {
	/* siteName */
	@Value("${pg.kcp.siteName}")
	private String siteName;

	/* siteCode 일반 */
	@Value("${pg.kcp.siteCode}")
	private String siteCode;

	/* siteKey 일반 */
	@Value("${pg.kcp.siteKey}")
	private String siteKey;

	/* siteCode reserve */
	@Value("${pg.kcp.reserve.siteCode}")
	private String siteCodeReserve;

	/* siteKey reserve */
	@Value("${pg.kcp.reserve.siteKey}")
	private String siteKeyReserve;

	/* siteCode gift */
	@Value("${pg.kcp.gift.siteCode}")
	private String siteCodeGift;

	/* siteKey gift */
	@Value("${pg.kcp.gift.siteKey}")
	private String siteKeyGift;

	/* siteCode claim */
	@Value("${pg.kcp.claim.siteCode}")
	private String siteCodeClaim;

	/* siteKey claim */
	@Value("${pg.kcp.claim.siteKey}")
	private String siteKeyClaim;

	/* kcp 접속 url */
	@Value("${pg.kcp.gw.url}")
	private String gwUrl;

	/* kcp log 경로 */
	@Value("${pg.kcp.logPath}")
	private String logPath;

	/* kcp log 레벨 */
	@Value("${pg.kcp.logLevel}")
	private String logLevel;

	/* kcp 접속 port */
	@Value("${pg.kcp.port}")
	private String port;

	/* kcp txMode */
	@Value("${pg.kcp.txMode}")
	private String txMode;

	/* kcp 허용ip 1 */
	@Value("${pg.kcp.allow.ip1}")
	private String ip1;

	/* kcp 허용ip 2 */
	@Value("${pg.kcp.allow.ip2}")
	private String ip2;

	/* kcp 허용ip 3 */
	@Value("${pg.kcp.allow.ip3}")
	private String ip3;
}