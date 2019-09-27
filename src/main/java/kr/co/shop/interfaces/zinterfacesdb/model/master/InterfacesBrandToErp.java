package kr.co.shop.interfaces.zinterfacesdb.model.master;


import lombok.Data;

/**
 * @Desc : ErpBrand 수신
 * @FileName : ErpBrand
 * @Project : shop.interface
 * @Date : 2019. 02. 12
 * @Author : 김영진
 */
@Data
public class InterfacesBrandToErp  {

	/**브랜드 코드*/
	private String brandCd;
	/**브랜드 이름*/
	private String brandNm;
	/** 사용여부 N값 고정*/
	private String useYn;
	/** 상태값*/
	private String workDiv;
	
}
