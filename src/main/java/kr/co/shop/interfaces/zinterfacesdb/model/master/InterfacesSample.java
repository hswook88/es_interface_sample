package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesSample {

	/**
	 * 설명 : 이름
	 */
	private String name;

	/**
	 * 설명 : 등록시간
	 */
	private java.sql.Timestamp regDate;

	/**
	 * 설명 : 제목
	 */
	private String title;

	/**
	 * 설명 :
	 */
	private int id;

	/**
	 * 설명 :
	 */
	private String codeNo;

	/**
	 * 설명 :
	 */
	private String useYn;

}
