package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class AfterServiceHistory {

	/**
	 * 접수번호
	 */
	private String csNo;

	/**
	 * 접수일자
	 */
	private String regDate;

	/**
	 * 매장명
	 */
	private String storeNm;

	/**
	 * 상품정보
	 */
	private String productInfo;

	/**
	 * AS구분 (심의,수선)
	 */
	private String reqType;

	/**
	 * AS상태
	 */
	private String reqStatus;

}