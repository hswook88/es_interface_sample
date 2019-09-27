package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineStockUpdate;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpGamga;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngProductErpSangPum;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesChngOnlineStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductGamGaHistroy;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStockHistory;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineProductStock;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOnlineproductGamGa;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesStockToErp;


@Mapper
public interface InterfacesBatchSqlDao {
	
	
	//중간계 재고 조회	
	public List<InterfacesChngProductErpStock> selectAlterErpStock(InterfacesChngProductErpStock interfacesAlterErpStock) throws Exception;

	//온라인 재고 조회
	public InterfacesChngOnlineStock selectPdProductErpList(InterfacesChngOnlineStock Liststock) throws Exception;
	
	//재고변동 업데이트
	public List<InterfacesOnlineStockUpdate> updateAlterStock(InterfacesOnlineStockUpdate updateStock) throws Exception;

	//재고 변동 이력 insert
	public void insertStockHistory(InterfacesOnlineProductStockHistory insertHist) throws Exception;
	
	//재고 마스터 테이블에 업데이트
	public void updateMaseterStock(InterfacesOnlineProductStock masterStock)throws Exception;

	
	
	//가격정보 조회(중간계)
	public List<InterfacesChngProductErpGamga> selectAlterErpGamga(InterfacesChngProductErpGamga interfacesAlterErpGamga) throws Exception;
	
	//가격정보 조회(내부)
	public InterfacesOnlineproductGamGa selectPdProdcutGamGaList(InterfacesOnlineproductGamGa pdGamGaErp) throws Exception;

	//가격정보 insert
	public void insertchngGamGa(InterfacesOnlineProductGamGaHistroy chngGamGa)throws Exception;

	
	
	
	//수정대상 상품 중간계 조회
	public List<InterfacesChngProductErpSangPum> selectAlterErpSangPumList()throws Exception;

	//수정대상상품 내부 조회
	public InterfacesOnlineProductDetail selectPdProductDetailList(InterfacesOnlineProductDetail chngDetail)throws Exception;

	//상품 마스터 정보 update
	public void updatePdProductErpSangPumBase(InterfacesOnlineProductDetail sangPumBase)throws Exception;
	
	//상품 색상 update
	public void updatePdProductErpSangPumColor(InterfacesOnlineProductDetail sangPumColor)throws Exception;
	
	//상품정보고시 정보 update
	public void updatePdProductErpSangPumMatt(InterfacesOnlineProductDetail sangPumMatt)throws Exception;

	//이력정보 insert
	public void insertErpProductHistory(InterfacesOnlineProductHistory productHistory)throws Exception;

	
	
	//주문건수 초기화
	
	//주문건수 조회 로직
	public List<InterfacesOnlineProductStock> selectOrderCount(InterfacesOnlineProductStock interfacesOnlineProductStock)throws Exception;
	
	//주문 건수 중간계 update
	public void sampleUpdateStockOrderCount(InterfacesStockToErp ss)throws Exception;
	
	//내부 주문 카은트 갱신(PD_PRODUCT_OPTION_STOCK)
	//public void updateOrderCountStock()throws Exception;
	
	//내부 주문 카은트 갱신(PD_PRODUCT_OPTION)
	//public void updateOrderCountMaster()throws Exception;
	














 


	
	
}
