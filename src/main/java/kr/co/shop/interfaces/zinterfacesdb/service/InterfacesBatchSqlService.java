package kr.co.shop.interfaces.zinterfacesdb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineStockUpdate;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpGamga;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpSangPum;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngOnlineStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductGamGaHistroy;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPdProduct;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStockHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineproductGamGa;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesBatchSqlDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterfacesBatchSqlService {

	@Autowired
	InterfacesBatchSqlDao interfacesBatchSqlDao;

	/**
	 * @Desc : 중간계 erp상품 중간계 재고 조회
	 * @Method Name : selectAlterErpStock
	 * @Date : 2019. 4. 1.
	 * @Author : 김영진
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesChngProductErpStock> selectAlterErpStock() throws Exception {
		return interfacesBatchSqlDao.selectAlterErpStock(null);
	}

	
	/**
	 * @Desc : 중간계 erp상품 내부 재고 조회
	 * @Method Name : selectPdProductErpList
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param cbcd
	 * @param prdtOptnNo
	 * @param vndrPrdtNoText
	 * @return
	 * @throws Exception
	 */
	public InterfacesChngOnlineStock selectPdProductErpList(String cbcd, String prdtOptnNo, String vndrPrdtNoText)
			throws Exception {
		InterfacesChngOnlineStock Liststock = new InterfacesChngOnlineStock();
		Liststock.setPrdtOptnNo(prdtOptnNo);
		Liststock.setCbcd(cbcd);
		Liststock.setVndrPrdtNoText(vndrPrdtNoText);
		return interfacesBatchSqlDao.selectPdProductErpList(Liststock);
	}

	
	/**
	 * @Desc : 중간계 erp상품 내부 재고 UPDATE
	 * @Method Name : updateAlterStockNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtOptnNo
	 * @param vndrPrdtNoText
	 * @param stockQty
	 * @param stockGbnCode
	 * @return
	 * @throws Exception
	 */
	public boolean updateAlterStockNoTrx(String prdtOptnNo, String vndrPrdtNoText, int stockQty, String stockGbnCode)
			throws Exception {
		boolean returnflag = false;
		InterfacesOnlineStockUpdate updateStock = new InterfacesOnlineStockUpdate();
		updateStock.setPrdtOptnNo(prdtOptnNo);
		updateStock.setStockQty(stockQty);
		updateStock.setVndrPrdtNoText(vndrPrdtNoText);
		updateStock.setStockGbnCode(stockGbnCode);
		try {
			interfacesBatchSqlDao.updateAlterStock(updateStock);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	
	/**
	 * @Desc : 중간계 erp상품 내부 재고 이력 INSERT
	 * @Method Name : insertStockHistoryNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtNo
	 * @param prdtOptnNo
	 * @param chngHistSeq
	 * @param changeCol
	 * @param changeName
	 * @param bfStockQty
	 * @param afStockQty
	 * @param rgstNo
	 * @return
	 * @throws Exception
	 */
	public boolean insertStockHistoryNoTrx(String prdtNo, String prdtOptnNo, int chngHistSeq, String changeCol,
			String changeName, String bfStockQty, String afStockQty, String rgstNo) throws Exception {
		boolean returnflag = false;
		InterfacesOnlineProductStockHistory insertHist = new InterfacesOnlineProductStockHistory();
		insertHist.setPrdtNo(prdtNo);
		insertHist.setPrdtOptnNo(prdtOptnNo);
		insertHist.setChngHistSeq(chngHistSeq);
		insertHist.setChangeCol(changeCol);
		insertHist.setChangeName(changeName);
		insertHist.setBfStockQty(bfStockQty);
		insertHist.setAfStockQty(afStockQty);
		insertHist.setRgstNo(rgstNo);
		try {
			interfacesBatchSqlDao.insertStockHistory(insertHist);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	// 재고 마스터 테이블 업데이트
	/**
	 * @Desc : 중간계 erp상품 재고 마스터테이블 UPDATE
	 * @Method Name : updateMaseterStockNoTrx
	 * @Date : 2019. 3. 25.
	 * @Author : 김영진
	 * @param prdtNo
	 * @param totalstockQty
	 * @param prdtOptnNo
	 * @throws Exception
	 */
	public void updateMaseterStockNoTrx(String prdtNo, int totalstockQty, String prdtOptnNo) throws Exception {
		InterfacesOnlineProductStock masterStock = new InterfacesOnlineProductStock();
		masterStock.setPrdtNo(prdtNo);
		masterStock.setTotalstockQty(totalstockQty);
		masterStock.setPrdtOptnNo(prdtOptnNo);
		interfacesBatchSqlDao.updateMaseterStock(masterStock);
	}
	
	/**
	 * @Desc : 중간계 erp상품 중간계 가격 조회
	 * @Method Name : selectAlterErpGamga
	 * @Date : 2019. 4. 1.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesChngProductErpGamga> selectAlterErpGamga() throws Exception {
		return interfacesBatchSqlDao.selectAlterErpGamga(null);
	}

	/**
	 * @Desc : 중간계 erp상품 가격 내부 조회
	 * @Method Name : selectPdProdcutGamGaList
	 * @Date : 2019. 4. 1.
	 * @Author : 김영진
	 * @param vndrPrdtNo
	 * @return
	 * @throws Exception
	 */
	public InterfacesOnlineproductGamGa selectPdProdcutGamGaList(String vndrPrdtNo) throws Exception {
		InterfacesOnlineproductGamGa pdGamGaErp = new InterfacesOnlineproductGamGa();
		pdGamGaErp.setVndrPrdtNo(vndrPrdtNo);
		return interfacesBatchSqlDao.selectPdProdcutGamGaList(pdGamGaErp);
	}

	/**
	 * @Desc : 중간계 erp상품 가격 INSERT
	 * @Method Name : insertchngGamGaNoTrx
	 * @Date : 2019. 4. 1.
	 * @Author : 김영진
	 * @param prdtNo
	 * @param prdtPriceHistNextSeq
	 * @param normalAmt
	 * @param sellAmt
	 * @param erpDscntRate
	 * @return
	 * @throws Exception
	 */
	public boolean insertchngGamGaNoTrx(String prdtNo, int prdtPriceHistNextSeq, int normalAmt, int sellAmt,
			int erpDscntRate) throws Exception {
		boolean returnflag = false;
		InterfacesOnlineProductGamGaHistroy chngGamGa = new InterfacesOnlineProductGamGaHistroy();
		chngGamGa.setPrdtNo(prdtNo);
		chngGamGa.setNormalAmt(normalAmt);
		chngGamGa.setSellAmt(sellAmt);
		chngGamGa.setErpDscntRate(erpDscntRate);
		chngGamGa.setPrdtPriceHistNextSeq(prdtPriceHistNextSeq);
		try {
			interfacesBatchSqlDao.insertchngGamGa(chngGamGa);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;

	}

	
	/**
	 * @Desc : 수정상품 리스트 중간계 조회
	 * @Method Name : selectAlterErpSangPumList
	 * @Date : 2019. 4. 8.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesChngProductErpSangPum> selectAlterErpSangPumList() throws Exception {
		return interfacesBatchSqlDao.selectAlterErpSangPumList();

	}

	/**
	 * @Desc : 수정상품 리스트 내부 조회
	 * @Method Name : selectPdProductDetailList 
	 * @Date : 2019. 4. 8.
	 * @Author : 김영진
	 * @param vndrPrdtNoText
	 * @param prdtNo
	 * @param colorNm
	 * @param orgPlaceCode
	 * @return
	 * @throws Exception
	 */
	public InterfacesOnlineProductDetail selectPdProductDetailList(String vndrPrdtNoText, String prdtNo, String colorNm,
			String orgPlaceCode) throws Exception {
		InterfacesOnlineProductDetail chngDetail = new InterfacesOnlineProductDetail();
		chngDetail.setVndrPrdtNoText(vndrPrdtNoText);
		chngDetail.setPrdtNo(prdtNo);
		chngDetail.setPrdtDtlColor(colorNm);
		chngDetail.setOrgPlaceCode(orgPlaceCode);
		return interfacesBatchSqlDao.selectPdProductDetailList(chngDetail);
	}

	// 마스터 테이블의 기본정보 update String brandNo, String prdtTypeCode, :중간계와 온라인의 브랜드 코드가
	// 다름
	/**
	 * @Desc : 수정상품 리스트 마스터테이블 UPDATE
	 * @Method Name : updatePdProductErpSangPumBaseNoTrx
	 * @Date : 2019. 4. 8.
	 * @Author : 김영진
	 * @param prdtName
	 * @param styleInfo
	 * @param mnftrName
	 * @param prdtNo
	 * @param orgPlaceCode
	 * @return
	 * @throws Exception
	 */
	public boolean updatePdProductErpSangPumBaseNoTrx(String prdtName, String styleInfo, String mnftrName,
			String prdtNo, String orgPlaceCode) throws Exception {
		boolean returnflag = false;
		InterfacesOnlineProductDetail sangPumBase = new InterfacesOnlineProductDetail();
		sangPumBase.setPrdtName(prdtName);
//		sangPumBase.setBrandNo(brandNo);
		sangPumBase.setStyleInfo(styleInfo);
		sangPumBase.setMnftrName(mnftrName);
//		sangPumBase.setPrdtTypeCode(prdtTypeCode);
		sangPumBase.setPrdtNo(prdtNo);
		sangPumBase.setOrgPlaceCode(orgPlaceCode);
		try {
			interfacesBatchSqlDao.updatePdProductErpSangPumBase(sangPumBase);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;

	}

	//색상정보 업데이트
	/**
	 * @Desc : 색상 수정 상품 리스트 색상테이블 UPDATE
	 * @Method Name : updatePdProductErpSangPumColorNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtColor
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public boolean updatePdProductErpSangPumColorNoTrx(String prdtColor, String prdtNo) throws Exception {

		InterfacesOnlineProductDetail sangPumColor = new InterfacesOnlineProductDetail();
		sangPumColor.setPrdtColor(prdtColor);
		sangPumColor.setPrdtNo(prdtNo);
		boolean returnflag = false;
		try {
			interfacesBatchSqlDao.updatePdProductErpSangPumColor(sangPumColor);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : 소재변경정보 UPDATE
	 * @Method Name : updatePdProductErpSangPumMattNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtMaterlText
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public boolean updatePdProductErpSangPumMattNoTrx(String prdtMaterlText, String prdtNo) throws Exception {
		boolean returnflag = false;
		InterfacesOnlineProductDetail sangPumAdd = new InterfacesOnlineProductDetail();
		sangPumAdd.setPrdtMaterlText(prdtMaterlText);
		sangPumAdd.setPrdtNo(prdtNo);
		try {
			interfacesBatchSqlDao.updatePdProductErpSangPumMatt(sangPumAdd);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : 상품변경정보이력 INSERT
	 * @Method Name : insertErpProductHistoryNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtNo
	 * @param chngHistSeq
	 * @param chngField
	 * @param chngFieldName
	 * @param chngBeforeField
	 * @param chngAfterField
	 * @param rgsterNo
	 * @return
	 * @throws Exception
	 */
	public boolean insertErpProductHistoryNoTrx(String prdtNo, int chngHistSeq, String chngField, String chngFieldName,
			String chngBeforeField, String chngAfterField, String rgsterNo) throws Exception {
		boolean returnflag = false;
		InterfacesOnlineProductHistory productHistory = new InterfacesOnlineProductHistory();
		productHistory.setPrdtNo(prdtNo);
		productHistory.setChngHistSeq(chngHistSeq);
		productHistory.setChngField(chngField);
		productHistory.setChngFieldName(chngFieldName);
		productHistory.setChngBeforeField(chngBeforeField);
		productHistory.setChngAfterField(chngAfterField);
		productHistory.setRgsterNo(rgsterNo);
		try {
			interfacesBatchSqlDao.insertErpProductHistory(productHistory);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	// 주문건수 초기화

	// 주문건수 조회 로직
	 public List<InterfacesOnlineProductStock> selectOrderCount()throws Exception{
		 return interfacesBatchSqlDao.selectOrderCount(null);

	 }

	// 주문 건수 중간계 update
	// public void updateOrderCountARS()throws Exception{}
	public boolean sampleUpdateStockOrderCountNoTrx(int orderCount, String sangPumFullCd, String maejangCd, String cbcd)
			throws Exception {
		boolean returnflag = false;
		InterfacesStockToErp ss = new InterfacesStockToErp();
		ss.setOrderCount(orderCount);
		ss.setSangPumFullCd(sangPumFullCd);
		ss.setMaejangCd(maejangCd);
		ss.setCbcd(cbcd);
		try {
			interfacesBatchSqlDao.sampleUpdateStockOrderCount(ss);
			returnflag = true;
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		return returnflag;
	}

	
	//중간계 상태값 변경
	public boolean sampleUpdateStockNoTrx(int orderCount, String sangPumFullCd, String maejangCd, String cbcd) {
		boolean returnflag = false;
		InterfacesStockToErp sp = new InterfacesStockToErp();
		sp.setCbcd(cbcd);
		sp.setMaejangCd(maejangCd);
		sp.setSangPumFullCd(sangPumFullCd);
		try {
			interfacesBatchSqlDao.sampleUpdateStockOrderCount(sp);
			returnflag = true;
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		return returnflag;
	}
		
	

	// 내부 주문 카은트 갱신(PD_PRODUCT_OPTION_STOCK)
	// public void updateOrderCountStock()throws Exception{}

	// 내부 주문 카은트 갱신(PD_PRODUCT_OPTION)
	// public void updateOrderCountMaster()throws Exception{}
	
}
