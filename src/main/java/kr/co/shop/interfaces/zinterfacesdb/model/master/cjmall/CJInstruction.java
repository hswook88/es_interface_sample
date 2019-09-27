package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

/**
 * CJmall 주문지시 처리용 모델
 * 
 * @author 백인천
 *
 */
@Data
public class CJInstruction {

	private String ordNo; // 주문정보 - 주문번호

	@XStreamImplicit
	private List<CJInstructionDetail> instructionDetail; // 지시상세내역

}
