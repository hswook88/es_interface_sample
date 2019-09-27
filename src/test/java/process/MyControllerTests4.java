package process;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Hlookup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.core.sym.Name;
import com.ibm.icu.impl.duration.impl.Utils;

import ch.qos.logback.classic.pattern.Util;

import kr.co.shop.common.util.UtilsText;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineStockUpdate;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpGamga;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpSangPum;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngOnlineStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPdProduct;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStockHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineproductGamGa;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForErp;

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
public class MyControllerTests4 {

//	@Autowired
//	private InterfacesProductErpService interfacesProductErpService;

//	@Autowired
//	private InterfacesReturnProcedureService interfacesReturnProcedureService;

	@Autowired
	InterfacesBatchSqlService interfacesBatchSqlService;
	@Autowired
	InterfacesProductService interfacesProductService;

	// 브랜드 수신용
	@Test
	public void testExample() throws Exception {
//		log.debug("testtesttest {}", interfaceErpProductService);
//		assertNotNull("널 값이 아닙니다.", interfaceErpProductService.searchErpBrand());
		String[] columnList = { "PRDT_NM", "STYLE_INFO", "MNFTR_NAME", "ORG_PLACE_CODE", "PRDT_MATERL_TEXT",
				"PRDT_COLOR" };//성별구분 코드: GENDER_GBN_CODE, 티어플래그 추가 필요 : DISP_FLAG_TEXT

		List<InterfacesChngProductErpSangPum> bfSangPum = interfacesBatchSqlService.selectAlterErpSangPumList();
		List<InterfacesOnlineProductHistory> history = new ArrayList<>();
		for (InterfacesChngProductErpSangPum beforeSangPum : bfSangPum) {

			InterfacesOnlineProductHistory tempHist = null;
			
			

//			Map<InterfacesBatchHistory, String> temphist = new HashedMap();

			String sangPumCd = beforeSangPum.getSangpumCd(); // 상품코드
			String prdtName = beforeSangPum.getSangPumNm(); // 상품이름

			// String brandCd = beforeSangPum.getBrandCd(); // 브랜드 코드 중간계와 채번로직이 다름 매핑 필요

			String colorNm = "black";// beforeSangPum.getColorNm(); // 색상이름
			// 내부 코드 디테일 테이블 조회 후 색상코드 셋팅

			String wonsanji = beforeSangPum.getWonSanJi(); // 원산지
			String styleInfo = beforeSangPum.getStyle(); // 스타일
			String prdtMaterlText = beforeSangPum.getMatt(); // 소재
			String heelhight = beforeSangPum.getHeelHeight(); // 굽높이 (컬럼매칭 x 확인필요)
			String mnftrName = beforeSangPum.getManuFacturer(); // 제조사
			String lastentrydt = beforeSangPum.getLastEntryDate(); // 재입고일 현업확인후 사용안함
			String sex = beforeSangPum.getSex(); // 성별
			String importer = beforeSangPum.getImporter(); // 수입자 (컬럼매칭 x 확인필요)
			String tierFlag = beforeSangPum.getTierflag(); // AS-IS에서 사용하던 티어 구분값
			String sangPumDiv = beforeSangPum.getSangPumDiv(); // 상품구분코드 (증정품 혹은 상품 구분하기 위한 코드)
			String maejangCd = beforeSangPum.getMaejangCd();// 중간계로 돌려주기위한 매장코드 호출
			String vndrPrdtNoText = "0000021";
			String prdtNo = "1000000008";
			
			
			// 내부 조건조회 확인
			InterfacesOnlineProductDetail beforeProduct = interfacesBatchSqlService
					.selectPdProductDetailList(vndrPrdtNoText, prdtNo, colorNm, wonsanji);

//			String orgPlaceCode = beforeProduct.getOrgPlaceCode();
//			String prdtColor = beforeProduct.getPrdtColor();
//			String prdtTypeCode = beforeProduct.getPrdtTypeCode(); // 타입코드가 중간계 데이터와 다름 매핑 필요
			String rgsterNo = "admin";
			int chngHistSeq = beforeProduct.getChngHistSeq();
			
			log.debug(String.valueOf(chngHistSeq));
//			log.debug(UtilsText.concat(beforeProduct.getPrdtColor())); //ERP색상정보
//			log.debug(UtilsText.concat(beforeProduct.getPrdtNo()));		
//			log.debug(UtilsText.concat(beforeProduct.getPrdtColorCode()));	//내부테이블 변경전 정보
//			log.debug(UtilsText.concat(beforeProduct.getPrdtName()));
//			log.debug(UtilsText.concat(beforeProduct.getStyleInfo()));

			if (beforeProduct != null) {
				history = new ArrayList<>();
				// brandNo, prdtTypeCode,
//				interfacesBatchSqlService.updatePdProductErpSangPumBase(prdtName, styleInfo, mnftrName,  prdtNo, orgPlaceCode);

//				interfacesBatchSqlService.updatePdProductErpSangPumColor(prdtColor, prdtNo);
//				log.debug(UtilsText.concat(prdtName,"/", styleInfo,"/", mnftrName,"/",  prdtNo,"/", orgPlaceCode));

//				interfacesBatchSqlService.updatePdProductErpSangPumMatt(prdtMaterlText, prdtNo);

				// 상태값 변경 메서드 호출 (분산트랜잭션으로 인한 수정 필요 (리턴값 반환 필요))
				// interfacesProductService.updateProcedureErpSangPumNoTrx(maejangCd, vndrPrdtNoText);

				// 해당 컬럼에 대한 update
				// 리턴 프로시저 호출
				
				if (history.size() != 0) {
						
					for (InterfacesOnlineProductHistory hist : history) {
						 log.debug(UtilsText.concat(hist.getPrdtNo(),"/",beforeProduct.getPrdtNo()));
						 
					//	 log.debug(UtilsText.concat(hist.getChngAfterField()));

						if ((hist.getPrdtNo()).equals(beforeProduct.getPrdtNo()) ) {
							//log.debug(UtilsText.concat(prdtColor));

							

						} else {
							log.debug(String.valueOf(chngHistSeq));

							for (String name : columnList) {
								tempHist = new InterfacesOnlineProductHistory();
								
								chngHistSeq++;
								tempHist.setPrdtNo(prdtNo);
								// tempHist.setChngHistSeq(chngHistSeq);
								tempHist.setRgsterNo(rgsterNo);
								switch (name) {
								case "PRDT_NM":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("상품이름");
									tempHist.setChngBeforeField(beforeProduct.getPrdtName());
									tempHist.setChngAfterField(prdtName);

									break;

									
									
								case "STYLE_INFO":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("스타일");
									tempHist.setChngBeforeField(beforeProduct.getStyleInfo());
									tempHist.setChngAfterField(styleInfo);

									break;

								case "MNFTR_NAME":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("제조사");
									tempHist.setChngBeforeField(beforeProduct.getMnftrName());
									tempHist.setChngAfterField(mnftrName);

									break;

								case "ORG_PLACE_CODE":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("원산지");
									tempHist.setChngBeforeField(beforeProduct.getOrgPlaceCode());
									tempHist.setChngAfterField(wonsanji);

									break;

								case "PRDT_MATERL_TEXT":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("소재");
									tempHist.setChngBeforeField(beforeProduct.getPrdtMaterlText());
									tempHist.setChngAfterField(prdtMaterlText);
									break;
/*
								case "PRDT_COLOR":
									tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name);
									tempHist.setChngFieldName("색상");
									tempHist.setChngBeforeField(beforeProduct.getPrdtColor());
									tempHist.setChngAfterField(prdtColor);

									break;
									*/
								/*
								case "GENDER_GBN_CODE" : tempHist.setChngHistSeq(chngHistSeq);
									tempHist.setChngField(name); tempHist.setChngFieldName("성별구분코드");
									  
								  	tempHist.setChngAfterField(sex); 
									  	
								  	break; 
									
								case "DISP_FLAG_TEXT" :
								  	tempHist.setChngHistSeq(chngHistSeq); tempHist.setChngField(name);
								  	tempHist.setChngFieldName("전시FLAG");
								  
								  	tempHist.setChngAfterField(tierFlag); 
									  	
								  	break;
								 
								 case "LAST_ENTRY_DATE" :
						  			tempHist.setChngHistSeq(chngHistSeq); 
						  			tempHist.setChngField(name);
						  			tempHist.setChngFieldName("재입고일자");
						  
						  			tempHist.setChngAfterField(String.valueOf(lastentrydt)); 
						  	
						  			break;
								 */
								}

								history.add(tempHist);

							}

						}
						break;
					}
					// 로그 기록 정보확인
					// 로그

				} else {
					log.debug(String.valueOf(chngHistSeq));
					for (String name : columnList) {
						tempHist = new InterfacesOnlineProductHistory();

						tempHist.setPrdtNo(prdtNo);
						chngHistSeq++;
						tempHist.setRgsterNo(rgsterNo);
						log.debug(name);
						switch (name) {
						case "PRDT_NM":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("상품이름");
							tempHist.setChngBeforeField(beforeProduct.getPrdtName());
							tempHist.setChngAfterField(prdtName);

							break;

						case "STYLE_INFO":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("스타일");
							tempHist.setChngBeforeField(beforeProduct.getStyleInfo());
							tempHist.setChngAfterField(styleInfo);

							break;

						case "MNFTR_NAME":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("제조사");
							tempHist.setChngBeforeField(beforeProduct.getMnftrName());
							tempHist.setChngAfterField(mnftrName);

							break;

						case "ORG_PLACE_CODE":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("원산지");
							tempHist.setChngBeforeField(wonsanji);
							tempHist.setChngAfterField(beforeProduct.getOrgPlaceCode());

							break;

						case "PRDT_MATERL_TEXT":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("소재");
							tempHist.setChngBeforeField(beforeProduct.getPrdtMaterlText());
							tempHist.setChngAfterField(prdtMaterlText);
							break;
/*
						case "PRDT_COLOR":
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name);
							tempHist.setChngFieldName("색상");
							tempHist.setChngBeforeField(beforeProduct.getPrdtColor());
							tempHist.setChngAfterField(prdtColor);

							break;
							*/
						/*
						case "GENDER_GBN_CODE" : 
							tempHist.setChngHistSeq(chngHistSeq);
							tempHist.setChngField(name); 
							tempHist.setChngFieldName("성별구분코드");
						  
						  	tempHist.setChngAfterField(sex); 
						  	
						  	break; 
						
						case "DISP_FLAG_TEXT" :
						  	tempHist.setChngHistSeq(chngHistSeq); 
						  	tempHist.setChngField(name);
						  	tempHist.setChngFieldName("전시FLAG");
						  
						  	tempHist.setChngAfterField(tierFlag); 
						  	
						  	break;
						
						 case "LAST_ENTRY_DATE" :
						  	tempHist.setChngHistSeq(chngHistSeq); 
						  	tempHist.setChngField(name);
						  	tempHist.setChngFieldName("재입고일자");
						  
						  	tempHist.setChngAfterField(String.valueOf(lastentrydt)); 
						  	
						  	break;
						*/ 
							
						
						}

						history.add(tempHist);

					}

				}
			} else {
				continue;
			}

			// log.debug(history.toString());
			log.debug(String.valueOf(history.size()));

			for (InterfacesOnlineProductHistory inList : history) {

				
//				log.debug(beforeProduct.getPrdtName());
//				log.debug(beforeProduct.getStyleInfo());
//				log.debug(beforeProduct.getMnftrName());
//				log.debug(beforeProduct.getOrgPlaceCode());
//				log.debug(beforeProduct.getPrdtMaterlText());
//				log.debug(beforeProduct.getPrdtColor());
//				log.debug(inList.getPrdtNo());
				log.debug(String.valueOf(inList.getChngHistSeq()));
//				log.debug(inList.getChngField());
//				log.debug(inList.getChngFieldName());
//				log.debug(inList.getChngBeforeField());
//				log.debug(inList.getChngAfterField());
//				log.debug(inList.getRgsterNo());
				log.debug(inList.toString());
				// 변경이력 insert

				//interfacesBatchSqlService.insertErpProductHistoryNoTrx(inList.getPrdtNo(),inList.getChngHistSeq(),inList.getChngField(),inList.getChngFieldName(),inList.getChngBeforeField(),inList.getChngAfterField(),inList.getRgsterNo());

			}

		}

	}

}