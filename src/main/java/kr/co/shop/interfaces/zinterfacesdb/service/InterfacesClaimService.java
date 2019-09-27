package kr.co.shop.interfaces.zinterfacesdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesOrderReturnToWms;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesReturnProcedureCancel;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesClaimDao;

@Service
public class InterfacesClaimService {

	@Autowired
	private InterfacesClaimDao interfacesClaimDao;

	/**
	 * @Desc : 주문상품 취소
	 * @Method Name : updateOrderPrdtNoGiftCancelNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderPrdtNoGiftCancelNoTrx(String cbcd, String maejangCd, String dlvyId, String itemSno,
			String sangpumFullCd, String count) throws Exception {
		boolean returnflag = false;
		InterfacesReturnProcedureCancel orderCancelNogift = new InterfacesReturnProcedureCancel();
		orderCancelNogift.setCbcd(cbcd);
		orderCancelNogift.setMaejangCd(maejangCd);
		orderCancelNogift.setDlvyId(dlvyId);
		orderCancelNogift.setItemSno(itemSno);
		orderCancelNogift.setSangPumFullCd(sangpumFullCd);
		orderCancelNogift.setCount(count);
		try {
			interfacesClaimDao.updateOrderPrdtNoGiftCancel(orderCancelNogift);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : 주문상품 취소 사은품 포함
	 * @Method Name : updateOrderPrdtGiftCancelNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderPrdtGiftCancelNoTrx(String cbcd, String maejangCd, String dlvyId, String itemSno,
			String sangpumFullCd, String count) throws Exception {
		boolean returnflag = false;
		InterfacesReturnProcedureCancel orderCancelgift = new InterfacesReturnProcedureCancel();
		orderCancelgift.setCbcd(cbcd);
		orderCancelgift.setMaejangCd(maejangCd);
		orderCancelgift.setDlvyId(dlvyId);
		orderCancelgift.setItemSno(itemSno);
		orderCancelgift.setSangPumFullCd(sangpumFullCd);
		orderCancelgift.setCount(count);
		try {
			interfacesClaimDao.updateOrderPrdtNoGiftCancel(orderCancelgift);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : 주문상품 회수지시(택배배송)
	 * @Method Name : updateOrderReturnProductNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderReturnProductNoTrx(String cbcd, String maejangCd, String dlvyId, String asReturnConfirm)
			throws Exception {
		boolean returnflag = false;
		InterfacesOrderReturnToWms returnProduct = new InterfacesOrderReturnToWms();
		returnProduct.setCbcd(cbcd);
		returnProduct.setMaejangCd(maejangCd);
		returnProduct.setDlvyId(dlvyId);
		returnProduct.setAsReturnConfirm(asReturnConfirm);
		try {
			interfacesClaimDao.updateOrderReturnProduct(returnProduct);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}

	/**
	 * @Desc : 주문상품 회수지시(매장픽업 및 편의점 픽업)
	 * @Method Name : updateOrderReturnProductNoTrx
	 * @Date : 2019. 4. 18.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public boolean updateOrderReturnProductPickUpNoTrx(String cbcd, String maejangCd, String dlvyId, String postNum,
			String addr, String addrDtl, String hdphnNum, String rcvrName, String prdtCode, String optionValue)
			throws Exception {
		boolean returnflag = false;
		InterfacesOrderReturnToWms returnPickup = new InterfacesOrderReturnToWms();
		returnPickup.setCbcd(cbcd);
		returnPickup.setMaejangCd(maejangCd);
		returnPickup.setDlvyId(dlvyId);
		returnPickup.setPostNum(postNum);
		returnPickup.setAddr(addr);
		returnPickup.setAddrDtl(addrDtl);
		returnPickup.setHdphnNum(hdphnNum);
		returnPickup.setRcvrName(rcvrName);
		returnPickup.setPrdtCode(prdtCode);
		returnPickup.setOptionValue(optionValue);
		try {
			interfacesClaimDao.updateOrderReturnProductPickUp(returnPickup);
			returnflag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnflag;
	}
}