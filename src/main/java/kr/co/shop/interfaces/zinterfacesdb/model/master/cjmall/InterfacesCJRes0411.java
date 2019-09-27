package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJRes;
import lombok.Data;

@Data
public class InterfacesCJRes0411 extends InterfacesCJRes {

	// 지시내역목록
	@XStreamImplicit
	private List<CJInstruction> instruction;

}
