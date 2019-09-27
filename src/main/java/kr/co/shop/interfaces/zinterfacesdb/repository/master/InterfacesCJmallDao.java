package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJDeliveryDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJInstructionDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJLtSupplyPlan;

@Mapper
public interface InterfacesCJmallDao {

	public void deleteGarbageOrder() throws Exception;

	public void insertOrder(CJInstructionDetail instructionDetail) throws Exception;

	public int selectLastOrderDt() throws Exception;

	public void execPrOrderCJ() throws Exception;

	public int selectWmsHoliday() throws Exception;

	public List<CJLtSupplyPlan> selectSangpumLtRenewal() throws Exception;

	public int selectProductLtRenewalCount() throws Exception;

	public void updateSangpumUnitErrorStatus(String cjUnitCd);

	public List<CJDeliveryDetail> selectNoDelivery() throws Exception;

	public void updateNoDeliveryStatus(CJDeliveryDetail deliveryDetail) throws Exception;

	public void updateOrderErrorStatus(CJDeliveryDetail deliveryDetail) throws Exception;

	public int selectNoDeliveryCnt() throws Exception;

	public void insertBackupStockWMS(CJLtSupplyPlan ltSupplyPlan) throws Exception;

}
