package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesIOLogger {

	/**
	 * 번호
	 */
	private int seq;

	/**
	 * 인터페이스ID
	 */
	private String ifId;

	/**
	 * 인터페이스명
	 */
	private String ifName;

	/**
	 * 송신 값
	 */
	private String ifInput;

	/**
	 * 수신 값
	 */
	private String ifOutput;

	/**
	 * 오류코드
	 */
	private String ifException;

	/**
	 * 생성일시
	 */
	private String compDate;

	/**
	 * 완료일시
	 */
	private String insDate;

	/**
	 * 성공여부
	 */
	private String successYn;

}
