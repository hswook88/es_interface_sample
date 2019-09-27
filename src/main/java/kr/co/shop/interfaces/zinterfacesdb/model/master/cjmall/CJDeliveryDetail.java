package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 미배송정보등록 처리용 모델
 * 
 * @author 백인천
 *
 */
@Data
@XStreamAlias("tns:beNotYet")
public class CJDeliveryDetail {

	@XStreamAlias("tns:ordNo")
	private String ordNo; // 주문정보 - 주문번호

	@XStreamAlias("tns:ordGSeq")
	private String ordGSeq; // 주문정보 - 주문상품순번

	@XStreamAlias("tns:ordDSeq")
	private String ordDSeq; // 주문정보 - 주문상세순번

	@XStreamAlias("tns:ordWSeq")
	private String ordWSeq; // 주문정보 - 주문처리순번

	@XStreamAlias("tns:noDelyGb")
	private String noDelyGb; // 미출고사유

	@XStreamAlias("tns:noDelyDesc")
	private String noDelyDesc; // 미출고사유상세

	@XStreamAlias("tns:hopeDelyDate")
	private String hopeDelyDate; // 창고출고예정일

	@XStreamAlias("tns:wbIdNo")
	private String wbIdNo; // 운송장식별번호

	@XStreamAlias("tns:delicompCd")
	private String delicompCd; // 택배사

	@XStreamAlias("tns:wbNo")
	private String wbNo; // 운송장번호

}
