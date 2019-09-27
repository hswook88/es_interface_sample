package process;

import static org.mockito.Mockito.validateMockitoUsage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
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

import kr.co.shop.common.paging.Pageable;
import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPickupCustomer;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForArs;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForErp;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesBatchSqlDao;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesClaimDao;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesDeliveryDao;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesOrderDao;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesProductDao;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesBatchSqlService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesDeliveryService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesOrderService;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { InterfacesProductService.class,
		InterfacesProductDao.class, DSConfigMaster.class, InterfacesBatchSqlService.class, InterfacesBatchSqlDao.class
		,InterfacesDeliveryService.class, InterfacesDeliveryDao.class,InterfacesOrderService.class, InterfacesOrderDao.class,
		InterfacesClaimService.class, InterfacesClaimDao.class})
@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath:transaction.xml")
public class MyControllerTests3 {



	@Autowired
	InterfacesProductService interfacesProductService;
	
	/*	
	@Autowired
	InterfacesBatchSqlService interfacesBatchSqlService;
	*/
	/*
	@Autowired
	InterfacesDeliveryService interfacesDeliveryService;
	
	@Autowired
	InterfacesOrderService interfacesOrderService;
	
	@Autowired
	InterfacesClaimService interfacesClaimService;
	*/
	
	
	// 브랜드 수신용
	@Test
	public void testExample() throws Exception {
//		log.debug("testtesttest {}", interfaceErpProductService);
//		assertNotNull("널 값이 아닙니다.", interfaceErpProductService.searchErpBrand());
//		String BrandCd = "000001";
//		String maejangcd = "";
//		String sangPumCd = "";
//		String WorkDiv = "2";
//		String inputType = "9";
//		String sangPumFullCd = "";
		
		//ERP브랜드 등록후 호출하는 프로시저
//		List<InterfacesBrandErp> updateProcedureErpBrand = interfacesProductService.updateProcedureErpBrand(BrandCd, WorkDiv);
		
		//ERP가격정보 호출 등록 후 호출하는 프로시저
//		List<InterfacesGamGaErp> updateProcedureErpGamGa = interfacesProductService.updateProcedureErpGamGa(maejangcd, sangPumCd);
		
		//ERP가격정보 호출 등록 후 호출하는 프로시저
//		List<InterfacesSangPumErp> updateProcedureErpSangPum = interfacesProductService.updateProcedureErpSangPum(maejangcd, sangPumCd);
		
		//ERP
//		List<InterfacesStockErp> updateProcedureErpStockErps = interfacesProductService.updateProcedureErpStockErps(maejangcd, sangPumFullCd, inputType);
/*		
		String CBCd = "AI";
		String maejangCd = "0072";
		String dlvyId = "";
		String newRcvrName = "";
		String newDlvyPostNum = "";
		String newHpNum = "";
		String newHpNum2 = "";
		String newHpNum3 = "";
		String newTelNum = "";
		String newTelNum2 = "";
		String newTelNum3 = "";
		String newAddr1 = "";
		String newAddr2 = "";
		String dlvyMessage = "";
		*/
		
		
//		List<InterfacesReturnProcedureAddr> returnProcedureAddrs = interfacesProductService.updateReturnAddr();
		/*

		String cbcd = "AI";
		String dlvyId = "20120509000030002";
		String dlvyMessage = "null";
		String maejangCd = "0072";
		String newAddr1 = "아이고";
		String newAddr2 = "맙소사";
		String newDlvyPostNum = "430-706";
		String newRcvrName = "p";
		String newHpNum  ="010";
		String newHpNum2 ="0000";
		String newHpNum3 ="0000";
		String newTelNum ="02";
		String newTelNum2 ="000";
		String newTelNum3 ="0000";

		String postNum = "430-706";
		String sangPumFullCd1 = "0022005001300";
		String sangPumFullCd2 = "0022005001210";
		String itemSno = "682806";
		int orderCount = 1;
		String sangPumFullCd = "0000021001003";
		String sangPumCd = "0000021";
		String asReturnConfirm ="0";
		String addr = "경기 안양시 만안구 석수2동 럭키아파트 ";
		String addrDtl = "ABC마트";
		String hdphnNum = "010-0000-0000";
		String rcvrName = "홍길동";
		String prdtCode = "0022005";
		String optionValue = "210";

		String AS = "AS";
		
		int addPsbltDt = 20180512;
		*/
//		String workDiv = "2"; //현재 구성해놓은 중간계 프로시저에 상태값을 고정하는 로직으로 주석처리
		
		/*
		String cbcd = "AI";
		String dlvyId = "20140625000130001";
		String maejangCd = "0072";
		String itemSno = "683978F1";
		String sangPumFullCd = "F000007001FFF";
		String count = "1";
		*/
//		interfacesOrderService.updateOrderChangeAddrNoTrx(cbcd, dlvyId, dlvyMessage, maejangCd, newAddr1, newAddr2, newDlvyPostNum, newHpNum, newHpNum2, newHpNum3, newRcvrName, newTelNum, newTelNum2, newTelNum3);
		
//		interfacesOrderService.updateOrderChangeOptnNoTrx(cbcd, dlvyId, itemSno, maejangCd, sangPumFullCd1, sangPumFullCd2);
		
//		interfacesClameService.updateOrderPrdtGiftCancelNoTrx(cbcd, maejangCd, dlvyId, itemSno, sangPumFullCd, count);
		
//		interfacesClameService.updateOrderPrdtNoGiftCancelNoTrx(cbcd, maejangCd, dlvyId, itemSno, sangPumFullCd, count);
		
//		interfacesClameService.updateOrderReturnProductNoTrx(cbcd, maejangCd, dlvyId, asReturnConfirm);
		
//		interfacesClameService.updateOrderReturnProductPickUpNoTrx(cbcd, maejangCd, dlvyId, postNum, addr, addrDtl, hdphnNum, rcvrName, prdtCode, optionValue);

//ars서버 쪽에 프로시저가 없음		interfacesDeliveryService.updateExtensionPickupPsbltNoTrx(dlvyId, addPsbltDt);
		
		
		
		
//		interfacesProductService.updateStockArsErpNoTrx(cbcd, maejangCd, orderCount, sangPumFullCd);
    
		String maejangcd = "7777";
		String sangPumCd = "7777777";
		
		
		//ERP브랜드 등록후 호출하는 프로시저
//		interfacesProductService.updateProcedureErpBrand(BrandCd, WorkDiv);
		
		//ERP가격정보 호출 등록 후 호출하는 프로시저
		interfacesProductService.updateProcedureErpGamGaNoTrx(maejangcd, sangPumCd);
		
		//ERP가격정보 호출 등록 후 호출하는 프로시저
//		interfacesProductService.updateProcedureErpSangPum(maejangCd, sangPumCd);
		
		
		//ERP
//		interfacesProductService.updateProcedureErpStockErps(maejangcd, sangPumFullCd, inputType);
/*	
		String sangpumCd = "0000021";
		String maejangCd = "0072";
		
		List<InterfacesSangPumDetailToErp> erpsangpum = interfacesProductService.selectErpSangPumDetail(sangpumCd, maejangCd);
		for(InterfacesSangPumDetailToErp sangpum : erpsangpum ) {
			log.debug(UtilsText.concat(sangpum.getMaejangCd()));
			log.debug(UtilsText.concat(sangpum.getSangPumFullCd()));
		}
		*/
	/*
		List<InterfacesBrandToErp> erpBrandList = interfacesProductService.selectErpBrand();
		for(InterfacesBrandToErp brandList : erpBrandList) {
			log.debug(UtilsText.concat(brandList.getBrandCd()));
			log.debug(UtilsText.concat(brandList.getBrandNm()));
			
			if(erpBrandList != null && erpBrandList.size() > 0){
				interfacesProductService.insertBrandBatchNoTrx(brandList.getBrandCd(), brandList.getBrandNm(), brandList.getBrandNm(), brandList.getBrandCd(), brandList.getUseYn());
				//상품 insert -> update로 변경 상품 이름수정에 대해서 변경하지못함
				
			}
		
			interfacesProductService.updateProcedureErpBrandNoTrx(brandList.getBrandCd(), "2");
			//if_brand의 workdiv상태값 변경
		}
		*/
		
/* as-is 로직처리 */
	/*
		List<InterfacesBrandToErp> erpBrandList = interfacesProductService.selectErpBrand();
		if (erpBrandList != null && erpBrandList.size() > 0) {
			for (InterfacesBrandToErp brand : erpBrandList) {
				try {
					interfacesProductService.insertBrandBatchNoTrx(brand.getBrandCd(), brand.getBrandNm(), brand.getBrandNm(), brand.getBrandCd(), brand.getUseYn());
				} catch (Exception e) {
					log.error("brand.setBrandCd : brandcd = " + brand.getBrandCd() + "::::"
							+ e.toString());
				}

				interfacesProductService.updateProcedureErpBrandNoTrx(brand.getBrandCd(), "2"); 
			}
		}
*/
		
		
		//String sangpumCd = "0000567";
		//String maejangCd = "0072";
		/*
		List<InterfacesSangPumToErp> sangpumli = interfacesProductService.selectErpSangPum();
		for(InterfacesSangPumToErp ssp : sangpumli) {
			log.debug(UtilsText.concat(ssp.getSangPumCd()));
			log.debug(UtilsText.concat(ssp.getMaejangCd()));
			log.debug(ssp.toString());
		}
		
		
		List<InterfacesSangPumDetailToErp> sangpum = interfacesProductService.selectErpSangPumDetail(sangpumCd, maejangCd);
		for(InterfacesSangPumDetailToErp sp : sangpum) {
			
			log.debug(sp.toString());
		}
		*/
	/*
		List<InterfacesOnlineProductStock> ssp = interfacesBatchSqlService.selectOrderCount();
		for(InterfacesOnlineProductStock s : ssp) {
			log.debug(s.toString());
			int orderCount = s.getOrderCount();
			String sangPumFullCd = s.getVndrPrdtNoText();
			String cbcd = "AI";
			String maejangCd = "0072";
				
				interfacesBatchSqlService.sampleUpdateStockOrderCountNoTrx(orderCount, sangPumFullCd, maejangCd, cbcd);
				//중간계 상태값 변경
				interfacesBatchSqlService.sampleUpdateStockNoTrx(orderCount, sangPumFullCd, maejangCd, cbcd);
		}
		*/
		
		/*
	//미등록 상품 수신(재고 유무에 따라 없으면 안뜸)
		List<String> sangPumCdList = new ArrayList<String>();
		//sangPumCdList.add("12412516");
		//sangPumCdList.add("12412516");
		//sangPumCdList.add("0000567");
		String sangPumCd = null;
		String sangPumNm = null; //
		String brandNm = null; //"아디다스";
		String regStartDate = "20100507";
		String regEndDate = "20181105";
	
		List<InterfacesSangPumToErp> sang =  interfacesProductService.selectErpSangPum(sangPumCdList, sangPumNm, brandNm, regStartDate, regEndDate);
		for(InterfacesSangPumToErp ss : sang) {
			log.debug(ss.toString());
		}
		*/
		
		
		
//		String sendNum = "1234567890";
		
		
		
		
		//int abc = interfacesProductService.selectErpSangPumCount(pageable);
		
			
		
		//selectErpSangPumCount(pageable)
		
		/*
		
	List<InterfacesPickupCustomer> pick = interfacesDeliveryService.selectAblePickUpTacbaeNumber(sendNum);
		for(InterfacesPickupCustomer sss : pick) {
			log.debug(sss.toString());
		}
		*/
/*
		List<InterfacesPickupCustomer> Dtm = interfacesDeliveryService.selectDtm();
		log.debug(Dtm.toString());
		*/
		
//		interfacesDeliveryService.updatePickUpDeliveryBySendNumberNoTrx(sendNum);
		
		
		/*
		List<InterfacesSangPumToErp> sang =  interfacesProductService.selectErpSangPum(pageable);
		for(InterfacesSangPumToErp ss : sang) {
			log.debug(ss.toString());
		}
		*/
		
		
		/*프로세스 정리*/
		
		//주문건수중 조건 조회(완료처리 한정)
		
		//for
		
		//pd_product_option_stock테이블 조회
		
		//if
		
		//주문건수 update
		
		//중간계 update
		

		String sangpumCd = "0000570";
		String maejangCd = "0072";
		
		List<InterfacesGamGaToErp> gg = interfacesProductService.selectErpGamGa(sangpumCd, maejangCd);
		for(InterfacesGamGaToErp g : gg) {
		//	log.debug(String.valueOf(g.getOnlinerate()));
			log.debug(g.getSpgb());
		}
		
		
	/*	
		List<InterfacesGamGaToErp> gamga = interfacesProductService.selectErpGamGa(sangpumCd, maejangCd);
		for(InterfacesGamGaToErp gg : gamga) {
			
			log.debug(gg.toString());
			
		}
		*/
	
		
		/*
		int orderCount = 7;
		String sangPumFullCd = "0000021001003";
		String maejangCd = "0074";
		String cbcd = "AS";
		
		interfacesBatchSqlService.sampleUpdateStockNoTrx(orderCount, sangPumFullCd, maejangCd, cbcd);
		*/
		
//		List<sampleSelectData> insertSampleData	= SampleSelectDataService.insertSampleData(CBCd, MaejangCd, SangPumFullCd, Qty, InputType, RegDate, ConDate, ErrorStatus, WorkDiv, BeforeQty);
		
		

	}

}