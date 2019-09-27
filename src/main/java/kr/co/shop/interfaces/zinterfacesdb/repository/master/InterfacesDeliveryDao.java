package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesDelivery;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPickupCustomer;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPickupCustomerDtm;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcdurePickupPsblt;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;

@Mapper
public interface InterfacesDeliveryDao {

	public int getCvsPickupCountToOpenDB(InterfacesDelivery interfacesDelivery) throws Exception;

	public void insertOrderToCvsPickupOpenDB(InterfacesDelivery interfacesDelivery) throws Exception;

	public void insertIforderToOpenDB() throws Exception;

	public void insertChangeOrderToOpenDB() throws Exception;
	
	
	//편의점 픽업배송 편의점송장번호 수신(중간계)
	public List<InterfacesPickupCustomer> selectAblePickUpTacbaeNumber(InterfacesPickupCustomer pickUp)throws Exception;
	
	//서버시간 수신(임시 수정예정)
	public List<InterfacesPickupCustomer> selectDtm()throws Exception;
	
	//편의점 픽업 처리 결과 수신 완료 처리(중간계)
	public void updatePickUpDeliveryBySendNumber(InterfacesPickupCustomer sendNumber)throws Exception;
	
	//매장 픽업 배송 연장처리 프로시저 호출
	public void  updateExtensionPickupPsblt(InterfacesReturnProcdurePickupPsblt pickupPsblt) throws Exception;

	//배송 타입 변경 프로시저 호출
	public void updateChngDlvyType(InterfacesReturnProcedureAddr dlvyType)throws Exception;

}
