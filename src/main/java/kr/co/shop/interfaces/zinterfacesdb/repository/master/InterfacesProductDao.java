package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaToErp;
import kr.co.shop.common.paging.Pageable;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesBrandToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaViewToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesInsertGiftForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesGamGaForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumNotToErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSangPumForErp;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForArs;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockForErp;



/**
 * @Desc : ErpBrand 수신
 * @FileName : ErpBrandDao
 * @Project : shop.interface
 * @Date : 2019. 02. 15
 * @Author : 김영진
 */

@Mapper
public interface InterfacesProductDao {

	/**
	 * 
	 * @Desc : 브랜드 정보 수신
	 * @Method Name : searchErpBrand
	 * @Date : 2019. 2. 26.
	 * @Author : 김영진
	 * @param interfaceErpBrand
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesBrandToErp> selectErpBrand(InterfacesBrandToErp interfaceErpBrand) throws Exception;

	/**
	 * 
	 * @Desc : 감가정보 수신
	 * @Method Name : selectErpGamGa
	 * @param sangpumCd
	 * @Date : 2019. 3. 26.
	 * @Author : 김영진
	 * @param maejangCd
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesGamGaToErp> selectErpGamGa(InterfacesGamGaToErp erpGamGa) throws Exception;
	
		
	/**
	 * 
	 * @Desc : 감가정보 수신
	 * @Method Name : selectErpGamGa
	 * @Date : 2019. 2. 26.
	 * @Author : 김영진
	 * @param erpGamGa
	 * @return
	 * @throws Exception
	 */	
	public List<InterfacesGamGaViewToErp> selectErpGamGaview(InterfacesGamGaViewToErp interfaceerpGamGaview) throws Exception;

	/**
	 * 
	 * 
	 * @Desc : 재고정보 수신
	 * @Method Name : selectErpStocks
	 * @Date : 2019. 2. 26.
	 * @Author : 김영진
	 * @param erpStock
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesStockToErp> selectErpStock(InterfacesStockToErp interfacesErpStock) throws Exception;
	

	/**
	 * 
	 * @Desc : erp상품 리스트
	 * @Method Name : searchErpSangPum
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumToErp> selectErpSangPumPaging(Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable) throws Exception;
	/*
	public List<InterfacesSangPumToErp> selectErpSangPum(InterfacesSangPumToErp erpSangPum) throws Exception;
*/
	public int selectErpSangPumCount(Pageable<InterfacesSangPumToErp, InterfacesSangPumToErp> pageable ) throws Exception;
	
	/**
	 * 
	 * @Desc :erp상품옵션정보 
	 * @Method Name : selectErpSangPumList
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param erpSangPumList
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumDetailToErp> selectErpSangPumDetail(InterfacesSangPumDetailToErp interfacesErpSangPumList) throws Exception;

	


	
	/**
	 * 
	 * @Desc : erp상품 온라인 등록 안된것
	 * @Method Name : searchErpSangPum
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param erpSangPum
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesSangPumNotToErp> selectNonErpSangPum(InterfacesSangPumNotToErp interfacesNonErpSangPum) throws Exception;
	
	/**
	 *
	 * @Desc : 중간계 사은품 등록
	 * @Method Name : insertFreeGiftToWms
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public void insertFreeGiftToErp(InterfacesInsertGiftForErp freegiftInsertErp)throws Exception;
	
	/**
	 *
	 * @Desc : 중간계 프로시저 호출
	 * @Method Name : callProcedure
	 * @Date : 2019. 2. 27.
	 * @Author : 김영진
	 * @param BrandCd
	 * @param WorkDiv
	 * @return
	 * @throws Exception
	 */

	public void updateProcedureErpBrand(InterfacesBrandToErp brandErp) throws Exception;
	

	/**
	 * 
	 * @Desc : Erp 가격정보 호출 프로시저
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param maejangcd
	 * @return
	 * @throws Exception
	 */
	
	public void updateProcedureErpGamGa(InterfacesGamGaForErp gamGaErp) throws Exception;
	
	
	/**
	 * 
	 * @Desc : Erp 상품정보 호출 프로시저
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param maejangCd
	 * @param sangPumCd
	 * @return
	 * @throws Exception
	 */
	
	public void updateProcedureErpSangPum(InterfacesSangPumForErp sangPumErp) throws Exception;
		
	/**
	 * 
	 * @Desc : Erp 재고정보 호출 프로시저
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 3. 6.
	 * @Author : 김영진
	 * @param maejangCd
	 * @param sangPumfullCd
	 * @param inputType
	 * @return
	 * @throws Exception
	 */

	public void  updateProcedureErpStock(InterfacesStockForErp stockErp) throws Exception;
	
	
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

	public void updateProcedureArsStock(InterfacesStockForArs interfacesStockArsErp) throws Exception;
	
	
	
	public void insertBrandBatch(InterfacesBrandToErp insertdpbrand)throws Exception;

	
	
	
	
	

	
	
	
}
