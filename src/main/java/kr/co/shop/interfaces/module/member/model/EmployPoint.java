package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class EmployPoint {

	/**
	 * 성공여부
	 */
	private String successFlag;

	/**
	 * 총 한도금액
	 */
	private String totalPoint;

	/**
	 * 잔액
	 */
	private String balancePoint;
}
