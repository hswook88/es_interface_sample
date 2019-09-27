package kr.co.shop.interfaces.zinterfacesdb.model.master;

import org.apache.commons.math3.stat.descriptive.summary.Product;

import lombok.Data;

@Data
public class InterfacesSangPumListToErp {

	/*B코드*/
	private String cbcd;
	
	/*매장코드*/
	private String maejangCd;
	
	/*상품풀코드*/
	private String sangPumFullCd;
	
	/*상품코드*/
	private String sangpumCd;
	
	/*색상이름*/
	private String colorNm;
	
	/*사이즈코드*/
	private String sizeCd;
	
	/*바코드*/
	private String barCode;
	
	/*벤더바코드*/
	private String barCode1;
	
	/*예비바코드*/
	private String barCode2;
	
	/*상품이름*/
	private String sangPumNm;
	
	/*스타일*/
	private String style;
	
	/*자사스타일*/
	private String akStyle;
	
	/*브랜드 코드*/
	private String brandCd;
	
	/*브랜드 이름*/
	private String brandNm;
	
	/*상품구분*/
	private String sangPumDiv;
	
	/*성별*/
	private String sex;
	
	/*벤더 코드*/
	private String vendCd;
	
	/*원산지*/
	private String wonSanJi;
	
	/*종류*/
	private String kind;
	
	/*소재*/
	private String matt;
	
	/*색상(온라인전용)*/
	private String coloronline;
	
	/*힐높이*/
	private String heelHeight;
	
	/*제조사*/
	private String manuFacturer;
	
	/*제조사코드*/
	private String manuFacturerCd;
	
	/*입력일자*/
	private String enterDate;
	
	/*수입자*/
	private String importer;
	
	/*사용구분*/
	private String useFlag;
	
	/*패킹수량*/
	private String boxInQty;
	
	/*등록일자*/
	private java.sql.Timestamp regDate;
	
	/*WMS수정일자*/
	private java.sql.Timestamp wms;
	
	/*ABC수정일자*/
	private java.sql.Timestamp abcmart;
	
	/*오픈마켓 수정일자*/
	private java.sql.Timestamp openMarket;
	
	/*에러상태*/
	private String errorStatus;
	
	/*상태값*/
	private String workDiv;
	
	/*최초 입고일*/
	private String firstEntryDate;
	
	/*재입고일*/
	private String lastEntryDate;
	
	/*티어 구분*/
	private String tierflag;
}
