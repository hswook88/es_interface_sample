package process;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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

import com.ibm.icu.impl.duration.impl.Utils;

import ch.qos.logback.classic.pattern.Util;
import kr.co.shop.common.util.UtilsText;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngOnlineStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpGamga;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpStock;


import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineproductGamGa;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPdProduct;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;


import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesBatchSqlDao;

import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesBatchSqlService;

import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { InterfacesBatchSqlService.class,
		InterfacesProductService.class, InterfacesBatchSqlDao.class, InterfacesBatchSqlDao.class,
		DSConfigMaster.class })
@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath:transaction.xml")
public class MyControllerTests2 {

//	@Autowired
//	private InterfacesProductErpService interfacesProductErpService;

//	@Autowired
//	private InterfacesReturnProcedureService interfacesReturnProcedureService;

	@Autowired
	InterfacesBatchSqlService interfacesBatchSqlService;
	@Autowired
	InterfacesProductService interfacesProductErpService;

	// 브랜드 수신용
	@Test
	public void testExample() throws Exception {
//		log.debug("testtesttest {}", interfaceErpProductService);
//		assertNotNull("널 값이 아닙니다.", interfaceErpProductService.searchErpBrand());


//  ERP 재고 연동 프로세스


		List<InterfacesChngProductErpStock> stock = interfacesBatchSqlService.selectAlterErpStock();
		ArrayList<Map<String, String>> historyList = new ArrayList<>();
		for (InterfacesChngProductErpStock AlterErpStock : stock) {
			

				Map<String, String> temphist = new HashMap<>();

				String prdtOptnNo = AlterErpStock.getSangPumFullCd()
						.substring(AlterErpStock.getSangPumFullCd().length() - 3);
				String vndrPrdtNoText = AlterErpStock.getSangPumFullCd().substring(0, 7);
				int stockQty = AlterErpStock.getQty();
				String cbcd = AlterErpStock.getCbcd();
				String sangpumFullCd = AlterErpStock.getSangPumFullCd();
				String inputType = AlterErpStock.getInputType();
				// option 테이블 조회 List beforeStocks
				InterfacesChngOnlineStock beforeStocks = interfacesBatchSqlService.selectPdProductErpList(cbcd, prdtOptnNo,
						vndrPrdtNoText);
				
				if (beforeStocks != null) {

	//				interfacesBatchSqlService.updateAlterStock(prdtOptnNo, vndrPrdtNoText, stockQty, cbcd);
					log.debug(UtilsText.concat(prdtOptnNo," ", vndrPrdtNoText," ", String.valueOf(stockQty)," " ,cbcd));
	//				interfacesProductErpService.updateProcedureErpStockErps(vndrPrdtNoText, sangpumFullCd, inputType); // wms상의
																														// 프로시저
																														// 호출

					if (historyList.size() > 0) {

						for (Map<String, String> hList : historyList) {
							log.debug(beforeStocks.getPrdtNo()) ;
							log.debug(hList.get("prdtNo"));

							// if (hList.get("prdtNo") == beforeStocks.getPrdtNo()) {
							if ((hList.get("prdtNo")).equals(beforeStocks.getPrdtNo())) {
								// hList.put("afStockQty", hList.get("afStockQty") + AlterErpStock.stockQty());
								int testa = Integer.parseInt(hList.get("afStockQty")) + stockQty;
								hList.put("afStockQty", String.valueOf(testa));
							} else {
								// historySet.put("prdtNo", beforeStocks.getPrdtNo());
								temphist.put("prdtNo",  beforeStocks.getPrdtNo());
								// historySet.put("bfStockQty", beforeStocks.getStockQty());
								temphist.put("bfStockQty", String.valueOf(beforeStocks.getTotalStockQty()));
								// historySet.put("afStockQty", AlterErpStock.stockQty());
								temphist.put("afStockQty", String.valueOf(stockQty));
								temphist.put("prdtOptnNo", beforeStocks.getPrdtOptnNo());
								temphist.put("changeCol", "Stock_Qty");
								historyList.add(temphist);
							}

							log.debug("추가 및 변경 : " + hList.get("prdtNo"));
							
						}

					} else {
						
						// historySet.put("prdtNo", beforeStocks.getPrdtNo());
						temphist.put("prdtNo", beforeStocks.getPrdtNo());
						// historySet.put("bfStockQty", beforeStocks.getStockQty());
						temphist.put("bfStockQty", String.valueOf(beforeStocks.getTotalStockQty()));
						// historySet.put("afStockQty", historySet.get("afStockQty") + stockQty);
						temphist.put("afStockQty", String.valueOf(stockQty));
						temphist.put("prdtOptnNo", beforeStocks.getPrdtOptnNo());
						temphist.put("changeCol", "ToTalStock_Qty");
						temphist.put("changeName",  "총재고수량"+ " "+"주문수량");
						temphist.put("chngHistSeq", String.valueOf(beforeStocks.getChngHistSeq()));
						log.debug(temphist.get("chngHistSeq"));
						log.debug("최초생성 : " + temphist.get("prdtNo"));
						historyList.add(temphist);
					}
				} else {
					continue;
				}
			
				
			}
			
			for (Map<String, String> hList : historyList) {
				int prdtHistSeq = Integer.parseInt(hList.get("chngHistSeq"));
				log.debug("prdtNo : " + hList.get("prdtNo") + " / " + "bfStockQty : " + hList.get("bfStockQty") + " / "
						+ "afStockQty : " + hList.get("afStockQty") + " / " + "changeCol : " + hList.get("changeCol"));
				log.debug(hList.get("prdtOptnNo"));
				log.debug(hList.get("prdtOptnNo"));
				log.debug(hList.get("chngHistSeq"));
				// option Table Update
				interfacesBatchSqlService.updateMaseterStockNoTrx(hList.get("prdtNo"), Integer.parseInt(hList.get("bfStockQty")), hList.get("prdtOptnNo"));
				
				//			int prdtPriceHistNextSeq = pdproductGamGa.getPrdtPriceHistNextSeq()+ 1;
				// history Table Update
				interfacesBatchSqlService.insertStockHistoryNoTrx(hList.get("prdtNo"), hList.get("prdtOptnNo"), prdtHistSeq+1, hList.get("changeCol"), 
						hList.put("changeName", "총재고수량"+ " "+"주문수량"),hList.get("bfStockQty"),hList.get("afStockQty"), "자동 배치");  // 히스토리 insert
			}

	
		
		//가격연동 프로세스
		List<InterfacesChngProductErpGamga> alterErpGamGa = interfacesBatchSqlService.selectAlterErpGamga();

		for(InterfacesChngProductErpGamga interfacesAlterErpGamga : alterErpGamGa) {

			
			String vndrPrdtNo = interfacesAlterErpGamga.getVndrPrdtNo();
			String applyStartDtm = interfacesAlterErpGamga.getApplyStartDtm();
			String applyEndDtm = interfacesAlterErpGamga.getApplyEndDtm();
			int nomalAmt = interfacesAlterErpGamga.getNomalAmt();
			int sellAmt = interfacesAlterErpGamga.getSellAmt();
			
			
			
			
			InterfacesOnlineproductGamGa pdproductGamGa = interfacesBatchSqlService.selectPdProdcutGamGaList(vndrPrdtNo);
			
			
			//if(pdproductGamGa != null) {
				
				String prdtNo = pdproductGamGa.getPrdtNo();
				int prdtPriceHistNextSeq = pdproductGamGa.getPrdtPriceHistNextSeq()+ 1;
				int erpDscntRate = (interfacesAlterErpGamga.getNomalAmt() - interfacesAlterErpGamga.getSellAmt())*100/interfacesAlterErpGamga.getNomalAmt();
//				System.out.println(pdproductGamGa.getPrdtNo());
//				int erpDscntRate = (interfacesAlterErpGamga.getNomalAmt() - interfacesAlterErpGamga.getSellAmt())*100/interfacesAlterErpGamga.getNomalAmt();
				System.out.println(interfacesAlterErpGamga.getNomalAmt());
				System.out.println(interfacesAlterErpGamga.getSellAmt());
				System.out.println(erpDscntRate);
				
				interfacesBatchSqlService.insertchngGamGaNoTrx(prdtNo, prdtPriceHistNextSeq, interfacesAlterErpGamga.getNomalAmt(), interfacesAlterErpGamga.getSellAmt(), erpDscntRate);
				
				
				
			
			
		}
	}
}