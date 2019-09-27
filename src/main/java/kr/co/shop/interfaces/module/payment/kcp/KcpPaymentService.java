package kr.co.shop.interfaces.module.payment.kcp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kcp.J_PP_CLI_N;

import kr.co.shop.interfaces.module.payment.base.AbstractPaymentLogService;
import kr.co.shop.interfaces.module.payment.base.IPaymentService;
import kr.co.shop.interfaces.module.payment.base.PaymentConst;
import kr.co.shop.interfaces.module.payment.base.PaymentException;
import kr.co.shop.interfaces.module.payment.base.PaymentResult;
import kr.co.shop.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.shop.interfaces.module.payment.config.KcpPaymentConfig;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentApproval;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentApprovalReturn;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentCancel;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentCancelReturn;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentRefund;
import kr.co.shop.interfaces.module.payment.kcp.model.KcpPaymentRefundReturn;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : KCP 결제모듈 구현체
 * @FileName : KcpPaymentService.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Component
@Slf4j
public class KcpPaymentService extends AbstractPaymentLogService implements IPaymentService {

	@Autowired
	KcpPaymentConfig kcpPaymentConfig; // KCP 결제 config 설정 값

	/**
	 * 결제방법 - KCP고정값
	 */
	@Override
	public String getPayMethodType() throws PaymentException {
		return PaymentConst.PAYMENT_METHOD_TYPE_KCP;
	}

	/**
	 * 인증
	 */
	@Override
	public PaymentResult authority(PaymentInfo payInfo) throws PaymentException {
		// 구현 없음
		return null;
	}

	/**
	 * 결제승인
	 */
	@Override
	public PaymentResult approval(PaymentInfo payInfo) throws PaymentException {
		KcpPaymentApproval kcpPaymentApproval = (KcpPaymentApproval) payInfo.getPayInfo();

		// KcpPaymentApproval kcpPaymentApproval = new KcpPaymentApproval();
		// BeanUtils.copyProperties(payInfo.getPayInfo(), kcpPaymentApproval); // 파라미터복사

		// objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		// KcpPaymentApproval kcpPaymentApproval =
		// objectMapper.convertValue(payInfo.getPayInfo(), KcpPaymentApproval.class);

		try {
			/*
			 * =============================================================================
			 * 02. 지불 요청 정보 설정
			 * --------------------------------------------------------------------------
			 */
			String req_tx = kcpPaymentApproval.getReqTx(); // 요청 종류
			String tran_cd = kcpPaymentApproval.getTranCd(); // 처리 종류
			/*
			 * =============================================================================
			 * 02. 지불 요청 정보 설정 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 03. 인스턴스 생성 및 초기화(변경 불가)
			 * --------------------------------------------------------------------------
			 */
			J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

			c_PayPlus.mf_init("", kcpPaymentConfig.getGwUrl(), kcpPaymentConfig.getPort(),
					Integer.parseInt(kcpPaymentConfig.getTxMode()), kcpPaymentConfig.getLogPath());
			c_PayPlus.mf_init_set();
			/*
			 * =============================================================================
			 * 03. 인스턴스 생성 및 초기화 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 04. 처리 요청 정보 설정
			 * --------------------------------------------------------------------------
			 *
			 * --------------------------------------------------------------------------
			 * 04-1. 승인 요청 정보 설정
			 * --------------------------------------------------------------------------
			 */
			if (req_tx.equals("pay")) {
				c_PayPlus.mf_set_enc_data(kcpPaymentApproval.getEncData(), kcpPaymentApproval.getEncInfo());

				/* 결제금액 유효성 검증 */
				int ordr_data_set_no;
				ordr_data_set_no = c_PayPlus.mf_add_set("ordr_data");
				c_PayPlus.mf_set_us(ordr_data_set_no, "ordr_mony", Long.toString(kcpPaymentApproval.getGoodMny()));
			}
			/*
			 * --------------------------------------------------------------------------
			 * 04. 처리 요청 정보 설정 END
			 * ==========================================================================
			 */

			/*
			 * =============================================================================
			 * 05. 실행
			 * -----------------------------------------------------------------------------
			 */
			if (tran_cd.length() > 0) {
				// 마지막 인자 값 : 한글 캐릭터셋에 대한 옵션이며 기본적으로 KCP는 EUC-KR 이라 "0"이며 KCP로 유입되는 한글 전문이 깨질 경우
				// "1" 로(UTF-8) 변경 가능
				c_PayPlus.mf_do_tx(kcpPaymentApproval.getSiteCd(), kcpPaymentApproval.getSiteKey(), tran_cd,
						"127.0.0.1", kcpPaymentApproval.getOrdrIdxx(), kcpPaymentConfig.getLogLevel(), "1");
			} else {
				c_PayPlus.m_res_cd = "9562";
				c_PayPlus.m_res_msg = "연동 오류";
			}
			/*
			 * --------------------------------------------------------------------------
			 * 05. 실행 END
			 * =============================================================================
			 */

			// 승인 결과 값 model 리턴
			KcpPaymentApprovalReturn kcpPaymentApprovalReturn = KcpPaymentApprovalReturn.of(c_PayPlus);

			super.dbLog(kcpPaymentApprovalReturn);

			if (StringUtils.equals(kcpPaymentApprovalReturn.getResCd(), "0000")) {
				return new PaymentResult(PaymentConst.YN_Y, kcpPaymentApprovalReturn.getResCd(), "",
						kcpPaymentApprovalReturn, PaymentConst.PAYMENT_APPROVAL_SUCCESS_MESSAGE, "");
			} else {
				return new PaymentResult(PaymentConst.YN_N, kcpPaymentApprovalReturn.getResCd(), "",
						kcpPaymentApprovalReturn, kcpPaymentApprovalReturn.getResMsg(), "");
			}

			/*
			 * =============================================================================
			 * 06. 승인 결과 값 추출 - 각 참조 값만 확인할 것(KcpPaymentApprovalReturn에 담겨 옴)
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res("tno"); // KCP 거래 고유 번호
			// c_PayPlus.mf_get_res("amount"); // KCP 실제 거래 금액
			// c_PayPlus.mf_get_res("pnt_issue"); // 결제 포인트사 코드
			// c_PayPlus.mf_get_res("coupon_mny"); // 쿠폰금액
			/*
			 * --------------------------------------------------------------------------
			 * 06-1. 신용카드 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "card_cd" ); // 카드사 코드
			// c_PayPlus.mf_get_res("card_name" ); // 카드사 명
			// c_PayPlus.mf_get_res( "app_time" ); // 승인시간
			// c_PayPlus.mf_get_res( "app_no" ); // 승인번호
			// c_PayPlus.mf_get_res( "noinf" ); // 무이자 여부
			// c_PayPlus.mf_get_res( "quota" ); // 할부 개월 수
			// c_PayPlus.mf_get_res("partcanc_yn" ); // 부분취소 가능유무
			// c_PayPlus.mf_get_res( "card_bin_type_01" ); // 카드구분1
			// c_PayPlus.mf_get_res( "card_bin_type_02" ); // 카드구분2
			// c_PayPlus.mf_get_res( "card_mny" ); // 카드결제금액
			/*
			 * --------------------------------------------------------------
			 * 06-1.1.복합결제(포인트+신용카드) 승인 결과 처리
			 * --------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res("pnt_amount"); // 적립금액 or 사용금액
			// c_PayPlus.mf_get_res("pnt_app_time"); // 승인시간
			// c_PayPlus.mf_get_res("pnt_app_no"); // 승인번호
			// c_PayPlus.mf_get_res("add_pnt"); // 발생 포인트
			// c_PayPlus.mf_get_res("use_pnt"); // 사용가능 포인트
			// c_PayPlus.mf_get_res("rsv_pnt"); // 총 누적 포인트
			// total_amount = amount + pnt_amount; // 복합결제시 총 거래금액
			/*
			 * --------------------------------------------------------------------------
			 * 06-2. 계좌이체 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "app_time" ); // 승인시간
			// c_PayPlus.mf_get_res( "bank_name"); // 은행명
			// c_PayPlus.mf_get_res( "bank_code" ); // 은행코드
			// c_PayPlus.mf_get_res( "bk_mny" ); // 계좌이체결제금액
			/*
			 * --------------------------------------------------------------------------
			 * 06-3. 가상계좌 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "bankname" ); // 입금할 은행 이름
			// c_PayPlus.mf_get_res( "depositor" ); // 입금할 계좌 예금주
			// c_PayPlus.mf_get_res( "account" ); // 입금할 계좌 번호
			// c_PayPlus.mf_get_res( "va_date" ); // 가상계좌 입금마감시간
			/*
			 * --------------------------------------------------------------------------
			 * 06-4. 포인트 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "pnt_amount" ); // 적립금액 or 사용금액
			// c_PayPlus.mf_get_res( "pnt_app_time" ); // 승인시간
			// c_PayPlus.mf_get_res( "pnt_app_no" ); // 승인번호
			// c_PayPlus.mf_get_res( "add_pnt" ); // 발생 포인트
			// c_PayPlus.mf_get_res( "use_pnt"); // 사용가능 포인트
			// c_PayPlus.mf_get_res( "rsv_pnt" ); // 총 누적 포인트
			/*
			 * --------------------------------------------------------------------------
			 * 06-5. 휴대폰 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "hp_app_time" ); // 승인 시간
			// c_PayPlus.mf_get_res( "commid" ); // 통신사 코드
			// c_PayPlus.mf_get_res( "mobile_no" ); // 휴대폰 번호
			/*
			 * --------------------------------------------------------------------------
			 * 06-6. 상품권 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "tk_app_time" ); // 승인 시간
			// c_PayPlus.mf_get_res( "tk_van_code" ); // 발급사 코드
			// c_PayPlus.mf_get_res( "tk_app_no" ); // 승인 번호
			/*
			 * --------------------------------------------------------------------------
			 * 06-7. 현금영수증 승인 결과 처리
			 * --------------------------------------------------------------------------
			 */
			// c_PayPlus.mf_get_res( "cash_authno" ); // 현금영수증 승인번호
			// c_PayPlus.mf_get_res( "cash_no" ); // 현금영수증 거래번호
			/*
			 * --------------------------------------------------------------------------
			 * 06. 승인 결과 처리 END
			 * =============================================================================
			 */

		} catch (Exception e) {
			log.error(e.toString());
			return new PaymentResult(PaymentConst.YN_N, "", "", null, PaymentConst.PAYMENT_APPROVAL_FAIL_MESSAGE,
					e.toString());
		}

	}

	/**
	 * 결제취소
	 */
	@Override
	public PaymentResult cancel(PaymentInfo payInfo) throws PaymentException {
		KcpPaymentCancel kcpPaymentCancel = (KcpPaymentCancel) payInfo.getPayInfo();

		try {
			/*
			 * =============================================================================
			 * 02. 지불 요청 정보 설정
			 * --------------------------------------------------------------------------
			 */
			String req_tx = kcpPaymentCancel.getReqTx(); // 요청 종류
			String cust_ip = kcpPaymentCancel.getCustIp(); // 요청ip
			String mod_type = kcpPaymentCancel.getModType(); // 프로세스 요청 구분 (전체취소= STSC, 부분취소= STPC)
			String tno = kcpPaymentCancel.getTno(); // 결제승인번호
			String mod_desc = kcpPaymentCancel.getModDesc(); // 취소사유
			String tran_cd = ""; // 업무코드
			/*
			 * =============================================================================
			 * 02. 지불 요청 정보 설정 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 03. 인스턴스 생성 및 초기화(변경 불가)
			 * --------------------------------------------------------------------------
			 */
			J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

			c_PayPlus.mf_init("", kcpPaymentConfig.getGwUrl(), kcpPaymentConfig.getPort(),
					Integer.parseInt(kcpPaymentConfig.getTxMode()), kcpPaymentConfig.getLogPath());
			c_PayPlus.mf_init_set();
			/*
			 * =============================================================================
			 * 03. 인스턴스 생성 및 초기화 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 04. 처리 요청 정보 설정
			 * --------------------------------------------------------------------------
			 *
			 * --------------------------------------------------------------------------
			 * 04-1. 04-1. 취소/매입 요청
			 * --------------------------------------------------------------------------
			 */
			if (StringUtils.isEmpty(req_tx)) {
				int mod_data_set_no;

				tran_cd = "00200000";
				mod_data_set_no = c_PayPlus.mf_add_set("mod_data");

				c_PayPlus.mf_set_us(mod_data_set_no, "tno", tno); // KCP 원거래 거래번호
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", mod_type); // 전체취소 STSC / 부분취소 STPC
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip", cust_ip); // 변경 요청자 IP
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", mod_desc);

				if (mod_type.equals("STPC")) { // 부분취소의 경우
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny()); // 취소요청금액
					c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", kcpPaymentCancel.getRemMny()); // 취소가능잔액

					// 복합거래 부분 취소시 주석을 풀어 주시기 바랍니다.
					// c_PayPlus.mf_set_us( mod_data_set_no, "tax_flag", "TG03" ); // 복합과세 구분
					// c_PayPlus.mf_set_us( mod_data_set_no, "mod_tax_mny", mod_tax_mny ); // 공급가 부분
					// 취소 요청 금액
					// c_PayPlus.mf_set_us( mod_data_set_no, "mod_vat_mny", mod_vat_mny ); // 부과세 부분
					// 취소 요청 금액
					// c_PayPlus.mf_set_us( mod_data_set_no, "mod_free_mny", mod_free_mny ); // 비과세
					// 부분 취소 요청 금액
				}
			} else if (StringUtils.equals(req_tx, "mod_escrow")) {
				String mod_desc_cd = "CA06"; // 취소 사유에 대한 상세코드 – 변경불가 CA06
				int mod_data_set_no = c_PayPlus.mf_add_set("mod_data");

				c_PayPlus.mf_set_us(mod_data_set_no, "tno", tno); // KCP 원거래 거래번호
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip", cust_ip); // 변경 요청자 IP
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", mod_desc);// 변경 사유

				if (StringUtils.equals(mod_type, "STE9_C") || StringUtils.equals(mod_type, "STE9_CP")
						|| StringUtils.equals(mod_type, "STE9_A") || StringUtils.equals(mod_type, "STE9_AP")
						|| StringUtils.equals(mod_type, "STE9_AR") || StringUtils.equals(mod_type, "STE9_V")
						|| StringUtils.equals(mod_type, "STE9_VP")) {

					tran_cd = "70200200";
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", "STE9");
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc_cd", mod_desc_cd);
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", mod_desc);

					if (StringUtils.equals(mod_type, "STE9_C")) { // 신용카드 구매 확인 후 취소
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STSC");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC03");
					} else if (StringUtils.equals(mod_type, "STE9_CP")) { // 신용카드 구매 확인 후 부분취소
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STPC");
						c_PayPlus.mf_set_us(mod_data_set_no, "part_canc_yn", "Y");
						c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", kcpPaymentCancel.getRemMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "amount", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC03");
					} else if (StringUtils.equals(mod_type, "STE9_A")) { // 계좌이체 구매 확인 후 취소
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STSC");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC03");
					} else if (StringUtils.equals(mod_type, "STE9_AP")) { // 계좌이체 구매 확인 후 부분취소
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STPC");
						c_PayPlus.mf_set_us(mod_data_set_no, "part_canc_yn", "Y");
						c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", kcpPaymentCancel.getRemMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "amount", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC04");
					} else if (StringUtils.equals(mod_type, "STE9_AR")) { // 계좌이체 구매 확인 후 환불
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STHD");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC04");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_bankcode", kcpPaymentCancel.getModBankCode());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_account", kcpPaymentCancel.getModAccount());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_depositor", kcpPaymentCancel.getModDepositor());
					} else if (StringUtils.equals(mod_type, "STE9_V")) { // 가상계좌 구매 확인 후 환불
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STHD");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC00");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_bankcode", kcpPaymentCancel.getModBankCode());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_account", kcpPaymentCancel.getModAccount());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_depositor", kcpPaymentCancel.getModDepositor());
					} else if (StringUtils.equals(mod_type, "STE9_VP")) { // 가상계좌 구매 확인 후 부분환불
						c_PayPlus.mf_set_us(mod_data_set_no, "sub_mod_type", "STPD");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", kcpPaymentCancel.getModMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", kcpPaymentCancel.getRemMny());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC04");
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_bankcode", kcpPaymentCancel.getModBankCode());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_account", kcpPaymentCancel.getModAccount());
						c_PayPlus.mf_set_us(mod_data_set_no, "mod_depositor", kcpPaymentCancel.getModDepositor());
						c_PayPlus.mf_set_us(mod_data_set_no, "part_canc_yn", "Y");
					}
				} else {
					tran_cd = "00200000";
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", kcpPaymentCancel.getModType()); // 원거래 변경 요청 종류

					// 상태변경 타입이 [즉시취소] 또는 [취소]인 계좌이체, 가상계좌의 경우
					if (StringUtils.equals(mod_type, "STE2") || StringUtils.equals(mod_type, "STE4")) {

						c_PayPlus.mf_set_us(mod_data_set_no, "bank_code", kcpPaymentCancel.getModBankCode()); // 환불수취은행코드
						c_PayPlus.mf_set_us(mod_data_set_no, "refund_account", kcpPaymentCancel.getModAccount());// 환불수취계좌번호
						c_PayPlus.mf_set_us(mod_data_set_no, "refund_nm", kcpPaymentCancel.getModDepositor()); // 환불수취계좌주명
					}
				}
			}
			/*
			 * --------------------------------------------------------------------------
			 * 04. 처리 요청 정보 설정 END
			 * ==========================================================================
			 */

			/*
			 * =============================================================================
			 * 05. 실행
			 * -----------------------------------------------------------------------------
			 */
			if (tran_cd.length() > 0) {
				// 마지막 인자 값 : 한글 캐릭터셋에 대한 옵션이며 기본적으로 KCP는 EUC-KR 이라 "0"이며 KCP로 유입되는 한글 전문이 깨질 경우
				// "1" 로(UTF-8) 변경 가능
				c_PayPlus.mf_do_tx(kcpPaymentCancel.getSiteCd(), kcpPaymentCancel.getSiteKey(), tran_cd, "", "",
						kcpPaymentConfig.getLogLevel(), "1");
			} else {
				c_PayPlus.m_res_cd = "9562";
				c_PayPlus.m_res_msg = "연동 오류";
			}
			/*
			 * --------------------------------------------------------------------------
			 * 05. 실행 END
			 * =============================================================================
			 */

			/*
			 * --------------------------------------------------------------------------
			 * 06. 취소 결과 처리 - 각 참조 값만 확인할 것(KcpPaymentCancelReturn에 담겨 옴)
			 * =============================================================================
			 */
//			if (res_cd.equals("0000")) { // 정상결제 인 경우
//				card_cd = c_PayPlus.mf_get_res("card_cd");
//				card_name = c_PayPlus.mf_get_res("card_name");
//				amount = c_PayPlus.mf_get_res("amount");
//				coupon_mny = c_PayPlus.mf_get_res("coupon_mny");
//				canc_time = c_PayPlus.mf_get_res("canc_time");
//
//				if (mod_type.equals("STPC")) { // 부분 취소 정상결제인 경우
//					card_cd = c_PayPlus.mf_get_res("card_cd");
//					card_name = c_PayPlus.mf_get_res("card_name");
//					amount = c_PayPlus.mf_get_res("amount");
//					coupon_mny = c_PayPlus.mf_get_res("coupon_mny");
//					canc_time = c_PayPlus.mf_get_res("canc_time");
//					panc_mod_mny = c_PayPlus.mf_get_res("panc_mod_mny");
//					panc_rem_mny = c_PayPlus.mf_get_res("panc_rem_mny");
//					panc_coupon_mod_mny = c_PayPlus.mf_get_res("panc_coupon_mod_mny ");
//					panc_card_mod_mny = c_PayPlus.mf_get_res("panc_card_mod_mny");
//					mod_pcan_seq_no = c_PayPlus.mf_get_res("mod_pcan_seq_no");
//				}
//			}

			// 취소 결과 값 model 리턴
			KcpPaymentCancelReturn kcpPaymentCancelReturn = KcpPaymentCancelReturn.of(c_PayPlus);

			super.dbLog(kcpPaymentCancelReturn);

			if (StringUtils.equals(kcpPaymentCancelReturn.getResCd(), "0000")) {
				return new PaymentResult(PaymentConst.YN_Y, kcpPaymentCancelReturn.getResCd(), "",
						kcpPaymentCancelReturn, PaymentConst.PAYMENT_CANCEL_SUCCESS_MESSAGE, "");
			} else {
				return new PaymentResult(PaymentConst.YN_N, kcpPaymentCancelReturn.getResCd(), "",
						kcpPaymentCancelReturn, kcpPaymentCancelReturn.getResMsg(), "");
			}

			/*
			 * --------------------------------------------------------------------------
			 * 07. 취소 결과 출력 - 각 참조 값만 확인
			 * =============================================================================
			 */
//			if (res_cd.equals("0000") && mod_type.equals("STSC")) {
//				out.println("취소요청이 완료되었습니다.        <br>");
//				out.println("결과코드 : " + res_cd + "<br>");
//				out.println("카드 코드: " + card_cd + "<br>");
//				out.println("카드 명  : " + card_name + "<br>");
//				out.println("결제 금액: " + amount + "<br>");
//				out.println("쿠폰 금액: " + coupon_mny + "<br>");
//				out.println("취소 시간: " + canc_time + "<br>");
//				out.println("결과메세지 : " + res_msg + "<p>");
//			} else if (res_cd.equals("0000") && mod_type.equals("STPC")) {
//				out.println("취소요청이 완료되었습니다.        <br>");
//				out.println("결과코드 : " + res_cd + "<br>");
//				out.println("카드 코드: " + card_cd + "<br>");
//				out.println("카드 명  : " + card_name + "<br>");
//				out.println("결제 금액: " + amount + "<br>");
//				out.println("쿠폰 금액: " + coupon_mny + "<br>");
//				out.println("취소 시간: " + canc_time + "<br>");
//				out.println("취소 금액: " + panc_mod_mny + "<br>");
//				out.println("남은 금액: " + panc_rem_mny + "<br>");
//				out.println("쿠폰 취소 금액:" + panc_coupon_mod_mny + "<br>");
//				out.println("카드 취소 금액:" + panc_card_mod_mny + "<br>");
//				out.println("부분취소 일련번호:" + mod_pcan_seq_no + "<br>");
//				out.println("결과메세지 : " + res_msg + "<p>");
//			} else {
//				out.println("취소요청이 처리 되지 못하였습니다.  <br>");
//				out.println("결과코드 : " + res_cd + "<br>");
//				out.println("결과메세지 : " + res_msg + "<p>");
//			}

			/*
			 * --------------------------------------------------------------------------
			 * 07. 취소 결과 출력 END
			 * =============================================================================
			 */
		} catch (Exception e) {
			log.error(e.toString());
			return new PaymentResult(PaymentConst.YN_N, "", "", null, PaymentConst.PAYMENT_CANCEL_FAIL_MESSAGE,
					e.toString());
		}
	}

	/**
	 * @Desc : 환불
	 * @Method Name : vcntRefund
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param payInfo
	 * @return
	 * @throws PaymentException
	 */
	@Override
	public PaymentResult refund(PaymentInfo payInfo) throws PaymentException {
		KcpPaymentRefund kcpPaymentRefund = (KcpPaymentRefund) payInfo.getPayInfo();

		try {
			/*
			 * =============================================================================
			 * 01. 인스턴스 생성 및 초기화(변경 불가)
			 * --------------------------------------------------------------------------
			 */
			String req_tx = kcpPaymentRefund.getReqTx(); // 요청 종류
			String cust_ip = kcpPaymentRefund.getCustIp(); // 요청ip
			String mod_type = kcpPaymentRefund.getModType(); // 프로세스 요청 구분 (전체취소= STSC, 부분취소= STPC)
			String tno = kcpPaymentRefund.getTno(); // 결제승인번호
			String mod_desc = kcpPaymentRefund.getModDesc(); // 취소사유
			String mod_comp_type = kcpPaymentRefund.getModCompType();
			String tran_cd = ""; // 업무코드
			/*
			 * =============================================================================
			 * 01. 인스턴스 생성 및 초기화 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 02. 인스턴스 생성 및 초기화(변경 불가)
			 * --------------------------------------------------------------------------
			 */
			J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

			c_PayPlus.mf_init("", kcpPaymentConfig.getGwUrl(), kcpPaymentConfig.getPort(),
					Integer.parseInt(kcpPaymentConfig.getTxMode()), kcpPaymentConfig.getLogPath());
			c_PayPlus.mf_init_set();
			/*
			 * =============================================================================
			 * 02. 인스턴스 생성 및 초기화 END
			 * =============================================================================
			 */

			/*
			 * =============================================================================
			 * 03. 처리 요청 정보 설정, 실행
			 * --------------------------------------------------------------------------
			 *
			 * --------------------------------------------------------------------------
			 * 03-1. 승인 요청
			 * --------------------------------------------------------------------------
			 */
			if (StringUtils.equals(req_tx, "mod")) {
				int mod_data_set_no;

				tran_cd = "00200000";
				mod_data_set_no = c_PayPlus.mf_add_set("mod_data");

				c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", mod_type); // 원거래 변경 요청 종류
				c_PayPlus.mf_set_us(mod_data_set_no, "tno", tno); // 거래번호
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip", cust_ip); // 변경 요청자 IP
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", mod_desc); // 변경 사유

				c_PayPlus.mf_set_us(mod_data_set_no, "mod_bankcode", kcpPaymentRefund.getModBankcode()); // 환불 요청 은행 코드
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_account", kcpPaymentRefund.getModAccount()); // 환불 요청 계좌
				c_PayPlus.mf_set_us(mod_data_set_no, "mod_depositor", kcpPaymentRefund.getModDepositor()); // 환불 요청 계좌주명

				if (StringUtils.equals(mod_type, "STHD")) {
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC00"); // 변경 유형
				} else if (StringUtils.equals(mod_type, "STPD")) {
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_sub_type", "MDSC03"); // 변경 유형
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", String.valueOf(kcpPaymentRefund.getModMny())); // 환불요청금액
					c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", String.valueOf(kcpPaymentRefund.getRemMny())); // 환불전금액
				}

				if (StringUtils.equals(mod_comp_type, "MDCP01")) {
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_comp_type", "MDCP01"); // 변경 유형
				} else if (StringUtils.equals(mod_comp_type, "MDCP02")) {
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_comp_type", "MDCP02"); // 변경 유형
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_socno", kcpPaymentRefund.getModSocno()); // 실명확인 주민번호
					c_PayPlus.mf_set_us(mod_data_set_no, "mod_socname", kcpPaymentRefund.getModSocname()); // 실명확인 성명
				}
			}
			/*
			 * --------------------------------------------------------------------------
			 * 03. 처리 요청 정보 설정, 실행 END
			 * ==========================================================================
			 */

			/*
			 * =============================================================================
			 * 04. 실행
			 * -----------------------------------------------------------------------------
			 */
			if (tran_cd.length() > 0) {
				// 마지막 인자 값 : 한글 캐릭터셋에 대한 옵션이며 기본적으로 KCP는 EUC-KR 이라 "0"이며 KCP로 유입되는 한글 전문이 깨질 경우
				// "1" 로(UTF-8) 변경 가능
				c_PayPlus.mf_do_tx(kcpPaymentRefund.getSiteCd(), kcpPaymentRefund.getSiteKey(), tran_cd, cust_ip, "",
						kcpPaymentConfig.getLogLevel(), "1");
			} else {
				c_PayPlus.m_res_cd = "9562";
				c_PayPlus.m_res_msg = "연동 오류";
			}
			/*
			 * --------------------------------------------------------------------------
			 * 04. 실행 END
			 * =============================================================================
			 */

			// 승인 결과 값 model 리턴
			KcpPaymentRefundReturn kcpPaymentRefundReturn = KcpPaymentRefundReturn.of(c_PayPlus);

			super.dbLog(kcpPaymentRefundReturn);

			if (StringUtils.equals(kcpPaymentRefundReturn.getResCd(), "0000")) {
				return new PaymentResult(PaymentConst.YN_Y, kcpPaymentRefundReturn.getResCd(), "",
						kcpPaymentRefundReturn, PaymentConst.PAYMENT_REFUND_SUCCESS_MESSAGE, "");
			} else {
				return new PaymentResult(PaymentConst.YN_N, kcpPaymentRefundReturn.getResCd(), "",
						kcpPaymentRefundReturn, kcpPaymentRefundReturn.getResMsg(), "");
			}

		} catch (Exception e) {
			log.error(e.toString());
			return new PaymentResult(PaymentConst.YN_N, "", "", null, PaymentConst.PAYMENT_REFUND_FAIL_MESSAGE,
					e.toString());
		}
	}

}
