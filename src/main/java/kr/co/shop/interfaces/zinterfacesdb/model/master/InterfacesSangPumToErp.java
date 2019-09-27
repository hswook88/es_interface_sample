package kr.co.shop.interfaces.zinterfacesdb.model.master;

import java.util.List;

import lombok.Data;

@Data
public class InterfacesSangPumToErp {
	
	
	/**b코드*/
	private String cbcd;
	/**매장코드*/
	private String maejangCd;
	/**상품코드*/
	private String sangPumCd;
	/**상품명*/
	private String sangPumNm;
	/**브랜드코드*/
	private String brandCd;
	/**브랜드명*/
	private String brandNm;
	/**등록시간*/
	private java.sql.Timestamp regDate;
	/**상품구분*/
	private String sangPumDiv;	
	/**시작시간 검색 조건*/
	private String regStartDate;
	/**종료시간 검색 조건*/
	private String regEndDate;
	/**다건검색용 상품 리스트*/
	private List<String> sangPumCdList;
	
	
	
	
	
	
	
	
	
		
}
	

	

