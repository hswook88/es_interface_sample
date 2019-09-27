package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * CJmall 판매가능수량등록 처리용 모델
 * 
 * @author 백인천
 *
 */
@Data
@XStreamAlias("tns:ltSupplyPlans")
public class CJLtSupplyPlan {

	@XStreamAlias("tns:unitCd")
	private String unitCd; // 단품코드
	@XStreamAlias("tns:chnCls")
	private String chnCls; // 가등록채널구분
	@XStreamAlias("tns:strDt")
	private String strDt; // 판매시작일
	@XStreamAlias("tns:endDt")
	private String endDt; // 판매종료일
	@XStreamAlias("tns:availSupQty")
	private String availSupQty; // 공급가능수량
	@XStreamAlias("tns:stockAlertQty")
	private String stockAlertQty; // 재고부족알림수량

	// IF_STOCK_WMS_CJ 처리용 필드
	@XStreamOmitField
	private String SANGPUMFULLCD; // 백업생성용 - 상품풀코드
	@XStreamOmitField
	private String stdYYYYMMDD; // 백업생성용 - 계산일자(물류기준일자)
	@XStreamOmitField
	private String wmsStockQty; // 백업생성용 - 물류기준 수량
	@XStreamOmitField
	private String junilOrderQty; // 백업생성용 - 전일 주문수량
	@XStreamOmitField
	private String dangilOrderQty; // 백업생성용 - 당일 주문수량
	@XStreamOmitField
	private String junilCancelQty; // 백업생성용 - 전일 주문취소수량
	@XStreamOmitField
	private String dangCancelQty; // 백업생성용 - 당일 주문취소수량

}
