package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesSabangnetOrderList {

	/** 겁색 시작일자(필수) */
	private String ordStDate;

	/** 검색 마지막일자(필수) */
	private String ordEdDate;

	/** 출력필드 리스트(필수) */
	private String ordField;

	/** 정산대조확인여부 */
	private String jungChkYn2;

	/** 주문번호(쇼핑몰) */
	private String orderId;

	/** 쇼핑몰CODE */
	private String mallId;

	/** 주문상태 */
	private String orderStatus;

	/** encoding타입 선택 */
	private String Lang;

	/** 매입처ID */
	private String PartnerId;

	/** 쇼핑몰ID */
	private String mallUserId;

	/** 물류처ID */
	private String dpartnerId;

}
