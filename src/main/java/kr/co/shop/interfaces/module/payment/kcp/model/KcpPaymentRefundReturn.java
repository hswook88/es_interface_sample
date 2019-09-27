package kr.co.shop.interfaces.module.payment.kcp.model;

import com.kcp.J_PP_CLI_N;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 환불연동 수신 데이터
 * @FileName : KcpPaymentRefundReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentRefundReturn extends PaymentBase {

	private static final long serialVersionUID = -1961206269747355048L;

	private String resCd; // 결과코드
	private String resMsg; // 결과메시지
	private String tno; // KCP거래번호

	/**
	 * 승인 결과를 객체에 넣는 메소드
	 * 
	 * @param c_PayPlus
	 * @return
	 */
	public static KcpPaymentRefundReturn of(J_PP_CLI_N c_PayPlus) {
		KcpPaymentRefundReturn reply = new KcpPaymentRefundReturn();

		reply.setResCd(c_PayPlus.m_res_cd);
		reply.setResMsg(c_PayPlus.m_res_msg);
		reply.setTno(c_PayPlus.mf_get_res("tno"));

		return reply;
	}
}