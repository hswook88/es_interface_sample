package kr.co.shop.interfaces.zinterfacesdb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import kr.co.shop.interfaces.common.cjmall.UtilsCJmall;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJLtSupplyPlan;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJOrderInput;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJReq0305;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJReq0411;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJReq0413;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJRes0305;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJRes0411;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJRes0413;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource(value = { "classpath:interfaces-${spring.profiles.active}.properties" })
public class InterfacesCJmallHandlerService {

	@Autowired
	private Environment env;

	@Autowired
	InterfacesCJmallService interfacesCJmallService;

	// CJ몰 인터페이스 설정
	private String URL = "";
	private String charset = "";
	private String logDir = "";

	@PostConstruct
	public void init() {
		URL = env.getProperty("interfaces.cjmall.api.url");
		env.getProperty("interfaces.cjmall.api.defaultSiteCharSet");
		logDir = env.getProperty("interfaces.cjmall.api.logDir");
	}

	/**
	 * L/T 공급계획 등록
	 * 
	 * @param model
	 */
	public void productLtSupplyPlan(ModelMap model) {

		log.debug("model {}", model);

		// orderService.insertCJErrorLog(1, 305, "call");

		try {
			/**************************************************
			 * DB에서 송신할 자료 조회
			 **************************************************/
			InterfacesCJReq0305 req0305 = new InterfacesCJReq0305();
			req0305.setLtSupplyPlans(interfacesCJmallService.getSangpumLtSupplyPlanRenewal());
			if (req0305.getLtSupplyPlans() == null) {
				return;
			}
			log.debug("DB에서 송신할 자료 조회 완료");

			/**************************************************
			 * XML 조립
			 **************************************************/
			String reqXml = UtilsCJmall.createXML(req0305, charset, logDir);
			log.debug("XML조립완료");

			/**************************************************
			 * 인터페이스 접속(송수신)
			 **************************************************/
			String returnXML = UtilsCJmall.transferXML(reqXml, URL, charset);
			log.debug("인터페이스 접속(송수신) 완료");

			/**************************************************
			 * XML 분석
			 **************************************************/
			InterfacesCJRes0305 res0305 = new InterfacesCJRes0305();
			res0305 = (InterfacesCJRes0305) UtilsCJmall.parsingXML(returnXML, res0305, logDir);
			log.debug("XML 분석 완료");

			/**************************************************
			 * DB에 수신내용 반영
			 **************************************************/
			if (res0305.getErrorMsg() == null) {

				// 처리결과를 처리하기 위한 변수
				CJLtSupplyPlan ltSupplyPlan = null;
				List<String> unidCdList = new ArrayList<String>();

				for (int i = 0; i < res0305.getLtSupplyPlans().size(); i++) {

					ltSupplyPlan = null;

					// 처리결과를 처리하기 위해 수신된 unitCd를 토대로 송신모델 검색
					for (CJLtSupplyPlan item : req0305.getLtSupplyPlans()) {
						if (item.getUnitCd().equals(res0305.getLtSupplyPlans().get(i).getLtSupplyPlan().getUnitCd())) {
							ltSupplyPlan = item;
							break;
						}
					}

					// 상품별로 작업성공과 실패에 따라 작업분기
					if (res0305.getLtSupplyPlans().get(i).getIfResult().getSuccessYn().equals("true")) {
						// 처리완료
						interfacesCJmallService.setLtSupplyPlanStatus(ltSupplyPlan);

					} else {
						// 부분실패에 대한 처리
						interfacesCJmallService.updateSangpumUnitErrorStatus(
								res0305.getLtSupplyPlans().get(i).getLtSupplyPlan().getUnitCd());
						// orderService.insertCJErrorLog(1, 305, "[" + ltSupplyPlan.getSANGPUMFULLCD() +
						// "] 판매가능수량등록 처리실패:";

					}
				}

				log.debug("DB에 수신내용 반영 완료");
			} else {
				// 작업전체실패시 처리
				// orderService.insertCJErrorLog(1, 305, "작업전체실패: " + res0305.getErrorMsg());
			}

			// 모든 작업이 정상적으로 처리
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			// 오류로 인한 작업실패에 대한 디비 처리
			// orderService.insertCJErrorLog(2, 305, Utils.getExceptionTraceToString(e));
		}

	}

	/**
	 * 주문/출하지시 조회 (직택배)
	 * 
	 * @param model
	 */
	public void getOrderDirect(ModelMap model) {

		log.debug("model {}", model);

		try {

			/**************************************************
			 * DB에서 송신할 자료 조회
			 **************************************************/
			InterfacesCJReq0411 req0411 = new InterfacesCJReq0411();
			CJOrderInput orderInput = new CJOrderInput();
			orderInput.setInstructionCls("1"); // 1:출고 2:취소 3:판매완료
			orderInput
					.setWbCrtDt(model.get("orderDt").equals("") ? new SimpleDateFormat("yyyy-MM-dd").format(new Date())
							: (String) model.get("orderDt"));
			req0411.setOrderInput(orderInput);
			log.debug("DB에서 송신할 자료 조회 완료");

			/**************************************************
			 * XML 조립
			 **************************************************/
			String reqXml = UtilsCJmall.createXML(req0411, charset, logDir);
			log.debug("XML조립완료");

			/**************************************************
			 * 인터페이스 접속(송수신)
			 **************************************************/
			String returnXML = UtilsCJmall.transferXML(reqXml, URL, charset);
			log.debug("인터페이스 접속(송수신) 완료");

			/**************************************************
			 * XML 분석
			 **************************************************/
			InterfacesCJRes0411 res0411 = new InterfacesCJRes0411();
			res0411 = (InterfacesCJRes0411) UtilsCJmall.parsingXML(returnXML, res0411, logDir);
			log.debug("XML 분석 완료");

			/**************************************************
			 * DB에 수신내용 반영
			 **************************************************/
			if (res0411.getErrorMsg() == null) {

				for (int i = 0; i < res0411.getInstruction().size(); i++) {
					// 주문조회건만 내려오므로 부분실패가 없음.
					interfacesCJmallService.insertOrder(res0411.getInstruction().get(i));
				}

				log.debug("DB에 수신내용 반영 완료");

			} else {
				log.debug("=-= 실패전문 =" + res0411.getErrorMsg());
				// 실패전문에 대한 처리
				if (!res0411.getErrorMsg().equals("검색된 데이터가 없습니다.")) {
					// orderService.insertCJErrorLog(1, 411, res0411.getErrorMsg());
				}
			}

			// 모든 작업이 정상적으로 처리
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			// 오류로 인한 작업실패에 대한 디비 처리
			// orderService.insertCJErrorLog(2, 411, Utils.getExceptionTraceToString(e));
		}

	}

	/**
	 * 미배송정보 등록
	 * 
	 * @param model
	 */
	public void setNoDeliveryDirect(ModelMap model) {

		// orderService.insertCJErrorLog(1, 413, "call");

		try {

			/**************************************************
			 * DB에서 송신할 자료 조회
			 **************************************************/
			InterfacesCJReq0413 req0413 = new InterfacesCJReq0413();
			req0413.setDeliveryDetail(interfacesCJmallService.selectNoDelivery());
			if (req0413.getDeliveryDetail() == null) {
				return;
			}
			log.debug("DB에서 송신할 자료 조회 완료");

			/**************************************************
			 * XML 조립
			 **************************************************/
			String reqXml = UtilsCJmall.createXML(req0413, charset, logDir);
			log.debug("XML조립완료");

			/**************************************************
			 * 인터페이스 접속(송수신)
			 **************************************************/
			String returnXML = UtilsCJmall.transferXML(reqXml, URL, charset);
			log.debug("인터페이스 접속(송수신) 완료");

			/**************************************************
			 * XML 분석
			 **************************************************/
			InterfacesCJRes0413 res0413 = new InterfacesCJRes0413();
			res0413 = (InterfacesCJRes0413) UtilsCJmall.parsingXML(returnXML, res0413, logDir);
			log.debug("XML 분석 완료");

			/**************************************************
			 * DB에 수신내용 반영
			 **************************************************/
			if (res0413.getErrorMsg() == null) {
				for (int i = 0; i < res0413.getDeliverys().size(); i++) {
					// 상품별로 작업성공과 실패에 따라 작업분기
					if (res0413.getDeliverys().get(i).getIfResult().getSuccessYn().equals("true")) {
						interfacesCJmallService.updateNoDeliveryStatus(res0413.getDeliverys().get(i).getBeNotYet());
					} else {
						// 부분실패에 대한 처리
						log.debug("=-= 등록실패메시지 =" + res0413.getDeliverys().get(i).getIfResult().getErrorMsg());
						interfacesCJmallService.updateOrderErrorStatus(res0413.getDeliverys().get(i).getBeNotYet());
						// orderService.insertCJErrorLog(1, 413,
						// "주문[" + res0413.getDeliverys().get(i).getBeNotYet().getOrdNo() + "-"
						// + res0413.getDeliverys().get(i).getBeNotYet().getOrdGSeq() + "-"
						// + res0413.getDeliverys().get(i).getBeNotYet().getOrdDSeq() + "-"
						// + res0413.getDeliverys().get(i).getBeNotYet().getOrdWSeq() + "] "
						// + res0413.getDeliverys().get(i).getIfResult().getErrorMsg());
					}
				}
				log.debug("DB에 수신내용 반영 완료");
			} else {
				// 작업전체실패시 처리
				// orderService.insertCJErrorLog(1, 413, "작업전체실패 " + res0413.getErrorMsg());
			}

			// 모든 작업이 정상적으로 처리
		} catch (Exception e) {

			log.error(e.getMessage(), e);

			// 오류로 인한 작업실패에 대한 디비 처리
			// orderService.insertCJErrorLog(2, 413, Utils.getExceptionTraceToString(e));
		}

	}

	/**
	 * <pre>
	 * CJ몰 연동 인터페이스
	 * 공통 - 처리대상 건수조회
	 * 배치작업시 일정한 건수로 반복수행을 하기위한 작업대상건수 조회용도
	 * </pre>
	 * 
	 * @param intfaceNo - 인터페이스번호
	 * @return 처리대상건수
	 * @throws Exception
	 */
	public int productNumber(int intfaceNo) {

		int count = 0;

		// 인터페이스 번호
		switch (intfaceNo) {
		case 305:
			count = interfacesCJmallService.selectProductLtRenewalCount();
			break;
		case 413:
			count = interfacesCJmallService.selectNoDeliveryCnt();
			break;
		default:
			count = 0;
			break;
		}

		return count;
	}

}
