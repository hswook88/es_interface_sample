package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import lombok.Data;

/**
 * CJ몰 판매가능수량등록용 모델
 * 
 * @author 백인천
 *
 */
@Data
public class CJLtSupplyPlans {

	// LT공급계획
	private CJLtSupplyPlan ltSupplyPlan;

	// 처리결과
	private CJResult ifResult;

}
