package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 미배송정보 등록 처리
 * 
 * @author Huchu
 *
 */
@Data
@XStreamAlias("tns:beNotYets")
public class CJDelivery {

	// 미배송정보
	private CJDeliveryDetail beNotYet;

	// 처리결과
	private CJResult ifResult;

}
