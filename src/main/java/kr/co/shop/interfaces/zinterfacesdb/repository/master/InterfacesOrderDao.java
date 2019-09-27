package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOrder;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureOptn;

@Mapper
public interface InterfacesOrderDao {

	public void insertIforderToOpenDB() throws Exception;

	public int getCvsPickupCountToOpenDB(InterfacesOrder interfacesOrder) throws Exception;

	//주소지변경
	public void updateOrderChangeAddr(InterfacesReturnProcedureAddr procedureAddr) throws Exception;
	
	//옵션변경
	public void updateOrderChangeOptn(InterfacesReturnProcedureOptn procedureOptn) throws Exception;
	
	//배송 중지 
	public void updateOrderHold(InterfacesOrder prdtorder)throws Exception;

	

}
