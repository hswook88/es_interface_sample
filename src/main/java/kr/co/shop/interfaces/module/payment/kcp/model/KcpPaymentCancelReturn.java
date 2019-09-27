package kr.co.shop.interfaces.module.payment.kcp.model;

import com.kcp.J_PP_CLI_N;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 취소연동 수신 데이터
 * @FileName : KcpPaymentCancelReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentCancelReturn extends PaymentBase {

	private static final long serialVersionUID = 3069886465708970142L;

	private String resCd; // 결과코드
	private String resMsg; // 결과메시지
	private String tno; // KCP거래번호
	private String amount; // 원거래 결제 금액
	private String cardCd; // 결제 건의 발급 사 코드
	private String cardName; // 결제 건의 발급 사 명
	private String couponMny; // 결제 건의 쿠폰 할인, 페이코 포인트 사용 금액
	private String cancTime; // 결제 건의 취소 요청 시간

	/*****************
	 * 부분취소
	 *****************/
	private String pancModMny; // 부분취소된 금액
	private String pancRemMny; // 부분취소 후 남아있는 금액(해당 금액을 다음 부분취소시 rem_mny 값에 적용)
	private String pancCardModMny; // 카드 부분 취소 요청 금액
	private String pancCouponModMny; // 쿠폰 부분 취소 요청 금액
	private String modPcanSeqNo; // 부분취소 일련번호(부분취소시에만 리턴)

	/**
	 * 승인 결과를 객체에 넣는 메소드
	 * 
	 * @param c_PayPlus
	 * @return
	 */
	public static KcpPaymentCancelReturn of(J_PP_CLI_N c_PayPlus) {
		KcpPaymentCancelReturn reply = new KcpPaymentCancelReturn();

		reply.setResCd(c_PayPlus.m_res_cd);
		reply.setResMsg(c_PayPlus.m_res_msg);
		reply.setTno(c_PayPlus.mf_get_res("tno"));
		reply.setAmount(c_PayPlus.mf_get_res("amount"));
		reply.setCardCd(c_PayPlus.mf_get_res("card_cd"));
		reply.setCardName(c_PayPlus.mf_get_res("card_name"));
		reply.setCouponMny(c_PayPlus.mf_get_res("coupon_mny"));
		reply.setCancTime(c_PayPlus.mf_get_res("canc_time"));
		reply.setPancModMny(c_PayPlus.mf_get_res("panc_mod_mny"));
		reply.setPancRemMny(c_PayPlus.mf_get_res("panc_rem_mny"));
		reply.setPancCardModMny(c_PayPlus.mf_get_res("panc_card_mod_mny"));
		reply.setPancCouponModMny(c_PayPlus.mf_get_res("panc_coupon_mod_mny"));
		reply.setModPcanSeqNo(c_PayPlus.mf_get_res("mod_pcan_seq_no"));

		return reply;
	}
}
