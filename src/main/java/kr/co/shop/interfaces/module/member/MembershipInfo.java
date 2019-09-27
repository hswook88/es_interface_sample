package kr.co.shop.interfaces.module.member;

import kr.co.shop.common.util.UtilsText;
import lombok.Data;

/**
 * @Desc : 맴버쉽포인트 인터페이스 정보
 * @FileName : MembershipInfo.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
@Data
public class MembershipInfo {

	/**
	 * 시스템인코딩
	 */
	public final static String sysCharset = "UTF-8";

	/**
	 * 맴버쉽서버 IP
	 */
	public final static String ip = "218.145.160.11";

	/**
	 * 접근포트
	 */
	public final static String port = "99";

	/**
	 * @Desc : 소멸예정포인트 조회[memB050a], 재고통합-매장/창고 실시간 재고도회[memB060a], 재고통합-주문번호별
	 *       상태조회[memB070a], 카드 분실처리[memB290a], 카드 분실처리[memA810a], 카드 이력
	 *       조회[memA820a], 현재 카드 번호 조회[memA830a], 분실카드 취소처리[memA850a], 기타적립포인트 유효성
	 *       확인[memA860a], 기타포인트 적립/차감[memA870a], 모바일카드 발급[memA880a], 상품권
	 *       잔액조회[memA900a], 가용포인트 조회[memA910a], 포인트 적립이력 조회[memA920a], 구매이력
	 *       조회[memA930a], 포인트 강제 적립/차감[memA940a], 카드 유효성 여부 확인[memA960a], 카드 사용
	 *       비밀번호 등록처리[memA970a], 카드 등록[memA990a]
	 * @Method Name : getMemberUrl
	 * @Date : 2019. 3. 15.
	 * @Author : 백인천
	 * @param code
	 * @return
	 */
	public static String getMemberUrl(String code) {
		String path = "/ngisns/jsp/mem/";
		String subPath = UtilsText.substring(code, 0, 4);
		String memUrlPath = UtilsText.concat(path, subPath, "/", code, ".jsp");

		return memUrlPath;
	}
}
