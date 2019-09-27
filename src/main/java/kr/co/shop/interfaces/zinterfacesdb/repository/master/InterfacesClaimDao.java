package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOrderReturnToWms;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureCancel;

@Mapper
public interface InterfacesClaimDao {

	// 사은품이 없는 상품 취소
	public void updateOrderPrdtNoGiftCancel(InterfacesReturnProcedureCancel orderCancelNogift) throws Exception;

	// 사은품 포함 상품 취소
	public void updateOrderPrdtGiftCancel(InterfacesReturnProcedureCancel orderCancelgift) throws Exception;

	// 배송 회수지시
	public void updateOrderReturnProduct(InterfacesOrderReturnToWms returnProduct) throws Exception;

	// 매장픽업 회수지시
	public void updateOrderReturnProductPickUp(InterfacesOrderReturnToWms returnPickup) throws Exception;
}
