package process;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.TestPropertySource;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.MembershipPointService;
import kr.co.shop.interfaces.module.member.model.CardNumber;
import kr.co.shop.interfaces.module.member.model.CardValidate;
import kr.co.shop.interfaces.module.member.model.EmployCertReport;
import kr.co.shop.interfaces.module.member.model.EmployPoint;
import kr.co.shop.interfaces.module.member.model.EmployPointHistory;
import kr.co.shop.interfaces.module.member.model.PrivateReport;
import kr.co.shop.interfaces.module.member.model.TermiExpectReport;
import kr.co.shop.interfaces.module.order.model.OrderNumberGetDelivery;
import kr.co.shop.interfaces.module.order.model.OrderNumberGetDeliveryService;
import kr.co.shop.interfaces.module.product.offlineproduct.ProductOfflineStockService;
import kr.co.shop.interfaces.module.product.offlineproduct.model.ProductOfflineStockHttp;
import kr.co.shop.interfaces.provider.InterfacesContextProvider;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesIOLoggerService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import kr.co.shop.zconfiguration.datasource.DsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = {
		// START DB AND BeanContext Configuration ※ 고정
		DsConfig.class, DSConfigMaster.class, InterfacesContextProvider.class,
		// END DB AND BeanContext Configuration
		ProductOfflineStockService.class,
		InterfacesIOLoggerService.class, MembershipPointService.class, TermiExpectReport.class, CardNumber.class,
		PrivateReport.class, CardValidate.class })

@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath:transaction.xml")
public class MyControllerAPITests {

	// @Autowired
	// private InterfacesSampleService interfacesSampleService;

	@Autowired
	private MembershipPointService membershipPointService;
	
	@Autowired
	private ProductOfflineStockService productOfflineStockService;
	
	@Autowired
	private OrderNumberGetDeliveryService orderNumberGetDeliveryService;

//	@Test
//	@DisplayName("Integration test which will get the actual output of text service")
//	public void contextLoads() throws Exception {
//		assertEquals("sample", interfacesSampleService.searchSampleBoard()); 
//	}

	@Test
	public void testExample() throws Exception {
		// log.debug("############## {}", interfacesSampleService);
		// assertNotNull("널 값이 아닙니다.", interfacesSampleService.searchSampleBoard());
		// String safe_key =
		// "MC0GCCqGSIb3DQIJAyEA062+QjdF3paUJLqNSNesAp3ID+VC6ND6J0GGH2BuUl4="; // 회원 보안키
		// String safe_key =
		// "MC0GCCqGSIb3DQIJAyEzxcrpkHmgoxrn5FZunupLP2cgKrtP74iprF46GUOBg5I="; // 회원 보안키
		String safe_key = "MC1GCCqGSIb3DQIJAyEAWHiYtYVRmzVkPV4UQCqc4YM8Xgozj+j5u6wcwHje7SI="; // 회원 보안키
		int testChangeAmt = 1000; // 변동금액
		String testPointCode = "test"; // 포인트코드
		String testCardNum = "2430515692800943"; // 카드넘버
		String testPasswd = "1234"; // 패스워드
		String orderNum = "123123123123";
		String testa = "";

		// 임직원 테스트용 변수
		String employeesNumber = "";
		String employeesName = "홍길동";
		String employeesUsePoint = "1000";
		String employeesCancelPoint = "1000";

		// 소멸예정포인트 조회 [memB050a]
		// TermiExpectReport testExpPoint =
		// MembershipPointService.getTermiExpectReportBySafeKey(safe_key);
		// log.debug("소멸예정포인트 > " + testExpPoint.getExpire_point());

		// 오프라인 AS 이력 (멤버십회원만) [memB110a]
		// List<AfterServiceHistory> testAsHistory =
		// MembershipPointService.getOfflineAsHistory(safe_key);
		// log.debug("testAsHistory size > " + String.valueOf(testAsHistory.size()));

		log.debug("포인트지급결과1 > ");

		// 포인트 적립/차감 [memB290a]
		boolean complateChk = membershipPointService.updatePointForMembershipHandler(safe_key, 1000, "cancel", 0);
		log.debug("포인트지급결과2 > " + complateChk);

		// 매장 비회원 구매 사후적립 , 오프라인구매확정 포인트적립 [memB300a]
		// String fixedPoint = membershipPointService.updatePointAfterPurchase(safe_key,
		// "0072", "00001", "0123456", 38000,
		// "20191231");
		// log.debug("fixedPoint > " + fixedPoint);

		// 내용이 있으면 코드랑 비교한다.
		// if (UtilsText.isNotEmpty(fixedPoint)) {
		// 코드테이블에서 해당 파일에 대한 코드를 가져온다
		// List<Code> failCodes =
		// InstanceFactory.getInstance().getCodeHandler().getCodes("MEMB300_FAIL_CODE");

		/**
		 * 04063801 구입내역없음 04063802 구입내역오류 04063803 구매확정된오류 04063804 반품된영수증오류 04063805
		 * 기타오류
		 * 
		 * --alert 문구 구입내역없음 입력하신 내용으로 조회된 내역이 없습니다. (Error Code : 04063801) 구입내역오류 입력하신
		 * 내용으로 조회된 내역이 없습니다. (Error Code : 04063802 ) 구매확정된오류 포인트 지급대상이 아닙니다. (Error
		 * Code : 04063803 ) 반품된영수증오류 포인트 지급대상이 아닙니다. (Error Code : 04063804) 기타오류 입력하신
		 * 내용으로 조회된 내역이 없습니다. (Error Code : 04063805 )
		 */
		// for (Code code : failCodes) {
		// if (UtilsText.equals(code.getCodeName(), fixedPoint)) {
		// failCode = code.getCodeValue();
		// break;
		// }
		// }
		// }

		// 환수포인트조회 [memB310a]
		// int clawbackPoint = membershipPointService.getClawbackPoint(safe_key,
		// "0123123123", 38000);
		// log.debug("환수포인트 > " + String.valueOf(clawbackPoint));

		// 포인트 환수 [memB320a]
		// boolean setClawbackPoint =
		// membershipPointService.updateClawbackPoint(safe_key, "000000123123123",
		// 3800);
		// log.debug("포인트 환수 성공여부 > " + String.valueOf(setClawbackPoint));

		// 카드분실처리 [memA810a]
		// boolean loseCardSet =
		// membershipPointService.inputLoseCardForUserBySafeKey(safe_key, testCardNum);
		// if (!loseCardSet) {
		// log.debug("카드 분실처리에 실패하였습니다.");
		// } else {
		// log.debug("카드 분실처리 성공");
		// }
/*
			String sangpumfullcd = "0000627001295";
			boolean stockmergeyn = true;
			List<ProductOfflineStockHttp> tests = productOfflineStockService.getProductOfflineStockFromHttp(sangpumfullcd, stockmergeyn);
			for(ProductOfflineStockHttp test : tests) {
				
				log.debug(test.toString());
			}
			*/
		
			List<OrderNumberGetDelivery> testorder = orderNumberGetDeliveryService.getDeliveryInfoForStockMerge(orderNum);

			for(OrderNumberGetDelivery test : testorder) {
				
				log.debug(test.toString());
			}
			
			
		// 회원카드정보이력 [memA820a]
		// List<CardHistory> testCardHistory =
		// membershipPointService.getCardHistorysBySafeKey(safe_key);
		// for (CardHistory cdHistory : testCardHistory) {
		// log.debug("카드정보 > " + cdHistory.getCardIssueDate() + " / " +
		// cdHistory.getCardNo() + " / "
		// + cdHistory.getDetailName());
		// }

		// 멤버십 카드 번호 조회 [memA830a]
		// CardNumber testCardNumber =
		// membershipPointService.getUserCardNumberBySafeKey(safe_key);
		// testCardNum = String.valueOf(testCardNumber.getCardNumber());
		// log.debug("testCardNum > " + testCardNum);

		// 기타적립코드 유효성 확인 [memA860a]
		// Boolean testValidationBySaveCode =
		// membershipPointService.isValidationBySavedCode("ValidationCode");

		// 기타적립금 적립차감 [memA870a]
		// boolean successChk =
		// membershipPointService.updateEtcProcessUserPointByUser(safe_key,
		// testChangeAmt,
		// testPointCode);
		// log.debug("적립금차감 성공여부 > " + String.valueOf(successChk));

		// 모바일카드 발급 [memA880a]
		// boolean compChk = membershipPointService.inputMobileCardBySafeKey(safe_key,
		// testPasswd);
		// log.debug("모바일카드 발급성공여부 > " + String.valueOf(compChk));

		// 구매확정시 포인트지급 [memA890a]
		// List<BuyFixProduct> testbuyFix =
		// membershipPointService.buyFixRequest(safe_key, orderNum);
		// for (BuyFixProduct bFix : testbuyFix) {
		// log.debug("구매확정시 포인트 지급 > " + bFix.getFullCode() + " / " +
		// bFix.getResultYn());
		// }

		// 기프트 카드 정보 조회 [memA900a]
		// GiftCard chkGifeCard = membershipPointService.checkGiftCard(testCardNum);
		// if (chkGifeCard.getBalance() > 0) {
		// log.debug(chkGifeCard.getGiftNo() + " : " +
		// String.valueOf(chkGifeCard.getBalance()));
		// }

		// 가용포인트 조회 [memA910a]
		// PrivateReport testPrivateReport =
		// membershipPointService.getPrivateReportBySafeKey(safe_key);
		// log.debug("TotalPoint > " + testPrivateReport.getTotalPoint());

		// 포인트 적립 이력 [memA920a]
		// List<StorePointHistory> testStorePointHistory =
		// membershipPointService.getStorePointHistoryBySafeKey(safe_key);
		// for (StorePointHistory aHistory : testStorePointHistory) {
		// log.debug(UtilsText.concat("포인트 만료일 > ",
		// String.valueOf(aHistory.getBuyPointExtinctDate()), " / ",
		// "이벤트 기간확인 > ", String.valueOf(aHistory.getValidateDate())));
		// }

		// 구매이력 조회 [memA930a]
		// List<StoreOrderHistory> testStoreOrder =
		// membershipPointService.getStoreOrderHistoryBySafeKey(safe_key);
		// for (StoreOrderHistory aStoreOrder : testStoreOrder) {
		// log.debug(UtilsText.concat("상품명 > ",
		// String.valueOf(aStoreOrder.getProductNm()), " / ", "구매시기 > ",
		// String.valueOf(aStoreOrder.getSaleDate())));
		// }

		// 포인트 강제 적립/차감 [memA940a]
		// boolean registUserPointChk =
		// membershipPointService.registUserPointByUser(safe_key, testChangeAmt);
		// if (!registUserPointChk) {
		// log.debug("강제 포인트 적립/차감에 실패하였습니다.");
		// } else {
		// log.debug("포인트 적립/차감이 완료되었습니다.");
		// }

		// 맴버십 카드 유효성 여부 확인 [memA960a]
		// CardValidate testVtCardnum =
		// membershipPointService.validateByCardNumberBySafeKey(safe_key, testCardNum);
		// String vtCdNum = testVtCardnum.getCardStat();
		// log.debug(UtilsText.concat("맴버십카드 여부 > ", vtCdNum));

		// 맴버십카드 비밀번호 변경 [memA970a]
		// boolean chkChangePassword =
		// membershipPointService.changeCardPasswordBySafeKey(safe_key, testPasswd);
		// if (!chkChangePassword) {
		// log.debug("맴버십카드 패스워드 변경에 실패하였습니다.");
		// }

		employeesNumber = "01";
		employeesName = "홍길동";
		employeesUsePoint = "1000";
		employeesCancelPoint = "1000";

		// 임직원 인증 [memA1000a]
		EmployCertReport employReport = membershipPointService.employeeCertificationByEmployNum(employeesNumber,
				employeesName);
		if (employReport.getSuccessFlag() == "SUCCESS") {
			log.debug(UtilsText.concat("사번 : ", employReport.getEmployeesNumber(), " / 이름 : ",
					employReport.getEmployeesName(), " / 부서 : ", employReport.getEmployeesDpmt(), " / 직책 : ",
					employReport.getEmployeesPosition(), " / 입사일 : ", employReport.getEmployeesJoinDt()));
		} else {
			log.debug("임직원정보 조회에 실패하였습니다.");
		}

		// 임직원 포인트 사용내역 [memA1001a]
		List<EmployPointHistory> empPointHis = membershipPointService.checkPointHistoryByEmployNum(employeesNumber);
		if (empPointHis.size() > 0) {
			for (EmployPointHistory pointHis : empPointHis) {
				log.debug(UtilsText.concat("사용구분 : ", pointHis.getUseGubun(), " / 사용금액 : ", pointHis.getUsePoint(),
						" / 사용일자 : ", pointHis.getInsDate(), " / 잔액 : ", pointHis.getBalancePoint()));
			}
		} else {
			log.debug("임직원 포인트 사용내역 조회에 실패하였습니다.");
		}

		// 임직원 포인트 사용처리 [memA1002a]
		EmployPoint usePoint = membershipPointService.saveUsePointByEmployNum(employeesNumber, employeesUsePoint);
		if (usePoint.getSuccessFlag() == "SUCCESS") {
			log.debug(UtilsText.concat("총포인트 : ", usePoint.getTotalPoint(), " / 잔액 : ", usePoint.getBalancePoint()));
		} else {
			log.debug("임직원정보 포인트 사용이 실패하였습니다.");
		}

		// 임직원 포인트 취소처리 [memA1003a]
		EmployPoint cancelPoint = membershipPointService.saveCancelPointByEmployNum(employeesNumber,
				employeesCancelPoint);
		if (cancelPoint.getSuccessFlag() == "SUCCESS") {
			log.debug(UtilsText.concat("총포인트 : ", cancelPoint.getTotalPoint(), " / 잔액 : ",
					cancelPoint.getBalancePoint()));
		} else {
			log.debug("임직원정보 포인트 사용취소가 실패하였습니다.");
		}

	}

}