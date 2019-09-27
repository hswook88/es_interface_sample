package kr.co.shop.interfaces.zinterfacesdb.service;

import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOrder;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureOptn;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesOrderDao;



@Service
public class InterfacesOrderService {

	private InterfacesOrderDao interfacesOrderDao;

	/**
	 * @Desc : OPENDB의 IF_ORDER에 데이터 생성
	 * @Method Name : insertIforderToOpenDB
	 * @Date : 2019. 4. 1.
	 * @Author : 백인천
	 * @throws Exception
	 */
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertIforderToOpenDB() throws Exception {

		interfacesOrderDao.insertIforderToOpenDB();

	}

	/**
	 * @Desc : OPENDB의 편의점픽업 테이블에서 해당 주문번호의 주문건수를 카운트한다.
	 * @Method Name : getCvsPickupCountToOpenDB
	 * @Date : 2019. 4. 1.
	 * @Author : 백인천
	 * @param orderNum
	 * @return
	 * @throws Exception
	 */
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public int getCvsPickupCountToOpenDB(String orderNum) throws Exception {

		InterfacesOrder ifOrder = new InterfacesOrder();
		ifOrder.setOrderNum(orderNum);

		int returnCvsPickupCount = interfacesOrderDao.getCvsPickupCountToOpenDB(ifOrder);

		return returnCvsPickupCount;

	}
	
	
	/**
	 * 
	 * @Desc : 주문 배송지 변경
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderChangeAddrNoTrx(String cbcd, String dlvyId, String dlvyMessage, String maejangCd,
			String newAddr1, String newAddr2, String newDlvyPostNum, String newHpNum, String newHpNum2,
			String newHpNum3, String newRcvrName, String newTelNum, String newTelNum2, String newTelNum3)
			throws Exception {
		boolean returnflag = false;
		InterfacesReturnProcedureAddr procedureAddr = new InterfacesReturnProcedureAddr();
		procedureAddr.setCbcd(cbcd);
		procedureAddr.setDlvyId(dlvyId);
		procedureAddr.setDlvyMessage(dlvyMessage);
		procedureAddr.setMaejangCd(maejangCd);
		procedureAddr.setNewAddr1(newAddr1);
		procedureAddr.setNewAddr2(newAddr2);
		procedureAddr.setNewDlvyPostNum(newDlvyPostNum);
		procedureAddr.setNewHpNum(newHpNum);
		procedureAddr.setNewHpNum2(newHpNum2);
		procedureAddr.setNewHpNum3(newHpNum3);
		procedureAddr.setNewRcvrName(newRcvrName);
		procedureAddr.setNewTelNum(newTelNum);
		procedureAddr.setNewTelNum2(newTelNum2);
		procedureAddr.setNewTelNum3(newTelNum3);
		try {
			interfacesOrderDao.updateOrderChangeAddr(procedureAddr);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnflag;
	}

	/**
	 * 
	 * @Desc : 주문 옵션 변경
	 * @Method Name : wmsErpGamGas
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderChangeOptnNoTrx(String cbcd, String dlvyId, String itemSno, String maejangCd,
			String sangPumFullCd1, String sangPumFullCd2) throws Exception {
		boolean returnflag = false;
		InterfacesReturnProcedureOptn procedureOptn = new InterfacesReturnProcedureOptn();
		procedureOptn.setCbcd(cbcd);
		procedureOptn.setDlvyId(dlvyId);
		procedureOptn.setItemSno(itemSno);
		procedureOptn.setMaejangCd(maejangCd);
		procedureOptn.setSangPumFullCd1(sangPumFullCd1);
		procedureOptn.setSangPumFullCd2(sangPumFullCd2);
		try {
			interfacesOrderDao.updateOrderChangeOptn(procedureOptn);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}
	
	
	/**
	 * 
	 * @Desc : 주문 배송 중단
	 * @Method Name : updateOrderHoldNoTrx
	 * @Date : 2019. 6. 13.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderHoldNoTrx(String cbcd, String maejangCd, String dlvyId) throws Exception {
		boolean returnflag = false;
		InterfacesOrder prdtorder = new InterfacesOrder();
		prdtorder.setCbcd(cbcd);
		prdtorder.setMaejangCd(maejangCd);
		prdtorder.setDlvyId(dlvyId);
				
		try {
			interfacesOrderDao.updateOrderHold(prdtorder);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}
	
	
	
	
	
}
