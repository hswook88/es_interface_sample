package kr.co.shop.interfaces.zinterfacesdb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.common.util.UtilsDate;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesDelivery;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPickupCustomer;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesPickupCustomerDtm;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcdurePickupPsblt;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureAddr;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesDeliveryDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterfacesDeliveryService {

	@Autowired
	private InterfacesDeliveryDao interfacesDeliveryDao;

	/**
	 * OPENDB에서 편의점 픽업배송 카운트 조회
	 * 
	 * @param orderNum
	 * @return
	 */
	public int getCvsPickupCountToOpenDBNoTrx(String orderNum) {
		InterfacesDelivery Deliva = new InterfacesDelivery();
		Deliva.setOrderNum(orderNum);

		int cvsPickupCount = 0;
		try {
			log.debug("a");
			cvsPickupCount = interfacesDeliveryDao.getCvsPickupCountToOpenDB(Deliva);
			log.debug("a > " + cvsPickupCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cvsPickupCount;
	}

	/**
	 * OPENDB의 CVS_PICKUP테이블에 편의점배송정보 생성
	 * 
	 * @param dlvyId
	 * @return
	 */
	public boolean insertOrderToCvsPickupOpenDBNoTrx(String dlvyId) {
		InterfacesDelivery Delivb = new InterfacesDelivery();
		Delivb.setDlvyId(dlvyId);

		boolean successFlag = false;
		try {
			interfacesDeliveryDao.insertOrderToCvsPickupOpenDB(Delivb);
			successFlag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successFlag;
	}

	/**
	 * OPENDB의 IF_ORDER에 데이터 생성
	 * 
	 * @return
	 */
	public boolean insertIforderToOpenDBNoTrx() {
		boolean successFlag = false;
		try {
			interfacesDeliveryDao.insertIforderToOpenDB();
			successFlag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successFlag;
	}

	/**
	 * OPENDB IF_ORDER에 교환배송데이터 생성
	 * 
	 * @return
	 */
	public boolean insertChangeOrderToOpenDBNoTrx() {
		boolean successFlag = false;
		try {
			interfacesDeliveryDao.insertChangeOrderToOpenDB();
			successFlag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successFlag;
	}
	
	/**
	 * 편의점 픽업배송 조회
	 * @param sendNum
	 * @return
	 * @throws Exception
	 */
	public List<InterfacesPickupCustomer> selectAblePickUpTacbaeNumber(String sendNum)throws Exception{
		InterfacesPickupCustomer pickUp = new InterfacesPickupCustomer();
		pickUp.setSendNum(sendNum);
		return interfacesDeliveryDao.selectAblePickUpTacbaeNumber(pickUp);
	}
	
	
	/**
	 * 중간계 IF_CVS_CUSTOMER테이블의 CONF_YN값 'Y'처리
	 * @param sendNum
	 * @return
	 * @throws Exception
	 */
	public boolean updatePickUpDeliveryBySendNumberNoTrx(String sendNum) throws Exception {
		boolean successFlag = false;
		List<InterfacesPickupCustomer> dtm = interfacesDeliveryDao.selectDtm();
		for (InterfacesPickupCustomer tt : dtm) {
			InterfacesPickupCustomer sendNumber = new InterfacesPickupCustomer();
			
			sendNumber.setSendNum(sendNum);
			sendNumber.setConfDtm(tt.getConfDtm());
			try {
				interfacesDeliveryDao.updatePickUpDeliveryBySendNumber(sendNumber);
				successFlag = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return successFlag;
	}
	
	/**
	 * @param addPsbltDt2 
	 * @Desc : 픽업기간 연장
	 * @Method Name : updateReturnPickupPsbltNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateExtensionPickupPsbltNoTrx(String dlvyId, int addPsbltDt ) throws Exception {
		boolean returnflag = false;
		InterfacesReturnProcdurePickupPsblt pickupPsblt = new InterfacesReturnProcdurePickupPsblt();
		pickupPsblt.setDlvyId(dlvyId);
		pickupPsblt.setAddPsbltDt(addPsbltDt);
		try {
			interfacesDeliveryDao.updateExtensionPickupPsblt(pickupPsblt);
			returnflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnflag;
	}
	
	/**
	 * @Desc : 배송 타입변경
	 * @Method Name : updateChngDlvyTypeNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateChngDlvyTypeNoTrx(String cbcd, String maejangCd, String dlvyId, String newRcvrName,String newDlvyPostNum, String newHpNum, String newHpNum2, String newHpNum3, String newTelNum, String newTelNum2, String newTelNum3, String newAddr1, String newAddr2, String dlvyMessage)throws Exception{
		boolean returnflag = false;
		InterfacesReturnProcedureAddr dlvyType = new InterfacesReturnProcedureAddr();
		dlvyType.setCbcd(cbcd);
		dlvyType.setMaejangCd(maejangCd);
		dlvyType.setDlvyId(dlvyId);
		dlvyType.setNewRcvrName(newRcvrName);
		dlvyType.setNewDlvyPostNum(newDlvyPostNum);
		dlvyType.setNewHpNum(newHpNum);
		dlvyType.setNewHpNum2(newHpNum2);
		dlvyType.setNewHpNum3(newHpNum3);
		dlvyType.setNewTelNum(newTelNum);
		dlvyType.setNewTelNum(newTelNum2);
		dlvyType.setNewTelNum(newTelNum3);
		dlvyType.setNewAddr1(newAddr1);
		dlvyType.setNewAddr2(newAddr2);
		dlvyType.setDlvyMessage(dlvyMessage);
		try {
			interfacesDeliveryDao.updateChngDlvyType(dlvyType);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}
	
	
}
