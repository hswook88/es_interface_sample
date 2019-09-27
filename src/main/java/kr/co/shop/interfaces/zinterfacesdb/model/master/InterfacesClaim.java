package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesClaim {

	/** 회원ID */
	private String memberId;

	/** 점포코드 */
	private String storeCd;

	/** 접수일자 */
	private String reqDt;

	/** 접수번호 */
	private String reqNo;

	/** 상품코드 */
	private String productCd;

	/** 사이즈 */
	private String sizeCd;

	/** 의뢰형태 */
	private String reqType;

	/** 유상구분 */
	private boolean payokYn;

	/** 진행상태 */
	private String reqStatus;

	/** 요청내용 */
	private String reqRemark;

	/** 등록일시 */
	private java.sql.Timestamp regDate;

	/** 완료일시? */
	private java.sql.Timestamp conDate;

	/** 에러상태 */
	private String errorStatus;

	/** 작업상태 */
	private String workDiv;

}
