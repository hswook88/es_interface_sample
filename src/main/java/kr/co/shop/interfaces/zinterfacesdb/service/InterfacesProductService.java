package kr.co.shop.interfaces.zinterfacesdb.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.common.paging.Pageable;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaViewToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesInsertGiftForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumToErp;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumNotToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForArs;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForErp;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesProductDao;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : Erp상품 수신
 * @FileName : ErpBrandService
 * @Project : shop.interface
 * @Date : 2019. 02. 12
 * @Author : 김영진
 */

@Service
public class InterfacesProductService {

	@Autowired
	private InterfacesProductDao interfaceProductDao;

	/**
	 * @Desc :Erp신규 브랜드 수신
	 * @Method Name : selectErpBrand
	 * @Date : 2019. 2. 12.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesBrandToErp> selectErpBrand() throws Exception {

		return interfaceProductDao.selectErpBrand(null);
	}


	/**
	 * @Desc :Erp가격 정보 수신
	 * @Method Name : selectErpGamGaview
	 * @Date : 2019. 2. 19.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesGamGaViewToErp> selectErpGamGaview() throws Exception {

		return interfaceProductDao.selectErpGamGaview(null);
	}
	


	/**
	 * @Desc :Erp상품 리스트 수신
	 * @Method Name : selectErpSangPum
	 * @Date : 2019. 3. 19.
	 * @Author : 김영진
	 * @param sangPumCd
	 * @param sangPumNm
	 * @param brandNm
	 * @param regStartDate
	 * @param regEndDate
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumToErp> selectErpSangPum(Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable) throws Exception {
		
		InterfacesSangPumToErp erpSangPum = new InterfacesSangPumToErp();
		List<InterfacesSangPumToErp> aa = new ArrayList<InterfacesSangPumToErp>();
		
		
		aa = interfaceProductDao.selectErpSangPumPaging(pageable);
		
		return aa;
	}
	
/*
	 public  List<InterfacesSangPumToErp> selectErpSangPum(List<String>sangPumCdList, String sangPumNm, String brandNm, String regStartDate, String regEndDate) throws Exception {
		
		InterfacesSangPumToErp erpSangPum = new InterfacesSangPumToErp();
		List<InterfacesSangPumToErp> aa = new ArrayList<InterfacesSangPumToErp>();
		
		erpSangPum.setSangPumCdList(sangPumCdList);
		erpSangPum.setSangPumNm(sangPumNm);
		erpSangPum.setBrandNm(brandNm);
		erpSangPum.setRegStartDate(regStartDate);
		erpSangPum.setRegEndDate(regEndDate);
		log.debug(erpSangPum.toString());
		aa = interfaceProductDao.selectErpSangPum(erpSangPum);
		return aa;
		
	}
		*/
	 public int selectErpSangPumCount(Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable) throws Exception {

			return  interfaceProductDao.selectErpSangPumCount(pageable);
		}
	

	/**
	 * @Desc :Erp상품 상세 정보 리스트 수신
	 * @Method Name : selectErpSangPumDetail
	 * @Date : 2019. 3. 19.
	 * @Author : 김영진
	 * @param sangpumCd
	 * @param maejangCd
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumDetailToErp> selectErpSangPumDetail(String sangpumCd, String maejangCd) throws Exception {
		InterfacesSangPumDetailToErp erpSangPumList = new InterfacesSangPumDetailToErp();
		erpSangPumList.setSangpumCd(sangpumCd);
		erpSangPumList.setMaejangCd(maejangCd);
		return interfaceProductDao.selectErpSangPumDetail(erpSangPumList);
	}


	/*
	 * 정상가격정보 수신
	 */
	public List<InterfacesGamGaToErp> selectErpGamGa(String sangpumCd, String maejangCd) throws Exception{
		InterfacesGamGaToErp erpGamGa = new InterfacesGamGaToErp();
		erpGamGa.setMaejangCd(maejangCd);
		erpGamGa.setSangpumCd(sangpumCd);
		return interfaceProductDao.selectErpGamGa(erpGamGa);
		
	}

	/**
	 * 재고정보 수신
	 */
	public List<InterfacesStockToErp> selectErpStock(String sangpumCd, String maejangCd, String inputType) throws Exception {
		InterfacesStockToErp erpStock = new InterfacesStockToErp();
		erpStock.setMaejangCd(maejangCd);
		erpStock.setSangpumCd(sangpumCd);
		erpStock.setInputType(inputType);
		return interfaceProductDao.selectErpStock(erpStock);
	}

	/**
	 *  @Desc :온라인 미등록 Erp상품 리스트 수신
	 * @Method Name : selectNonErpSangPum
	 * @Date : 2019. 4. 15.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumNotToErp> selectNonErpSangPum() throws Exception {
		return interfaceProductDao.selectNonErpSangPum(null);

	}
	
	
	/**
	 * 
	 * @Desc :Erp브랜드관련 프로시저 호출
	 * @Method Name : updateProcedureErpBrand
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateProcedureErpBrandNoTrx(String brandCd, String workDiv) throws Exception {
		boolean returnflag = false;
		InterfacesBrandToErp brandErp = new InterfacesBrandToErp();
		brandErp.setBrandCd(brandCd);
		brandErp.setWorkDiv(workDiv);
		try {
			interfaceProductDao.updateProcedureErpBrand(brandErp);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * 
	 * @Desc : Erp가격관련 프로시저 호출
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateProcedureErpGamGaNoTrx(String maejangCd, String sangPumCd) throws Exception {
		boolean returnflag = false;
		InterfacesGamGaForErp gamGaErp = new InterfacesGamGaForErp();
		gamGaErp.setMaejangCd(maejangCd);
		gamGaErp.setSangPumCd(sangPumCd);
		try {
			interfaceProductDao.updateProcedureErpGamGa(gamGaErp);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * 
	 * @Desc : Erp상품관련 프로시저 호출
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateProcedureErpSangPumNoTrx(String maejangCd, String sangPumCd) throws Exception {
		boolean returnflag = false;
		InterfacesSangPumForErp sangPumErp = new InterfacesSangPumForErp();
		sangPumErp.setSangPumCd(sangPumCd);
		sangPumErp.setMaejangCd(maejangCd);
		try {
			interfaceProductDao.updateProcedureErpSangPum(sangPumErp);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * 
	 * @Desc : Erp재고관련 프로시저 호출
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateProcedureErpStockErpsNoTrx(String maejangCd, String sangPumFullCd, String inputType)
			throws Exception {
		boolean returnflag = false;
		InterfacesStockForErp stockErp = new InterfacesStockForErp();
		stockErp.setMaejangCd(maejangCd);
		stockErp.setSangPumFullCd(sangPumFullCd);
		stockErp.setInputType(inputType);
		try {
			interfaceProductDao.updateProcedureErpStock(stockErp);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : Erp 재고정보 중간계 업데이트(ARS)
	 * @Method Name : updateStockArsErp
	 * @Date : 2019. 4. 1.
	 * @Author : 김영진
	 * @param cbcd
	 * @param maejangCd
	 * @param WorkDiv
	 * @param orderCount
	 * @param sangPumFullCd
	 * @return
	 * @throws Exception
	 */
	public boolean updateStockArsErpNoTrx(String cbcd, String maejangCd, int orderCount, String sangPumFullCd)
			throws Exception {
		boolean returnflag = false;
		InterfacesStockForArs arsStock = new InterfacesStockForArs();
		arsStock.setCbcd(cbcd);
		arsStock.setMaejangCd(maejangCd);
		arsStock.setOrderCount(orderCount);
		arsStock.setSangPumFullCd(sangPumFullCd);
		try {
			interfaceProductDao.updateProcedureArsStock(arsStock);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}
	
	/**
	 *
	 * @Desc : 중간계 사은품 등록
	 * @Method Name : insertFreeGiftToWms
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtNo
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public boolean insertFreeGiftToErpNoTrx(String prdtNo)throws Exception{
		boolean returnflag = false;
		InterfacesInsertGiftForErp freegiftInsertErp = new InterfacesInsertGiftForErp();
		freegiftInsertErp.setPrdtNo(prdtNo);
		try {
			interfaceProductDao.insertFreeGiftToErp(freegiftInsertErp);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
		
	}
	
	
	
	/**
	 *
	 * @Desc : 중간계 브랜드 배치 등록
	 * @Method Name : insertFreeGiftToWms
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtNo
	 * @return 
	 * @return
	 * @throws Exception
	 */	
	public boolean insertBrandBatchNoTrx(String brandCd, String brandNm, String brandNm2, String brandCd2, String useYn)throws Exception{
		boolean returnflag = false;
		InterfacesBrandToErp insertdpbrand = new InterfacesBrandToErp();
		insertdpbrand.setBrandCd(brandCd);
		insertdpbrand.setBrandCd(brandCd2);
		insertdpbrand.setBrandNm(brandNm2);
		insertdpbrand.setBrandNm(brandNm);
		insertdpbrand.setUseYn(useYn);
		try {
			interfaceProductDao.insertBrandBatch(insertdpbrand);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}





	

	

}
