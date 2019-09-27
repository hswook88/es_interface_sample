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
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesProductDao;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesDeliveryService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesOrderService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { DSConfigMaster.class, InterfacesDeliveryService.class,
		InterfacesClaimService.class,InterfacesProductService.class, InterfacesProductDao.class })
@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath:transaction.xml")
public class MyControllerTests {

	@Autowired
	private InterfacesDeliveryService interfacesDeliveryService;

	@Autowired
	private InterfacesClaimService interfacesClaimService;

	@Autowired
	private InterfacesProductService interfacesProductService;
	
	@Autowired
	private InterfacesOrderService interfacesOrderService;
	// @Autowired
	// private InterfacesMemberService interfacesMemberService;

	@Test
	public void testExample() throws Exception {
		// log.debug("interfacesOrderService {}", interfacesOrderService);

		// 주문번호
//		String orderNum = "2016082300273";

		// 배송DI
//		String dlvyId = "";

		// interfacesIOLoggerService.insertLogger("MembershipPoint", "testInput",
		// "testOutput", "y");

		// String safeKey =
		// "MC1GCCqGSIb3DQIJAyEAWHiYtYVRmzVkPV4UQCqc4YM8Xgozj+j5u6wcwHje7SI=";
		// String safeKeySeq = "1212";

		// InterfacesMember ifMb =
		// interfacesMemberService.selectMemberOnlineNoTrx(safeKey, safeKeySeq);

		// if (ifMb == null) {
		// log.debug("X 회원정보 없음");
		// } else {
		// log.debug("O 회원정보 있음");
		// }

		/*
		 * InterfacesMember ifMember = new InterfacesMember(); ifMember.set("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz(""); ifMember.setClazz("");
		 * ifMember.setClazz(""); ifMember.setClazz("");
		 * 
		 * interfacesMemberService.insertMemberOnline(ifMember);
		 */
/*
		// 편의점 픽업배송 조회
		int testGetCvsCount = interfacesDeliveryService.getCvsPickupCountToOpenDBNoTrx(orderNum);
		log.debug(UtilsText.concat("[", String.valueOf(orderNum), "] 상품의 편의점 픽업배송 수량 > ",
				String.valueOf(testGetCvsCount)));

		// OPENDB의 CVS_PICKUP테이블에 편의점배송정보 저장
		boolean testInsertCvsPickup = interfacesDeliveryService.insertOrderToCvsPickupOpenDBNoTrx(dlvyId);
		log.debug("InsertCvsPickup Success > " + testInsertCvsPickup);

		// OPENDB의 IF_ORDER로 데이터 전송
		boolean testInsertIfOrderToOpenDB = interfacesDeliveryService.insertIforderToOpenDBNoTrx();
		log.debug("InsertIfOrderToOpenDB Success > " + testInsertIfOrderToOpenDB);

		// OPENDB의 IF_ORDER에 교환배송 데이터를 생성
		boolean testInsertChangeOrderToOpenDB = interfacesDeliveryService.insertChangeOrderToOpenDBNoTrx();
		log.debug("InsertChangeOrderToOpenDB Success > " + testInsertChangeOrderToOpenDB);

		*/
/*		
		String sangpumCd = "0000570";
		String maejangCd = "0072";
		
		List<InterfacesGamGaToErp> gg = interfacesProductService.selectErpGamGa(sangpumCd, maejangCd);
		for(InterfacesGamGaToErp g : gg) {
		//	log.debug(String.valueOf(g.getOnlinerate()));
			log.debug(g.getSpgb());
		}
		
		*/
		
		String maejangCd = "0072";
		String cbcd = "AI";
		String dlvyId = "20120509000030002";
		interfacesOrderService.updateOrderHoldNoTrx(cbcd, maejangCd, dlvyId);
		
		
		
		// OPENDB에서 처리되지 않은 오프라인 AS 이력 조회
//		List<InterfacesClaim> testOfflineAsList = interfacesClaimService.getOfflineAsNoProcessListNoTrx();
//		for (InterfacesClaim claim : testOfflineAsList) {
//			log.debug(UtilsText.concat(claim.getMemberId()), " / ", claim.getProductCd());
//
//			// 오프라인 AS이력 데이터 내부테이블에 Insert
//
//			// 이관된 오프라인 AS이력 데이터에 대해 OPENDB의 데이터 상태값 변경
//			interfacesClaimService.setOfflineAsStateChangeNoTrx(claim);
//		}

	}

}