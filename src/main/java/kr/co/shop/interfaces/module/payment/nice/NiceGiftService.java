package kr.co.shop.interfaces.module.payment.nice;

import static kr.co.shop.interfaces.module.payment.nice.model.CommonResponse.outPutCommonResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.co.shop.interfaces.module.payment.base.AbstractPaymentLogService;
import kr.co.shop.interfaces.module.payment.nice.model.BalanceRequest;
import kr.co.shop.interfaces.module.payment.nice.model.BalanceResponse;
import kr.co.shop.interfaces.module.payment.nice.model.BalanceTransferRequest;
import kr.co.shop.interfaces.module.payment.nice.model.BalanceTransferResponse;
import kr.co.shop.interfaces.module.payment.nice.model.ChargeRequest;
import kr.co.shop.interfaces.module.payment.nice.model.ChargeResponse;
import kr.co.shop.interfaces.module.payment.nice.model.CollectionRequest;
import kr.co.shop.interfaces.module.payment.nice.model.CollectionResponse;
import kr.co.shop.interfaces.module.payment.nice.model.CommNiceRes;
import kr.co.shop.interfaces.module.payment.nice.model.PerformanceResponse;
import kr.co.shop.interfaces.module.payment.nice.model.SaleAgenciesRequest;
import kr.co.shop.interfaces.module.payment.nice.model.SaleAgenciesResponse;
import kr.co.shop.interfaces.module.payment.nice.model.TranHistoryRequest;
import kr.co.shop.interfaces.module.payment.nice.model.TranHistoryResponse;
import kr.co.nicevan.pos.PosClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 나이스 기프트 카드 전문 서비스
 * @FileName : NiceGiftService.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Component
@Slf4j
public class NiceGiftService extends AbstractPaymentLogService {

	/**
	 * @Desc : 전문 송수신
	 * @Method Name : send
	 * @Date : 2019. 4. 10.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String send(String param) throws Exception {
		byte[] sendBuff;
		byte[] recvBuff = new byte[2048];

		sendBuff = (param.getBytes());

		PosClient posClient = new PosClient();

		// TEST IP/PORT
		recvBuff = posClient.service("211.33.136.19", 4457, sendBuff);

		String result = "";
		log.debug("Recv : {}", new String(recvBuff, "euc-kr"));
		log.debug("Recv : {}", new String(recvBuff, StandardCharsets.UTF_8));
//            result = new String(recvBuff, "euc-kr");
		result = new String(recvBuff, StandardCharsets.UTF_8);

//		String tmpResult = new String(recvBuff, "euc-kr");
//		int reqLen = Integer.parseInt(param.substring(0, 4));
//		log.debug("##### tmpMessage: {}", tmpResult.substring(reqLen + 4, reqLen + 30));
		log.debug("##### result: {}", result);

		return result;
	}

	/**
	 * @Desc : 응답전문에서 응답 코드 파싱
	 * @Method Name : validRes
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param responseStr
	 * @param totalSize
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> validRes(String responseStr, int totalSize) throws Exception {
		String responseCode = responseStr.substring(totalSize, totalSize + 4);
		log.debug("##### responseCode: {}", responseCode);
		HashMap<String, Object> validResult = new HashMap<>();
		validResult.put("responseCode", responseCode);
		validResult.put("responseMsg", NiceGiftUtil.convertCodeToMsg(responseCode));

		return validResult;
	}

	/**
	 * @Desc : 응답전문을 HashMap에 담는다
	 * @Method Name : responseToObject
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param responseStr
	 * @param totalSize
	 * @return
	 */
	public HashMap<String, Object> responseToObject(String responseStr, Long totalSize) {

		String commonResponse = responseStr.substring(totalSize.intValue(), totalSize.intValue() + 66);
		String responseBody = responseStr.substring(totalSize.intValue() + 66);

		log.debug("###### commonResponse: {}", commonResponse);

		HashMap<String, Object> param = new HashMap<>();
		HashMap<String, Object> result = outPutCommonResponse(param, commonResponse);
		result.put("responseBody", responseBody);

		return result;
	}

	/**
	 * @Desc : 잔액조회 전문 송수신
	 * @Method Name : sendBalance
	 * @Date : 2019. 4. 17.
	 * @Author : YSW
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CommNiceRes<BalanceResponse> sendBalance(BalanceRequest param) throws Exception {

		String result = send(param.getRequest());

		BalanceResponse responseObj = new BalanceResponse();
		HashMap<String, Object> resResult = validRes(result, 250);
		CommNiceRes<BalanceResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);

		if ("0000".equals(resResult.get("responseCode"))) {
			HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
			BalanceResponse convertResult = responseObj.convertObject(resultObj);
			convertResult.setCardImgCode(result.substring(146, 156));
			sendResult.setResData(convertResult);
		}

		return sendResult;
	}

	/**
	 * @Desc : 거래내역조회 전문 송수신
	 * @Method Name : sendTranHistory
	 * @Date : 2019. 4. 17.
	 * @Author : YSW
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CommNiceRes<TranHistoryResponse> sendTranHistory(TranHistoryRequest param) throws Exception {

		String result = send(param.getRequest());

		TranHistoryResponse responseObj = new TranHistoryResponse();
		HashMap<String, Object> resResult = validRes(result, 251);
		CommNiceRes<TranHistoryResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);

		try {
			if ("0000".equals(resResult.get("responseCode"))) {
				HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
				log.debug("##### resultObj: {}", resultObj.toString());
				TranHistoryResponse convertResult = responseObj.convertObject(resultObj, param.getPage());
				sendResult.setResData(convertResult);
			}
		} catch (Exception e) {
			log.debug("##### error: {}", e.getMessage());
			CommNiceRes<TranHistoryResponse> exResult = new CommNiceRes<>();
			exResult.setResCode("9999");
			exResult.setResMsg(e.getMessage());
			return exResult;
		}

		return sendResult;
	}

	/**
	 * @Desc : 회수하기(사용하기) 전문 송수신
	 * @Method Name : sendCollection
	 * @Date : 2019. 4. 17.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CommNiceRes<CollectionResponse> sendCollection(CollectionRequest param) throws Exception {

//		String result = send(param.getRequest());
		String result;
		String reqStr;
		if ("A4".equals(param.getServiceCode())) {
			log.debug("##### electronDoc type: {}", "사용");
			log.debug("##### request: {}", param.getRequest());
			result = send(param.getRequest());
			reqStr = param.getRequest();
		} else {
			log.debug("##### electronDoc type: {}", "사용 취소");
			log.debug("##### OApprovalNo: {}", param.getOApprovalNo());
			log.debug("##### cancel request: {}", param.getCancelRequest());
			result = send(param.getCancelRequest());
			reqStr = param.getCancelRequest();
		}

		CollectionResponse responseObj = new CollectionResponse();
		HashMap<String, Object> resResult = validRes(result, 282);
		CommNiceRes<CollectionResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);
		sendResult.setReqStr(reqStr);

		try {
			if ("0000".equals(resResult.get("responseCode"))) {
				HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
				log.debug("##### resultObj: {}", resultObj.toString());
				CollectionResponse convertResult = responseObj.convertObject(resultObj);
				sendResult.setResData(convertResult);
			}
		} catch (Exception e) {
			log.debug("##### error: {}", e.getMessage());
			CommNiceRes<CollectionResponse> exResult = new CommNiceRes<>();
			exResult.setResCode("9999");
			exResult.setResMsg(e.getMessage());
			return exResult;
		}

		return sendResult;
	}

	public CommNiceRes<ChargeResponse> sendCharge(ChargeRequest param) throws Exception {
		String result;
		String reqStr;
		if ("A3".equals(param.getServiceCode())) {
			log.debug("##### electronDoc type: {}", "충전");
			log.debug("##### request: {}", param.getRequest());
			result = send(param.getRequest());
			reqStr = param.getRequest();
		} else {
			log.debug("##### electronDoc type: {}", "충전 취소");
			log.debug("##### OApprovalNo: {}", param.getOApprovalNo());
			log.debug("##### cancel request: {}", param.getCancelRequest());
			result = send(param.getCancelRequest());
			reqStr = param.getCancelRequest();
		}

		ChargeResponse responseObj = new ChargeResponse();
		HashMap<String, Object> resResult = validRes(result, 256);
		CommNiceRes<ChargeResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);
		sendResult.setReqStr(reqStr);
		sendResult.setResStr(result);

		try {
			if ("0000".equals(resResult.get("responseCode"))) {
				HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
				log.debug("##### resultObj: {}", resultObj.toString());
				ChargeResponse convertResult = responseObj.convertObject(resultObj);
				sendResult.setResData(convertResult);
			}
		} catch (Exception e) {
			log.debug("##### error: {}", e.getMessage());
			CommNiceRes<ChargeResponse> exResult = new CommNiceRes<>();
			exResult.setResCode("9999");
			exResult.setResMsg(e.getMessage());
			return exResult;
		}

		return sendResult;
	}

	/**
	 * @Desc : 판매대행(선물하기) 전문 송수신
	 * @Method Name : sendSaleAgencies
	 * @Date : 2019. 4. 23.
	 * @Author : nalpari
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CommNiceRes<SaleAgenciesResponse> sendSaleAgencies(SaleAgenciesRequest param) throws Exception {

		String result;
		String reqStr;
		if ("A2".equals(param.getServiceCode())) {
			log.debug("##### electronDoc type: {}", "판매대행(선물하기)");
			log.debug("##### request: {}", param.getRequest());
			result = send(param.getRequest());
			reqStr = param.getRequest();
		} else {
			log.debug("##### electronDoc type: {}", "판매대행 취소");
			log.debug("##### cancel request: {}", param.getCancelRequest());
			result = send(param.getCancelRequest());
			reqStr = param.getCancelRequest();
		}

		SaleAgenciesResponse responseObj = new SaleAgenciesResponse();
		HashMap<String, Object> resResult = validRes(result, 353);
		CommNiceRes<SaleAgenciesResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);
		sendResult.setReqStr(reqStr);
		sendResult.setResStr(result);

		try {
			if ("0000".equals(resResult.get("responseCode"))) {
				HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
				log.debug("##### resultObj: {}", resultObj.toString());
				SaleAgenciesResponse convertResult = responseObj.convertObject(resultObj);
				sendResult.setResData(convertResult);
			}
		} catch (Exception e) {
			log.debug("##### error: {}", e.getMessage());
			CommNiceRes<SaleAgenciesResponse> exResult = new CommNiceRes<>();
			exResult.setResCode("9999");
			exResult.setResMsg(e.getMessage());
			return exResult;
		}

		return sendResult;

	}

	public CommNiceRes<BalanceTransferResponse> sendBalanceTransfer(BalanceTransferRequest param) throws Exception {
		String result;
		String reqStr;
		if ("AC".equals(param.getServiceCode())) {
			log.debug("##### electronDoc type: {}", "상품권잔액이전");
			log.debug("##### request: {}", param.getRequest());
			result = send(param.getRequest());
			reqStr = param.getRequest();
		} else {
			log.debug("##### electronDoc type: {}", "상품권잔액이전 취소");
			log.debug("##### cancel request: {}", param.getCancelRequest());
			result = send(param.getCancelRequest());
			reqStr = param.getCancelRequest();
		}

		BalanceTransferResponse responseObj = new BalanceTransferResponse();
		HashMap<String, Object> resResult = validRes(result, 380);
		CommNiceRes<BalanceTransferResponse> sendResult = new CommNiceRes<>(resResult.get("responseCode").toString(),
				resResult.get("responseMsg").toString(), responseObj);
		sendResult.setReqStr(reqStr);
		sendResult.setResStr(result);

		try {
			if ("0000".equals(resResult.get("responseCode"))) {
				HashMap<String, Object> resultObj = responseToObject(result, param.getTotalSize());
				log.debug("##### resultObj: {}", resultObj.toString());
				BalanceTransferResponse convertResult = responseObj.convertObject(resultObj);
				sendResult.setResData(convertResult);
			}
		} catch (Exception e) {
			log.debug("##### error: {}", e.getMessage());
			CommNiceRes<BalanceTransferResponse> exResult = new CommNiceRes<>();
			exResult.setResCode("9999");
			exResult.setResMsg(e.getMessage());
			return exResult;
		}

		return sendResult;
	}

	/**
	 * @Desc : 실적 데이터 파일을 읽고 처리한다
	 * @Method Name : readPerformanceFile
	 * @Date : 2019. 5. 8.
	 * @Author : nalpari
	 */
	public static List<PerformanceResponse> readPerformanceFile(String niceServiceType) {
		// 결과
		List<PerformanceResponse> result = new ArrayList<>();
		try {
			// 파일 객체 생성
			File file = null;
			if ("0215".equals(niceServiceType)) {
				file = new File("D:\\performanceData\\OC01_1~1.DAT");
			} else if ("0216".equals(niceServiceType)) {
				file = new File("D:\\performanceData\\OS01_1~1.DAT");
			}
			// 입력 스트림 생성
			FileReader filereader = new FileReader(file);
			// 입력 버퍼 생성
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
//				System.out.println(line);
//				log.debug("### readLine: {}", line);
				PerformanceResponse performanceResponse = new PerformanceResponse(niceServiceType, line);
				performanceResponse.setMapngDtm("N");
				result.add(performanceResponse);
			}
			// .readLine()은 끝에 개행문자를 읽지 않는다.
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		readPerformanceFile("0216");
	}
}
