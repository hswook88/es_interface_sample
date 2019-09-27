package kr.co.shop.interfaces.zinterfacesdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesIOLogger;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesIOLoggerDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterfacesIOLoggerService {

	@Autowired
	private InterfacesIOLoggerDao interfacesIOLoggerDao;

	/*
	 * 인터페이스 통신 로그 저장
	 */
	public void insertLoggerNoTrx(String ifname, String ifinput, String ifoutput, String successyn) throws Exception {
		if (ifname.length() > 100) {
			log.debug("ifName의 길이가 varchar(100)의 제한을 조과함으로 길이 이상의 문자열을 편집합니다.");
			ifname = ifname.substring(0, 99);
		}

		InterfacesIOLogger loggerParams = new InterfacesIOLogger();
		loggerParams.setIfName(ifname);
		loggerParams.setIfInput(ifinput);
		loggerParams.setIfOutput(ifoutput);
		loggerParams.setSuccessYn(successyn);

		interfacesIOLoggerDao.insertLogger(loggerParams);
	}

	public boolean setLoggerData(String ifName, String inputData, String outputData, String complateYn) {
		boolean successFlag = false;

		try {
			insertLoggerNoTrx(ifName, inputData, outputData, complateYn);
			successFlag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("InterfacesHistory 기록에 실패하였습니다.");
			log.debug(UtilsText.concat(">>> ", ifName, " / ", inputData, " / ", outputData, " / ", complateYn));
		}

		return successFlag;
	}

}
