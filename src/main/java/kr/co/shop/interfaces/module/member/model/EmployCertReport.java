package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class EmployCertReport {

	/**
	 * 성공여부
	 */
	private String successFlag;

	/**
	 * 사번
	 */
	private String employeesNumber;

	/**
	 * 이름
	 */
	private String employeesName;

	/**
	 * 부서
	 */
	private String employeesDpmt;

	/**
	 * 직책
	 */
	private String employeesPosition;

	/**
	 * 입사일
	 */
	private String employeesJoinDt;

	/**
	 * 퇴사일
	 */
	private String employeesResignDt;

}
