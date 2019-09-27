package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJReq;
import lombok.Data;

@Data
@XStreamAlias("tns:ifRequest")
public class InterfacesCJReq0305 extends InterfacesCJReq {

	public InterfacesCJReq0305() {
		super("IF_03_05");
	}

	@XStreamImplicit
	private List<CJLtSupplyPlan> ltSupplyPlans;

}
