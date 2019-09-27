package kr.co.shop.interfaces.zinterfacesdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJDeliveryDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJInstruction;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJInstructionDetail;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJLtSupplyPlan;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesCJmallDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterfacesCJmallService {

	@Autowired
	InterfacesCJmallDao interfacesCJmallDao;

	public void deleteGarbageOrderNoTrx() throws Exception {
		log.debug("deleteGarbageOrder :: ");
		interfacesCJmallDao.deleteGarbageOrder();
	}

	public void insertOrder(CJInstruction instruction) throws Exception {

		for (CJInstructionDetail instructionDetail : instruction.getInstructionDetail()) {
			instructionDetail.setOrdNo(instruction.getOrdNo());

			// 주문일시를 ms-sql datetime형식으로 변경
			// 2012-12-28+09:00 -> 2013-01-04 13:53:21.327
			String convertDt = UtilsText.concat(instructionDetail.getWbCrtDt().substring(0, 10), " ",
					instructionDetail.getWbCrtDt().substring(11), ":00.000");
			instructionDetail.setWbCrtDt(convertDt);

			// 주문정보를 IF_ORDER_CJ에 insert
			interfacesCJmallDao.insertOrder(instructionDetail);

		}

	}

	public int selectLastOrderDt() throws Exception {
		return interfacesCJmallDao.selectLastOrderDt();
	}

	public void execPrOrderCJNoTrx() throws Exception {
		// CJ주문자료를 물류로 전달하는 프로시저 호출
		interfacesCJmallDao.execPrOrderCJ();
	}

	public void insertLastOrderDtmNoTrx() throws Exception {
		interfacesCJmallDao.execPrOrderCJ();
	}

	/**
	 * <pre>
	 * IF_03_05 L/T 공급계획 등록
	 * 상품별 판매에 대한 계획을 수립
	 * </pre>
	 * 
	 * @return List<CJLtSupplyPlan>
	 */
	public List<CJLtSupplyPlan> getSangpumLtSupplyPlanRenewal() throws Exception {

		// WMS의 전일 휴무여부를 조회
		if (interfacesCJmallDao.selectWmsHoliday() > 0) {
			// orderService.insertCJErrorLog(1, 305, "전일자가 물류의 휴무일로 조회되어 작업을 중단되었습니다.");
			return null;
		}

		List<CJLtSupplyPlan> ltSupplyPlans = interfacesCJmallDao.selectSangpumLtRenewal();

		return ltSupplyPlans;

	}

	public List<CJDeliveryDetail> selectNoDelivery() throws Exception {
		List<CJDeliveryDetail> deliveryDetail = interfacesCJmallDao.selectNoDelivery();

		String errorMessage = "";

		// 주문정보 유효성체크
		for (int i = 0; i < deliveryDetail.size(); i++) {

			errorMessage = "";

			if (UtilsText.isBlank(deliveryDetail.get(i).getNoDelyGb())) {
				errorMessage = "미출고등록실패 주문[" + deliveryDetail.get(i).getOrdNo() + "-"
						+ deliveryDetail.get(i).getOrdGSeq() + "-" + deliveryDetail.get(i).getOrdDSeq() + "-"
						+ deliveryDetail.get(i).getOrdWSeq() + "] 미출고사유코드가 존재하지 않습니다.";
			} else if (UtilsText.isBlank(deliveryDetail.get(i).getNoDelyDesc())) {
				errorMessage = "미출고등록실패 주문[" + deliveryDetail.get(i).getOrdNo() + "-"
						+ deliveryDetail.get(i).getOrdGSeq() + "-" + deliveryDetail.get(i).getOrdDSeq() + "-"
						+ deliveryDetail.get(i).getOrdWSeq() + "] 미출고사유상세가 존재하지 않습니다.";
			} else if (UtilsText.isBlank(deliveryDetail.get(i).getHopeDelyDate())) {
				errorMessage = "미출고등록실패 주문[" + deliveryDetail.get(i).getOrdNo() + "-"
						+ deliveryDetail.get(i).getOrdGSeq() + "-" + deliveryDetail.get(i).getOrdDSeq() + "-"
						+ deliveryDetail.get(i).getOrdWSeq() + "] 창고출고예정일이 존재하지 않습니다.";
			} else if (UtilsText.isBlank(deliveryDetail.get(i).getWbIdNo())) {
				errorMessage = "미출고등록실패 주문[" + deliveryDetail.get(i).getOrdNo() + "-"
						+ deliveryDetail.get(i).getOrdGSeq() + "-" + deliveryDetail.get(i).getOrdDSeq() + "-"
						+ deliveryDetail.get(i).getOrdWSeq() + "] 운송장식별번호가 존재하지 않습니다.";
			}

			// 유효성체크에서 걸렸다면 해당 상품 에러처리 및 로그테이블 처리
			if (errorMessage.length() > 0) {
				// interfacesCJmallDao.updateOrderErrorStatus(deliveryDetail.get(i));
				// this.insertCJErrorLog(1, 433, errorMessage);

				deliveryDetail.remove(i);
				i = i - 1;
			}

		}

		if (deliveryDetail.size() == 0) {
			// 처리대상이 없는 경우에는 정상오류 이므로 예외처리 하지 아니함.
			return null;
		}

		return deliveryDetail;
	}

	public int selectProductLtRenewalCount() {
		int returnCount = 0;
		try {
			returnCount = interfacesCJmallDao.selectProductLtRenewalCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnCount;
	}

	public int selectNoDeliveryCnt() {
		int returnCount = 0;
		try {
			returnCount = interfacesCJmallDao.selectNoDeliveryCnt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnCount;
	}

	/**
	 * <pre>
	 * IF_03_05 L/T 공급계획 등록
	 * 판매가능수량변경에 성공한 데이터를 IF_STOCK_WMS_CJ 테이블에 저장
	 * </pre>
	 * 
	 * @param ltSupplyPlan
	 * @throws Exception
	 */
	public void setLtSupplyPlanStatus(CJLtSupplyPlan ltSupplyPlan) throws Exception {
		interfacesCJmallDao.insertBackupStockWMS(ltSupplyPlan);

	}

	public void updateNoDeliveryStatus(CJDeliveryDetail deliveryDetail) throws Exception {
		interfacesCJmallDao.updateNoDeliveryStatus(deliveryDetail);
	}

	public void updateOrderErrorStatus(CJDeliveryDetail deliveryDetail) throws Exception {
		interfacesCJmallDao.updateOrderErrorStatus(deliveryDetail);
	}

	/**
	 * <pre>
	 * IF_03_04 판매가격 수정
	 * 가격변동이 실패한 단품에 대해 에러비트를 셋팅(부분처리)
	 * </pre>
	 * 
	 * @param cjUnitCd
	 */
	public void updateSangpumUnitErrorStatus(String cjUnitCd) {
		interfacesCJmallDao.updateSangpumUnitErrorStatus(cjUnitCd);

	}

}
