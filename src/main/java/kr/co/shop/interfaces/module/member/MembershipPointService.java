package kr.co.shop.interfaces.module.member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.config.MembershipConfig;
import kr.co.shop.interfaces.module.member.model.AfterServiceHistory;
import kr.co.shop.interfaces.module.member.model.AfterServiceHistoryParser;
import kr.co.shop.interfaces.module.member.model.BuyFixProduct;
import kr.co.shop.interfaces.module.member.model.BuyFixProductParser;
import kr.co.shop.interfaces.module.member.model.CardConfirmParser;
import kr.co.shop.interfaces.module.member.model.CardHistory;
import kr.co.shop.interfaces.module.member.model.CardHistorysParser;
import kr.co.shop.interfaces.module.member.model.CardNumber;
import kr.co.shop.interfaces.module.member.model.CardNumberParser;
import kr.co.shop.interfaces.module.member.model.CardValidate;
import kr.co.shop.interfaces.module.member.model.ClawbackPointParser;
import kr.co.shop.interfaces.module.member.model.EmployCertReport;
import kr.co.shop.interfaces.module.member.model.EmployCertReportParser;
import kr.co.shop.interfaces.module.member.model.EmployPoint;
import kr.co.shop.interfaces.module.member.model.EmployPointHistory;
import kr.co.shop.interfaces.module.member.model.EmployPointHistoryParser;
import kr.co.shop.interfaces.module.member.model.EmployPointParser;
import kr.co.shop.interfaces.module.member.model.GeneralConfirmParser;
import kr.co.shop.interfaces.module.member.model.GiftCard;
import kr.co.shop.interfaces.module.member.model.GiftCardParser;
import kr.co.shop.interfaces.module.member.model.LatedSavePointParser;
import kr.co.shop.interfaces.module.member.model.PrivateReport;
import kr.co.shop.interfaces.module.member.model.PrivateReportParser;
import kr.co.shop.interfaces.module.member.model.StoreOrderHistory;
import kr.co.shop.interfaces.module.member.model.StoreOrderHistoryParser;
import kr.co.shop.interfaces.module.member.model.StorePointHistory;
import kr.co.shop.interfaces.module.member.model.StorePointHistoryParser;
import kr.co.shop.interfaces.module.member.model.TermiExpectReport;
import kr.co.shop.interfaces.module.member.model.TermiExpectReportParser;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import kr.co.shop.interfaces.module.member.utils.RequestSender;
import kr.co.shop.interfaces.module.member.utils.Util;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesIOLoggerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MembershipPointService {

	@Autowired
	private InterfacesIOLoggerService interfacesIOLoggerService;

	/**
	 * @Desc : 통신 기본정보를 구성.
	 * @Method Name : generateSender
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param urlKey
	 * @return
	 */
	private static RequestSender generateSender(String urlKey) {
		String host = MembershipConfig.getMemberShipServer();
		String url = MembershipInfo.getMemberUrl(urlKey);
		return new RequestSender(host + url);
	}

	@SuppressWarnings("serial")
	private final static Map<String, Boolean> cardStatus = new HashMap<String, Boolean>(5) {
		{
			put("01", true);
			put("02", true);
			put("03", false);
			put("04", false);
			put("05", false);
		}
	};

	@SuppressWarnings("serial")
	private final static Map<String, Boolean> safekeyCheck = new HashMap<String, Boolean>(3) {
		{
			put("00", true);
			put("01", true);
			put("02", true);
		}
	};

	/**
	 * @Desc : 소멸예정포인트 조회 [memB050a]
	 * @Method Name : getTermiExpectReportBySafeKey
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 * @return
	 * @return
	 */
	public TermiExpectReport getTermiExpectReportBySafeKey(String safeKey) throws MemberShipProcessException {
		RequestSender sender = generateSender("memB050a");
		String urlEcd = "";

		try {
			urlEcd = Util.UrlEncoding(safeKey, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
			log.debug("UrlEncoding Failed");
		}

		sender.addParameter("safe_key", urlEcd);

		TermiExpectReport ter = null;
		TermiExpectReportParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.send();
			log.debug("Status 확인 : " + sender.getStatus());

			parser = new TermiExpectReportParser(sender.getResponseBody());
			ter = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		if (ter == null) {
			ter = new TermiExpectReport();
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB050a] 소멸예정포인트 조회(getTermiExpectReportBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = ter.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return ter;
	}

	/**
	 * @Desc : 오프라인 AS 이력 (멤버십회원만) [memB110a]
	 * @Method Name : getOfflineAsHistory
	 * @Date : 2019. 3. 7.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 */
	public List<AfterServiceHistory> getOfflineAsHistory(String safeKey) throws MemberShipProcessException {
		List<AfterServiceHistory> result = null;
		AfterServiceHistoryParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			RequestSender sender = generateSender("memB110a");
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.send();

			parser = new AfterServiceHistoryParser(sender.getResponseBody());
			result = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		if (result == null) {
			result = new ArrayList<AfterServiceHistory>(1);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB110a] 오프라인 AS이력(getOfflineAsHistory)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = result.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return result;
	}

	/**
	 * @Desc : 포인트 적립 혹은 차감 [memB290a]
	 * @Method Name : updatePointForMembershipHandler
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param safeKey
	 * @param point
	 * @param gubun
	 * @param eventpoint
	 * @return
	 */
	public boolean updatePointForMembershipHandler(String safeKey, int point, String gubun, int eventpoint)
			throws Exception {
		RequestSender sender = generateSender("memB290a");

		// Interfaces 성공여부
		String complateYn = "N";

		boolean returnFlag = false;
		GeneralConfirmParser parser = null;

		try {
			sender.addParameter("safe_key", Util.encode(safeKey)); // 안심키
			sender.addParameter("date", Util.getYyyymmdd()); // 사용/사용취소
			sender.addParameter("point", Integer.toString(point)); // 적립포인트
			sender.addParameter("eventpoint", Integer.toString(eventpoint)); // 이벤트포인트
			sender.addParameter("gubun", gubun); // 사용/사용취소구분자 (사용: use, 사용취소 : cancel)
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB290a] 포인트 적립 혹은 차감(updatePointForMembershipHandler)";
		String inputData = UtilsText.concat(safeKey, " / ", String.valueOf(point), " / ", gubun, " / ",
				String.valueOf(eventpoint));
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 매장 비회원 구매 사후적립 , 오프라인구매확정 포인트적립 [memB300a]
	 * @Method Name : savePointAfterPurchase
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param safeKey
	 * @param storeCd
	 * @param posNo
	 * @param dealNo
	 * @param price
	 * @param date
	 * @return
	 */
	public String updatePointAfterPurchase(String safeKey, String storeCd, String posNo, String dealNo, int price,
			String date) throws MemberShipProcessException {
		RequestSender sender = generateSender("memB300a");
		String returnString = "";
		returnString = "04063805";

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey)); // 안심키
			sender.addParameter("store_cd", storeCd); // 매장코드
			sender.addParameter("Pos_no", posNo); // Pos번호
			sender.addParameter("Deal_no", dealNo); // 영수증번호
			sender.addParameter("Price", Integer.toString(price)); // 구입금액 (오프라인구매확정일경우 0 으로 보내줌)
			sender.addParameter("date", date); // 구매일자
			sender.send();

			LatedSavePointParser parser = new LatedSavePointParser(sender.getResponseBody());
			String failCode = parser.parse();

			returnString = failCode;
			complateYn = "Y";
		} catch (MemberShipProcessException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			returnString = "04063805";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB290a] 포인트 적립 혹은 차감(updatePointForMembershipHandler)";
		String inputData = UtilsText.concat(safeKey, " / ", storeCd, " / ", posNo, " / ", dealNo, " / ",
				String.valueOf(price), " / ", date);
		String outputData = returnString;

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnString;
	}

	/**
	 * @Desc : 환수포인트조회 [memB310a]
	 * @Method Name : getClawbackPoint
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param safeKey
	 * @param orderNum
	 * @param price
	 * @return
	 */
	public int getClawbackPoint(String safeKey, String orderNum, int price) throws MemberShipProcessException {
		RequestSender sender = generateSender("memB310a");
		sender.addParameter("safe_key", Util.encode(safeKey));
		sender.addParameter("Ord_no", orderNum);
		sender.addParameter("Price", Integer.toString(price));

		int clawbackPoint = 0;
		ClawbackPointParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.send();
			parser = new ClawbackPointParser(sender.getResponseBody());
			Integer point = parser.parse();
			if (point == null) {
				clawbackPoint = 0;
			} else {
				clawbackPoint = point;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB310a] 환수포인트조회(getClawbackPoint)";
		String inputData = UtilsText.concat(safeKey, " / ", orderNum, " / ", String.valueOf(price));
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return clawbackPoint;
	}

	/**
	 * @Desc : 포인트 환수 [memB320a]
	 * @Method Name : updateClawbackPoint
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param safeKey
	 * @param dlvyId
	 * @param clawbackPoint
	 * @return
	 */
	public boolean updateClawbackPoint(String safeKey, String dlvyId, int clawbackPoint)
			throws MemberShipProcessException {
		RequestSender sender = generateSender("memB320a");

		boolean returnFlag = false;
		GeneralConfirmParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey)); // 안심키
			sender.addParameter("devy_id", dlvyId); // 배송아이디
			sender.addParameter("clawbackPoint", Integer.toString(clawbackPoint)); // 환수금액
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memB320a] 포인트 환수(updateClawbackPoint)";
		String inputData = UtilsText.concat(safeKey, " / ", dlvyId, " / ", String.valueOf(clawbackPoint));
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : VIP 탈퇴 가능 체크/탈퇴 처리 [memA800a]
	 * @Method Name : updateVipMemberCancel
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param safeKey
	 * @param point
	 * @return
	 */
	public boolean updateVipMemberCancel(String safeKey, String point) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA800a");
		boolean returnFlag = false;

		GeneralConfirmParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			if (UtilsText.isEmpty(point) || point == null) {
				point = UtilsText.defaultString(point, "0");
			}

			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("point", point);
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA800a] VIP 탈퇴 가능 체크/탈퇴 처리(updateVipMemberCancel)";
		String inputData = UtilsText.concat(safeKey, " / ", point);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 카드분실처리 [memA810a]
	 * @Method Name : inputLoseCardForUserBySafeKey
	 * @Date : 2019. 3. 4.
	 * @Author : 백인천
	 * @param safeKey
	 * @param cardNumber
	 * @return
	 */
	public boolean inputLoseCardForUserBySafeKey(String safeKey, String cardNumber) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA810a");
		boolean returnFlag = false;

		GeneralConfirmParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("card_no", Util.encode(cardNumber));
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA810a] 카드분실처리(inputLoseCardForUserBySafeKey)";
		String inputData = UtilsText.concat(safeKey, " / ", cardNumber);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 회원카드정보이력 [memA820a]
	 * @Method Name : getCardHistorysBySafeKey
	 * @Date : 2019. 3. 4.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 */
	public List<CardHistory> getCardHistorysBySafeKey(String safeKey) throws MemberShipProcessException {
		List<CardHistory> result = null;
		RequestSender sender = generateSender("memA820a");
		CardHistorysParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.send();

			parser = new CardHistorysParser(sender.getResponseBody());
			result = parser.parse();
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		if (result == null) {
			result = new ArrayList<CardHistory>(1);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA820a] 회원카드정보이력(getCardHistorysBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return result;
	}

	/**
	 * 
	 * @Desc : 멤버십 카드 번호 조회 [memA830a]
	 * @Method Name : getUserCardNumberBySafeKey
	 * @Date : 2019. 2. 20.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 */
	public CardNumber getUserCardNumberBySafeKey(String safeKey) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA830a");

		CardNumber parser = null;
		CardNumberParser cardPar = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.send();

			cardPar = new CardNumberParser(sender.getResponseBody());
			parser = cardPar.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA830a] 멤버십 카드 번호 조회(getUserCardNumberBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return parser;
	}

	/**
	 * @Desc : 기타적립코드 유효성 확인 [memA860a]
	 * @Method Name : isValidationBySavedCode
	 * @Date : 2019. 2. 20.
	 * @Author : 백인천
	 * @param validateCode
	 * @return
	 */
	public boolean isValidationBySavedCode(String validateCode) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA860a");
		boolean returnFlag = false;

		GeneralConfirmParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("comm_cd", validateCode);
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA860a] 기타적립코드 유효성 확인(isValidationBySavedCode)";
		String inputData = UtilsText.concat(validateCode);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 기타적립금 적립차감 [memA870a]
	 * @Method Name : updateEtcProcessUserPointByUser
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param safeKey
	 * @param changeAmount
	 * @param etcPointCode
	 */
	public boolean updateEtcProcessUserPointByUser(String safeKey, int changeAmount, String etcPointCode)
			throws Exception {
		RequestSender sender = generateSender("memA870a");
		sender.addParameter("safe_key", Util.encode(safeKey));
		sender.addParameter("sale_date", Util.getYyyymmdd());
		sender.addParameter("comm_cd", etcPointCode);

		GeneralConfirmParser parser = null;

		boolean returnFlag = false;

		// Interfaces 성공여부
		String complateYn = "N";

		String gubun = null;
		if (changeAmount == 0) {
			log.error(UtilsText.concat("변경금액이 0일수는 없다. ", safeKey));
		} else {
			gubun = changeAmount > 0 ? "save" : "subt";
			changeAmount = Math.abs(changeAmount);
		}

		try {
			sender.addParameter("gubun", gubun);
			sender.addParameter("etc_point", Integer.toString(changeAmount));
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA870a] 기타적립금 적립차감(updateEtcProcessUserPointByUser)";
		String inputData = UtilsText.concat(safeKey, " / ", String.valueOf(changeAmount), " / ", etcPointCode);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 모바일카드 발급 [memA880a]
	 * @Method Name : inputMobileCardBySafeKey
	 * @Date : 2019. 3. 4.
	 * @Author : 백인천
	 * @param safeKey
	 * @param password
	 */
	public boolean inputMobileCardBySafeKey(String safeKey, String password) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA880a");
		GeneralConfirmParser parser = null;

		boolean returnFlag = false;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("password", Util.encode(password));
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA870a] 기타적립금 적립차감(updateEtcProcessUserPointByUser)";
		String inputData = UtilsText.concat(safeKey, " / ", password);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 구매확정시 포인트지급 [memA890a]
	 * @Method Name : buyFixRequest
	 * @Date : 2019. 3. 14.
	 * @Author : 백인천
	 * @param safeKey
	 * @param orderNum
	 * @return
	 */
	public List<BuyFixProduct> buyFixRequest(String safeKey, String orderNum) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA890a");
		List<BuyFixProduct> results = null;

		// Interfaces 성공여부
		String complateYn = "N";

		BuyFixProductParser parser = null;

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("Ord_no", Util.encode(orderNum));
			sender.send();

			parser = new BuyFixProductParser(sender.getResponseBody());

			results = parser.parse();
			if (results.isEmpty()) {
				if (log.isErrorEnabled()) {
					log.error(String.format("구매확정 결과없음[%s]", orderNum));
				}
			}
			complateYn = "Y";
		} catch (MemberShipProcessException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(String.format("시스템오류가 발생하였습니다.[%s][%s]", orderNum, "포인트지급실패"));
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(String.format("시스템오류가 발생하였습니다.[%s][%s]", orderNum, "서버통신오류"));
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA890a] 구매확정시 포인트지급(buyFixRequest)";
		String inputData = UtilsText.concat(safeKey, " / ", orderNum);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return results;
	}

	/**
	 * @Desc : 기프트 카드 정보 조회 [memA900a]
	 * @Method Name : checkGiftCard
	 * @Date : 2019. 3. 5.
	 * @Author : 백인천
	 * @param cardNo
	 * @return
	 */
	public GiftCard checkGiftCard(String cardNo) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA900a");

		String encCardNo = Util.encode(cardNo);

		GiftCard pr = null;
		GiftCardParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			log.debug(encCardNo);
			sender.addParameter("card_no", encCardNo);
			sender.send();

			parser = new GiftCardParser(sender.getResponseBody());
			pr = parser.parse();

			complateYn = "Y";
		} catch (MemberShipProcessException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException("카드가존재하지않습니다.");
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException("시스템오류가 발생하였습니다.[서버통신오류]");
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA900a] 기프트 카드 정보 조회(checkGiftCard)";
		String inputData = UtilsText.concat(cardNo);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return pr;

	}

	/**
	 * @Desc : 가용포인트 조회 [memA910a]
	 * @Method Name : getPrivateReportBySafeKey
	 * @Date : 2019. 2. 15.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 */
	public PrivateReport getPrivateReportBySafeKey(String safeKey) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA910a");
		sender.addParameter("safe_key", Util.encode(safeKey));

		PrivateReport pr = null;
		PrivateReportParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.send();
			parser = new PrivateReportParser(sender.getResponseBody());
			pr = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		if (pr == null) {
			pr = new PrivateReport();
		}

		// 20190625 포인트 확인 위해 임시로 포인트 디폴트값 구성하여 리턴(방부장님 요청사항)
		pr.setTotalPoint(500000);
		pr.setEventPoint(200000);
		pr.setAccessPoint(300000);
		pr.setEventDate("2019-07-30");

		// Interfaces History 구성하여 로그 생성
		// String ifName = "[memA910a] 가용포인트 조회(getPrivateReportBySafeKey)";
		String ifName = "[memA910a] 가용포인트 조회(getPrivateReportBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return pr;
	}

	/**
	 * @Desc : 포인트 적립 이력 [memA920a]
	 * @Method Name : getStorePointHistoryBySafeKey
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param safeKey
	 * @return
	 */
	public List<StorePointHistory> getStorePointHistoryBySafeKey(String safeKey) throws MemberShipProcessException {
		List<StorePointHistory> result = null;
		StorePointHistoryParser parser = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			RequestSender sender = generateSender("memA920a");
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.send();

			parser = new StorePointHistoryParser(sender.getResponseBody());
			result = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		if (result == null) {
			result = new ArrayList<StorePointHistory>(1);
		}

		Collections.sort(result, new Comparator<StorePointHistory>() {
			@Override
			public int compare(StorePointHistory o1, StorePointHistory o2) {
				int compared = o1.getInsTime().compareTo(o2.getInsTime());
				if (compared > 0) {
					return -1;
				} else if (compared < 0) {
					return 1;
				}
				return 0;
			}
		});

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA920a] 포인트 적립 이력(getStorePointHistoryBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return result;
	}

	/**
	 * @Desc : 구매이력 조회 [memA930a]
	 * @Method Name : registUserPointByUser
	 * @Date : 2019. 2. 19.
	 * @Author : 백인천
	 * @param safeKey
	 * @param changeAmount
	 */
	public List<StoreOrderHistory> getStoreOrderHistoryBySafeKey(String safeKey) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA930a");
		sender.addParameter("safe_key", Util.encode(safeKey));

		StoreOrderHistoryParser parser = null;
		List<StoreOrderHistory> result = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.send();

			parser = new StoreOrderHistoryParser(sender.getResponseBody());
			result = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA930a] 구매이력 조회(getStoreOrderHistoryBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return result;
	}

	/**
	 * @Desc : 포인트 강제 적립/차감 [memA940a]
	 * @Method Name : registUserPointByUser
	 * @Date : 2019. 2. 19.
	 * @Author : 백인천
	 * @param safeKey
	 * @param changeAmount
	 */
	public boolean registUserPointByUser(String safeKey, int changeAmount) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA940a");
		sender.addParameter("safe_key", Util.encode(safeKey));
		sender.addParameter("date", Util.getYyyymmdd());

		GeneralConfirmParser parser = null;

		boolean returnFlag = false;

		// Interfaces 성공여부
		String complateYn = "N";

		String gubun = null;
		if (changeAmount == 0) {
			log.error(UtilsText.concat("변경금액이 0일수는 없다. ", safeKey));
		} else {
			gubun = changeAmount > 0 ? "save" : "subt";
			changeAmount = Math.abs(changeAmount);
		}

		sender.addParameter("gubun", gubun);
		sender.addParameter("point", Integer.toString(changeAmount));
		try {
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA940a] 포인트 강제 적립/차감(registUserPointByUser)";
		String inputData = UtilsText.concat(safeKey, " / ", String.valueOf(changeAmount));
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 맴버십 카드 유효성 여부 확인 [memA960a]
	 * @Method Name : validateByCardNumberBySafeKey
	 * @Date : 2019. 2. 26.
	 * @Author : 백인천
	 * @param safeKey
	 * @param cardNumber
	 * @return
	 */
	public CardValidate validateByCardNumberBySafeKey(String safeKey, String cardNumber)
			throws MemberShipProcessException {
		RequestSender sender = generateSender("memA960a");

		CardConfirmParser parser = null;
		CardValidate cardValidate = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("card_no", Util.encode(cardNumber));
			sender.send();

			parser = new CardConfirmParser(sender.getResponseBody());
			cardValidate = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// 카드번호를 잘못입력했을 경우 CardStat가 ""으로 넘어오므로 메시지 처리를 위해 ""일 경우 통과 시킴
		if (!UtilsText.isEmpty(cardValidate.getCardStat())) {
			if (false == cardStatus.containsKey(cardValidate.getCardStat())) {
				log.error(UtilsText.concat("존재하지않는 상태코드 [", cardValidate.getCardStat(), "]"));
			}
		}
		if (false == safekeyCheck.containsKey(cardValidate.getSafekeyCheck())) {
			log.error(UtilsText.concat("존재하지않는 본인여부코드 [", cardValidate.getCardStat(), "]"));
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA960a] 맴버십 카드 유효성 여부 확인(validateByCardNumberBySafeKey)";
		String inputData = UtilsText.concat(safeKey);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return cardValidate;
	}

	/**
	 * @Desc : 맴버십카드 비밀번호 변경 [memA970a]
	 * @Method Name : changeCardPasswordBySafeKey
	 * @Date : 2019. 3. 15.
	 * @Author : 백인천
	 * @param safeKey
	 * @param password
	 */
	public boolean changeCardPasswordBySafeKey(String safeKey, String password) throws MemberShipProcessException {
		RequestSender sender = generateSender("memA970a");

		GeneralConfirmParser parser = null;

		boolean returnFlag = false;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("safe_key", Util.encode(safeKey));
			sender.addParameter("password", Util.encode(password));
			sender.send();

			parser = new GeneralConfirmParser(sender.getResponseBody());
			String generalFlag = parser.parse();

			if (!"FAILURE".equals(generalFlag)) {
				returnFlag = true;
			}
			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// Interfaces History 구성하여 로그 생성
		String ifName = "[memA970a] 맴버십카드 비밀번호 변경(changeCardPasswordBySafeKey)";
		String inputData = UtilsText.concat(safeKey, " / ", password);
		String outputData = parser.toString();

		interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData, complateYn);

		return returnFlag;
	}

	/**
	 * @Desc : 임직원 인증 [memA1000a]
	 * @Method Name : employeeCertificationByEmployNum
	 * @Date : 2019. 4. 2.
	 * @Author : 백인천
	 * @param employeesNumber
	 * @param employeesName
	 */
	public EmployCertReport employeeCertificationByEmployNum(String employeesNumber, String employeesName)
			throws Exception {
		RequestSender sender = generateSender("memA1000a");

		EmployCertReportParser parser = null;
		EmployCertReport employCertReport = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("employeesNumber", employeesNumber);
			sender.addParameter("employeesName", employeesName);
			sender.send();

			parser = new EmployCertReportParser(sender.getResponseBody());
			employCertReport = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// 테스트용으로 하드코딩값 전달
		if (employCertReport.getSuccessFlag() == null) {
			if (employeesNumber.equals("01") && employeesName.equals("홍길동")) {
				employCertReport.setSuccessFlag("SUCCESS");
				employCertReport.setEmployeesNumber("01");
				employCertReport.setEmployeesName("홍길동");
				employCertReport.setEmployeesDpmt("정보전략팀");
				employCertReport.setEmployeesPosition("과장");
				employCertReport.setEmployeesJoinDt("20190101");
				employCertReport.setEmployeesResignDt("99991231");
			} else {
				employCertReport.setSuccessFlag("FAILED");
			}
		}

		/*
		 * // Interfaces History 구성하여 로그 생성 String ifName =
		 * "[memA1000a] 임직원 인증(employeeCertificationByEmployNum)"; String inputData =
		 * UtilsText.concat(UtilsText.concat(employeesNumber + " / " + employeesName));
		 * String outputData = parser.toString();
		 * 
		 * interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData,
		 * complateYn);
		 */

		return employCertReport;
	}

	/**
	 * @Desc : 임직원 포인트 사용내역 [memA1001a]
	 * @Method Name : checkPointHistoryByEmployNum
	 * @Date : 2019. 4. 2.
	 * @Author : 백인천
	 * @param employeesNumber
	 */
	public List<EmployPointHistory> checkPointHistoryByEmployNum(String employeesNumber)
			throws MemberShipProcessException {
		RequestSender sender = generateSender("memA1001a");
		sender.addParameter("employeesNumber", Util.encode(employeesNumber));

		EmployPointHistoryParser parser = null;
		List<EmployPointHistory> result = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.send();

			parser = new EmployPointHistoryParser(sender.getResponseBody());
			result = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// log.debug("complateYn >> " + complateYn);

		// 테스트용으로 하드코딩값 전달
		if (complateYn.equals("Y")) {
			result = new ArrayList<EmployPointHistory>();
			if (employeesNumber.equals("01")) {
				for (int i = 0; i < 4; i++) {
					EmployPointHistory aa = new EmployPointHistory();
					if (i == 0) {
						aa.setUsePoint("25000");
						aa.setUseGubun("use");
						aa.setInsDate("20190401");
						aa.setTotalPoint("300000");
						aa.setBalancePoint("275000");
					} else if (i == 1) {
						aa.setUsePoint("35000");
						aa.setUseGubun("use");
						aa.setInsDate("20190408");
						aa.setTotalPoint("275000");
						aa.setBalancePoint("240000");
					} else if (i == 2) {
						aa.setUsePoint("30000");
						aa.setUseGubun("use");
						aa.setInsDate("20190412");
						aa.setTotalPoint("240000");
						aa.setBalancePoint("210000");
					} else if (i == 3) {
						aa.setUsePoint("10000");
						aa.setUseGubun("use");
						aa.setInsDate("20190422");
						aa.setTotalPoint("210000");
						aa.setBalancePoint("200000");
					}
					result.add(aa);
				}
			}
		}

		/*
		 * // Interfaces History 구성하여 로그 생성 String ifName =
		 * "[memA1001a] 임직원 포인트 사용내역(checkPointHistoryByEmployNum)"; String inputData =
		 * UtilsText.concat(employeesNumber); String outputData = parser.toString();
		 * 
		 * interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData,
		 * complateYn);
		 */

		return result;
	}

	/**
	 * @Desc : 임직원 포인트 사용처리 [memA1002a]
	 * @Method Name : saveUsePointByEmployNum
	 * @Date : 2019. 4. 2.
	 * @Author : 백인천
	 * @param employeesNumber
	 * @param usePoint
	 */
	public EmployPoint saveUsePointByEmployNum(String employeesNumber, String usePoint)
			throws MemberShipProcessException {
		RequestSender sender = generateSender("memA1002a");

		EmployPointParser parser = null;
		EmployPoint employPoint = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("employeesNumber", employeesNumber);
			sender.addParameter("usePoint", usePoint);
			sender.send();

			parser = new EmployPointParser(sender.getResponseBody());
			employPoint = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// 테스트용으로 하드코딩값 전달
		if (employPoint.getSuccessFlag() == null) {
			if (employeesNumber.equals("01")) {
				employPoint.setSuccessFlag("SUCCESS");
				employPoint.setTotalPoint("300000");
				employPoint.setBalancePoint("200000");
			} else {
				employPoint.setSuccessFlag("FAILED");
			}
		}

		/*
		 * // Interfaces History 구성하여 로그 생성 String ifName =
		 * "[memA1002a] 임직원 포인트 사용처리(saveUsePointByEmployNum)"; String inputData =
		 * UtilsText.concat(UtilsText.concat(employeesNumber + " / " + usePoint));
		 * String outputData = parser.toString();
		 * 
		 * interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData,
		 * complateYn);
		 */

		return employPoint;
	}

	/**
	 * @Desc : 임직원 포인트 취소처리 [memA1002a]
	 * @Method Name : saveCancelPointByEmployNum
	 * @Date : 2019. 4. 2.
	 * @Author : 백인천
	 * @param employeesNumber
	 * @param usePoint
	 */
	public EmployPoint saveCancelPointByEmployNum(String employeesNumber, String cancelPoint)
			throws MemberShipProcessException {
		RequestSender sender = generateSender("memA1002a");

		EmployPointParser parser = null;
		EmployPoint employPoint = null;

		// Interfaces 성공여부
		String complateYn = "N";

		try {
			sender.addParameter("employeesNumber", employeesNumber);
			sender.addParameter("cancelPoint", cancelPoint);
			sender.send();

			parser = new EmployPointParser(sender.getResponseBody());
			employPoint = parser.parse();

			complateYn = "Y";
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MemberShipProcessException(e);
		}

		// 테스트용으로 하드코딩값 전달
		if (employPoint.getSuccessFlag() == null) {
			if (employeesNumber.equals("01")) {
				employPoint.setSuccessFlag("SUCCESS");
				employPoint.setTotalPoint("300000");
				employPoint.setBalancePoint("200000");
			} else {
				employPoint.setSuccessFlag("FAILED");
			}
		}

		/*
		 * // Interfaces History 구성하여 로그 생성 String ifName =
		 * "[memA1002a] 임직원 포인트 취소처리(saveCancelPointByEmployNum)"; String inputData =
		 * UtilsText.concat(UtilsText.concat(employeesNumber + " / " + cancelPoint));
		 * String outputData = parser.toString();
		 * 
		 * interfacesIOLoggerService.setLoggerData(ifName, inputData, outputData,
		 * complateYn);
		 */

		return employPoint;
	}

}