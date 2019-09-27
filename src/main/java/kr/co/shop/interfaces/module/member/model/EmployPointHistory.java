package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class EmployPointHistory {

	/**
	 * 사용금액
	 */
	private String usePoint;

	/**
	 * 사용구분
	 */
	private String useGubun;

	/**
	 * 사용일자
	 */
	private String insDate;

	/**
	 * 총한도금액
	 */
	private String totalPoint;

	/**
	 * 잔액
	 */
	private String balancePoint;

}
